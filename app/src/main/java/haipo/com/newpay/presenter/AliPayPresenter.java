package haipo.com.newpay.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import haipo.com.newpay.R;
import haipo.com.newpay.basic.CustomApplication;
import haipo.com.newpay.http.HttpParams;
import haipo.com.newpay.mode.AliPayMode;
import haipo.com.newpay.mode.bean.AliResultBean;
import haipo.com.newpay.mode.bean.GlobalBean;
import haipo.com.newpay.mode.inter.IAliPayMode;
import haipo.com.newpay.presenter.inter.IAliPayPresenter;
import haipo.com.newpay.ui.inter.IAliPayView;
import haipo.com.newpay.utils.ConfigUtlis;
import haipo.com.newpay.utils.Utils;
import haipo.com.newpay.utils.XmlUtils;
import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/18 14:59
 */
public class AliPayPresenter extends Subscriber<String> implements IAliPayPresenter {

    private IAliPayView view;
    private IAliPayMode mode;


    private Subscriber<String> query ;


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

        }

    };

    public AliPayPresenter(IAliPayView view) {
        this.view = view;
        mode = new AliPayMode(this);

    }

    @Override
    public void getData(Activity activity, String payInfo) {

        Parcelable parcelable = activity.getIntent().getParcelableExtra(payInfo);
        if (!TextUtils.isEmpty(payInfo) && TextUtils.equals(ConfigUtlis.getConfig("payInfo"), payInfo)) {
            GlobalBean globalBean = (GlobalBean) parcelable;
            globalBean.BillNo = "order" + Utils.getStringDate("yyyyMMddHHmmss");
            mode.setData(globalBean);
            onGetData(globalBean.function);
        }


    }

    @Override
    public void setAuth_code(String result) {
        mode.setAuth_code(result);
        switch (mode.getFunction()) {
            case 0:
                mode.aliPay(HttpParams.PARAMS_SCAN,0);
                break;
            case 1:
                mode.aliPay(HttpParams.PARAMS_BAR,0);
                break;
            case 2:
                mode.wxPay(HttpParams.PARAMS_SCAN,0);
                break;
            case 3:
                mode.wxPay(HttpParams.PARAMS_BAR,0);
                break;
        }

    }

    @Override
    public void onGetData(int function) {
        switch (function) {
            case 1://条码支付,调往扫码页面
            case 3://微信条码支付(刷卡支付)
                view.jumpToScan();
                break;
            case 0://扫码支付,生成二维码
                mode.aliPay(HttpParams.PARAMS_SCAN,0);
                break;
            case 2:
                mode.wxPay(HttpParams.PARAMS_BAR,0);
                break;
        }


    }

    @Override
    public void cancle(String tag) {
        unsubscribe();

        if (query!=null&&!query.isUnsubscribed()) {
            query.unsubscribe();
        }

        if (cancle!=null&&cancle.isUnsubscribed()){
            cancle.unsubscribe();
        }
    }

    @Override
    public Subscriber<? super String> getQuerySub() {

        return query=new Subscriber<String>() {
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
                paySuccess(s);
            }

        };
    }

    @Override
    public void onStart() {
        view.showProgress(mode.getFunction()+"");
    }

    @Override
    public void onCompleted() {
        view.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        view.dismiss();
        e.printStackTrace();
        view.showFail(e.getLocalizedMessage());
    }

    @Override
    public void onNext(String o) {
        view.dismiss();
        int function = mode.getFunction();
        switch (function) {
            case 0://支付宝扫码支付
            case 1://支付宝条码支付
                dealAli(o,mode.getGloble().type);
                break;
            case 2://微信扫码支付
            case 3://微信条码支付
                dealwx(o,function);
                break;
        }


    }

    private void dealwx(String respon,int function) {
        String return_code = XmlUtils.getSpecialValue(respon, "return_code");
        String result_code = XmlUtils.getSpecialValue(respon, "result_code");

        String return_msg = XmlUtils.getSpecialValue(respon, "return_msg");
        String err_code_des = XmlUtils.getSpecialValue(respon, "err_code_des");

        if (TextUtils.isEmpty(respon)) {
            //TODO 查询订单支付状态
        } else if (!TextUtils.equals("SUCCESS", return_code)) {
            view.showFail(respon);
        } else if (!TextUtils.equals("SUCCESS", result_code)) {
            view.showFail(respon);
        } else {
            if (function==2){
                Bitmap mBitmap = CodeUtils.createImage(XmlUtils.getSpecialValue(respon, "code_url"), 400, 400, BitmapFactory.decodeResource(CustomApplication.getAppContext().getResources(), R.mipmap.wx_logo));
                view.showBar(mBitmap);
            }else if (function==3){
                view.showSuccess(respon);
            }else {
                view.showFail("支付失败,请重试");
            }

        }

    }

    private void dealAli(String o,int function) {
        try {
            AliResultBean bean = new Gson().fromJson(o,AliResultBean.class);
            AliResultBean.AlipayTradePayResponseBean response = null;
            switch (function) {
                case HttpParams.PARAMS_SCAN://扫码支付
                    response = bean.alipay_trade_precreate_response ;
                    break;
                case HttpParams.PARAMS_BAR://条码支付
                    response = bean.alipay_trade_pay_response ;
                    break;
                case HttpParams.PARAMS_QUERY:
                    Logger.e("---------结束"+ Utils.getStringDate("ss秒")+"----------------");
                    break;
                case HttpParams.PARAMS_CANCLE:
                    break;
                default:
                    view.showFail("支付失败,请重试");
                    return;
            }

            if ("10000".equals(response.code)){
                if (HttpParams.PARAMS_SCAN==function){
                    Bitmap mBitmap = CodeUtils.createImage(response.qr_code, 400, 400, BitmapFactory.decodeResource(CustomApplication.getAppContext().getResources(), R.mipmap.ali_logo));
                    view.showBar(mBitmap);
                    Logger.e("---------开始"+ Utils.getStringDate("ss秒")+"----------------");

                    mode.aliQuery();
                }else if (HttpParams.PARAMS_BAR==function){
                    view.showSuccess(o);
                }else {
                    view.showFail("支付失败,请重试");
                }

            }else {
                view.showFail(response.sub_msg);
            }
        } catch (JsonSyntaxException e) {
            view.showFail("网路异常,请返回重试");
            e.printStackTrace();
        }

    }


    /**
     * 查询支付成功
     * @param s
     */
    public void paySuccess(String s) {
        view.showSuccess(s);
    }
}
