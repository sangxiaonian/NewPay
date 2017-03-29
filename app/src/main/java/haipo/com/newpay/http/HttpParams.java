package haipo.com.newpay.http;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import haipo.com.newpay.basic.CustomApplication;
import haipo.com.newpay.utils.ConfigUtlis;
import haipo.com.newpay.utils.DeviceUtlis;
import haipo.com.newpay.utils.Utils;


/**
 * Description：
 *
 * @Author桑小年
 * @Data：2016/11/10 10:47
 */
public class HttpParams   {

    public static HttpParams instance;
    /**
     * 条码支付:收银员使用扫码设备读取用户手机支付宝
     */
    public static final int PARAMS_BAR =1;
    /**
     * 扫码支付 :收银员通过收银台或商户后台调用支付宝接口，生成二维码
     */
    public static final int PARAMS_SCAN =2;

    /**
     * 查询交易
     */
    public static final int PARAMS_QUERY =3;
     /**
     *
     * 交易撤销
     */
    public static final int PARAMS_CANCLE =4;

    private static Map<String,String> params ;

    private HttpParams() {
        params=new TreeMap<String, String>(
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                }
        ) ;


    }

    public static HttpParams getInstance(){
        if (instance==null){
            synchronized (HttpParams.class){
                if (instance==null){
                    instance = new HttpParams();
                }
            }
        }
        params.clear();
        return instance;
    }

    public  HttpParams getSpecialParams(int code){
        params.clear();
        params.put("app_id", ConfigUtlis.getConfig("app_id"));
        params.put("seller_id",ConfigUtlis.getConfig("seller_id"));
        params.put("format",ConfigUtlis.getConfig("format"));
        params.put("charset",ConfigUtlis.getConfig("charset"));
        params.put("sign_type",ConfigUtlis.getConfig("sign_type"));
        params.put("version",ConfigUtlis.getConfig("version"));
        params.put("timestamp", Utils.getStringDate("yyyy-MM-dd HH:mm:ss"));

        switch (code){
            case PARAMS_BAR:
                params.put("method",ConfigUtlis.getConfig("bar_method"));
                break;
            case PARAMS_SCAN:
                params.put("method",ConfigUtlis.getConfig("scan_method"));
                break;
            case PARAMS_QUERY:
                params.put("method",ConfigUtlis.getConfig("query_method"));
                break;
            case PARAMS_CANCLE:
                params.put("method",ConfigUtlis.getConfig("cancle_method"));
                break;
        }
        return instance;
    }

    public HttpParams getWeixinPamars(int code){
        params.clear();
        params.put("appid",ConfigUtlis.getConfig("appid"));
        params.put("mch_id",ConfigUtlis.getConfig("mch_id"));
        params.put("nonce_str",Utils.getRandomData(15));
        params.put("sign_type",ConfigUtlis.getConfig("wx_sign_type"));
        params.put("body","海珀科技");
        params.put("out_trade_no","order"+Utils.getStringDate("yyyyMMddHHmmss"));
        params.put("total_fee","1");
        params.put("spbill_create_ip", URLEncoder.encode(DeviceUtlis.getIP(CustomApplication.getAppContext())));//终端IP
        switch (code){
            case PARAMS_BAR://条码支付,即为刷卡支付
                break;
            case PARAMS_SCAN:
                params.put("trade_type","NATIVE");
                params.put("notify_url",ConfigUtlis.getConfig("wx_notify_url"));
            break;
        }
        return instance;
    }


    public Map<String,String> getParams(){
        return params;
    }

    public void put(String key,String value){
        params.put(key,value);
    }

    public void putAll(Map<String,String> params){
        this.params.putAll(params);
    }

    public void remove(String key){
        params.remove(key);
    }

    @Override
    public String toString() {

        return encodParams();
    }

    private String encodParams(){
        StringBuffer sb = new StringBuffer();
        Set<String> set = params.keySet();

        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            String value = params.get(key);
            if (!TextUtils.isEmpty(value)) {
                sb.append(key).append("=").append(value).append("&");
            }

        }

        return sb.toString().substring(0,sb.length()-1);
    }
}
