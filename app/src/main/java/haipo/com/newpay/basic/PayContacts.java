package haipo.com.newpay.basic;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/14 14:33
 */
public class PayContacts {
    /**
     * 支付宝支付网址
     */
    public static String PAY_URl="http://pay.haiposoft.com/alipayapi.php";


    public static String base_url = "http://120.210.205.29:8067/";//测式环境
    public static String weixin_notify_url = base_url+"BillPay/WeixinPayXZ.ashx";//微信回调地址
    public static String weixin_mch_create_ip = "127.0.0.1";//微信订单生成机器的Ip
    public static String weixin_service = "pay.weixin.native";
    public static String weixin_query_seriver_url="unified.trade.query";
    public static String weixin_url="https://pay.swiftpass.cn/pay/";//微信支付地址
//    public static String weixin_url="https://pay.swiftpass.cn/pay/gateway";//微信支付地址
    //	public static String weixin_key="7dfc69fedd87276571e5a7a746028886";//微信支付的key值
//	public static String weixin_mch_id = "101550000804";//微信商户号
    public static String weixin_key="5cfb907241d25a4bae82241565871d01";//微信支付的key值
    public static String weixin_mch_id = "101500000819";//微信商户号

}
