package haipo.com.newpay.mode;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import haipo.com.newpay.Ali.AlipayApiException;
import haipo.com.newpay.Ali.AlipaySignature;
import haipo.com.newpay.http.HttpParams;
import haipo.com.newpay.http.HttpServer;
import haipo.com.newpay.http.RetrofitUtils;
import haipo.com.newpay.mode.bean.AliBean;
import haipo.com.newpay.mode.bean.AliResultBean;
import haipo.com.newpay.mode.bean.GlobalBean;
import haipo.com.newpay.mode.inter.IAliPayMode;
import haipo.com.newpay.presenter.AliPayPresenter;
import haipo.com.newpay.utils.ConfigUtlis;
import haipo.com.newpay.utils.Utils;
import haipo.com.newpay.utils.XmlUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static haipo.com.newpay.http.HttpParams.PARAMS_BAR;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/18 15:02
 */
public class AliPayMode implements IAliPayMode {


    /**
     * 支付宝RSA私钥
     */
    public String priKey;

    /**
     * 支付宝RSA公钥
     */
    public String pub;

    /**
     * 支付宝公钥
     */
    public String ali_key;


    private GlobalBean globalBean;

    private AliPayPresenter payPresenter;
    private Observable<String> wxPayBody;

    private final int times = 6;



