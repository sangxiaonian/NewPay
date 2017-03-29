package haipo.com.newpay.http;


import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/9 11:59
 */
public interface HttpServer {


    /**
     * 微信支付
     *
     * @param body 参数
     * @return
     */
    @POST("gateway")
    Observable<String> getPayData(@Body String body);

    /**
     * 支付宝扫描支付
     * @param params
     * @return
     */
    @POST("gateway.do")
    Observable<String> getAliPay(@QueryMap Map<String, String> params);

    /**
     * 微信条码支付,刷卡支付
     * @param params
     * @return
     */
    @POST("micropay")
    Observable<String> getWXPayBody(@Body String params);
    /**
     * 微信扫码支付
     * @param params
     * @return
     */
    @POST("{address}")
    Observable<String> getWXScanPayBody(@Path("address") String address, @Body String params);
//unifiedorder
}
