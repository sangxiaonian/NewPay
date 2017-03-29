package haipo.com.newpay.mode.bean;

import java.util.List;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/22 10:17
 */
public class AliResultBean {



    /**
     * 交易返回数据
     */
    public AlipayTradePayResponseBean alipay_trade_pay_response;
    public AlipayTradePayResponseBean alipay_trade_precreate_response;
    public AlipayTradePayResponseBean alipay_trade_query_response;
    /**
     * 签名加密数据
     */
    public String sign;




    public static class AlipayTradePayResponseBean {
        /**
         * 返回码
         */
        public String code;
        /**
         * 返回信息
         */
        public String msg;

        /**
         * buyer_logon_id
         */
        public String buyer_logon_id;
        /**
         * 买家付款的金额
         */
        public String buyer_pay_amount;
        /**
         * 买家在支付宝的用户id
         */
        public String buyer_user_id;
        /**
         * 	交易支付时间
         */
        public String gmt_payment;
        /**
         * 交易中可给用户开具发票的金额
         */
        public String invoice_amount;
        public String open_id;
        /**
         * 商户订单号
         */
        public String out_trade_no;
        /**
         * 使用积分宝付款的金额
         */
        public String point_amount;
        /**
         * 实收金额
         */
        public String receipt_amount;
        /**
         * 业务返回码
         */
        public String sub_code;

        /**
         * 业务返回描述码
         */
        public String sub_msg;

        /**
         * 交易金额
         */
        public String total_amount;
        /**
         * 支付宝交易号
         */
        public String trade_no;

        /**
         * 二维码
         */
        public String qr_code;


        public List<FundBillListBean> fund_bill_list;
        public String trade_status;


        public static class FundBillListBean {


            public String amount;
            public String fund_channel;


        }
    }
}
