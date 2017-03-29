package haipo.com.newpay.mode.bean;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/15 17:10
 */
public class AliBean {

    /**
     * 支付授权码 扫描用户信息获得
     */
    public String auth_code;

    /**
     * 交易订单号
     */
    public String out_trade_no;

    /**
     * 支付类型
     */
    public String scene;

    /**
     * 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
     */
    public String seller_id;

    /**
     * 门店编号
     */
    public String store_id;

    /**
     * 订单标题
     */
    public String subject;

    /**
     * 轮训时间
     */
    public String timeout_express;

    /**
     * 总金额
     */
    public String total_amount;



}