    private Subscriber<String> cancle = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Logger.i("----------------------------onCompleted-------------------------------");

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(String s) {
            payPresenter.paySuccess(s);
        }

    };


    public AliPayMode(AliPayPresenter payPresenter) {
        priKey = ConfigUtlis.getConfig("private_key");
        pub = ConfigUtlis.getConfig("public_key");
        ali_key = ConfigUtlis.getConfig("alipay_public_key");

        this.payPresenter = payPresenter;
    }

    @Override
    public void getData(Activity activity, String payInfo) {

    }

    @Override
    public void setAuth_code(String auth_code) {
        globalBean.auth_code = auth_code;
    }


    @Override
    public void aliPay(int function, final long delayTime) {
        HttpServer server = RetrofitUtils.getInstance().getStringClient(ConfigUtlis.getConfig("open_api_domain"));
        HttpParams params = getAliParams(function);
        final Observable<String> aliPay = server.getAliPay(params.getParams());
        aliPay.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payPresenter);
    }


    /**
     * 获取参数
     *
     * @param function
     * @return
     */
    private HttpParams getAliParams(int function) {
        HttpParams params = null;
        String biz_content = null;
        globalBean.type = function;
        params = HttpParams.getInstance().getSpecialParams(function);
        AliBean bean = new AliBean();
        bean.out_trade_no = globalBean.BillNo;
        bean.seller_id = ConfigUtlis.getConfig("seller_id");
        bean.total_amount = globalBean.payAmount;
        try {
            bean.subject = URLEncoder.encode(ConfigUtlis.getConfig("subject"), "UTF-8");
            bean.subject =  ConfigUtlis.getConfig("subject");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        bean.timeout_express = ConfigUtlis.getConfig("timeout_express");

        switch (function) {
            case HttpParams.PARAMS_BAR://条码支付
                bean.auth_code = globalBean.auth_code;
                bean.scene = ConfigUtlis.getConfig("ali_bar_code");
                break;
            case HttpParams.PARAMS_SCAN://扫码支付
                break;
        }
        biz_content = new Gson().toJson(bean);
        params.put("biz_content", biz_content);
        String sign_params = params.toString();
        Logger.i(sign_params);
        try {
            String sign = AlipaySignature.rsaSign(sign_params, priKey, "UTF-8");
            Logger.i(sign);
            params.put("sign", sign);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public int getFunction() {
        return globalBean.function;
    }

    @Override
    public GlobalBean getGloble() {
        return globalBean;
    }

    @Override
    public void aliQuery() {
        HttpServer server = RetrofitUtils.getInstance().getStringClient(ConfigUtlis.getConfig("open_api_domain"));
        HttpParams params = getAliParams(HttpParams.PARAMS_QUERY);
        Observable<String> aliPay = server.getAliPay(params.getParams());

        aliPay.timeout(10, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {

                        return observable
                                .zipWith(Observable.range(1, times), new Func2<Void, Integer, Integer>() {
                                    @Override
                                    public Integer call(Void aVoid, Integer integer) {
                                        return integer;
                                    }
                                }).flatMap(new Func1<Integer, Observable<Long>>() {
                                    @Override
                                    public Observable<Long> call(Integer integer) {
                                        return Observable.timer(integer * 6, TimeUnit.SECONDS);
                                    }
                                });
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {

                        AliResultBean bean = new Gson().fromJson(s, AliResultBean.class);
                        return TextUtils.equals(bean.alipay_trade_query_response.code, "10000")
                                && TextUtils.equals(bean.alipay_trade_query_response.trade_status, "TRADE_SUCCESS");
                    }
                })
                .takeUntil(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        AliResultBean bean = new Gson().fromJson(s, AliResultBean.class);
                        return TextUtils.equals(bean.alipay_trade_query_response.code, "10000")&&TextUtils.equals(bean.alipay_trade_query_response.trade_status, "TRADE_SUCCESS");
                    }
                })
                .subscribe(payPresenter.getQuerySub());
    }







    @Override
    public void aliCancle() {
        HttpServer queryserver = RetrofitUtils.getInstance().getStringClient(ConfigUtlis.getConfig("open_api_domain"));
        HttpParams queryparams = getAliParams(HttpParams.PARAMS_QUERY);
        Observable<String> queryaliPay = queryserver.getAliPay(queryparams.getParams());
//        queryaliPay.





        HttpServer server = RetrofitUtils.getInstance().getStringClient(ConfigUtlis.getConfig("open_api_domain"));
          HttpParams params = getAliParams(HttpParams.PARAMS_CANCLE);
          Observable<String> aliPay = server.getAliPay(params.getParams());
          aliPay.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Void> observable) {
                        return observable
                                .zipWith(Observable.range(1, times), new Func2<Void, Integer, Integer>() {
                                    @Override
                                    public Integer call(Void aVoid, Integer integer) {
                                        return integer;
                                    }
                                }).flatMap(new Func1<Integer, Observable<Long>>() {
                                    @Override
                                    public Observable<Long> call(Integer integer) {
                                        return Observable.timer(integer * 6, TimeUnit.SECONDS);
                                    }
                                });

                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {

                        AliResultBean bean = new Gson().fromJson(s, AliResultBean.class);
                        return TextUtils.equals(bean.alipay_trade_query_response.code, "10000");
                    }
                })
                .takeUntil(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        AliResultBean bean = new Gson().fromJson(s, AliResultBean.class);
                        return TextUtils.equals(bean.alipay_trade_query_response.code, "10000");
                    }
                })
                .subscribe(cancle);
    }

    @Override
    public void setData(GlobalBean bean) {
        globalBean = bean;
    }


    @Override
    public void wxPay(int function, long delayTime) {
        globalBean.type = function;
        HttpParams params = null;
        HttpServer server = RetrofitUtils.getInstance().getStringClient(ConfigUtlis.getConfig("open_api_wx"));
        String address = "";
        switch (function) {
            case HttpParams.PARAMS_SCAN://扫码支付
                params = HttpParams.getInstance().getWeixinPamars(HttpParams.PARAMS_SCAN);
                address = ConfigUtlis.getConfig("wx_bar_method");
                break;
            case PARAMS_BAR://条码支付,微信刷卡支付
                params = HttpParams.getInstance().getWeixinPamars(PARAMS_BAR);
                params.put("auth_code", globalBean.auth_code);
                address = ConfigUtlis.getConfig("wx_scan_method");
                break;
        }


        StringBuffer sb = new StringBuffer();
        sb.append(params.toString()).append("&key=").append(ConfigUtlis.getConfig("wx_key"));
        String sign = Utils.MD5(sb.toString()).toUpperCase();
        params.put("sign", sign.toUpperCase());
        String body = XmlUtils.mapToXml(params.getParams());
        wxPayBody = server.getWXScanPayBody(address, body);
        wxPayBody.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .delay(delayTime, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(payPresenter);
    }

    @Override
    public void cancle(int function) {
        payPresenter.unsubscribe();
    }




}
