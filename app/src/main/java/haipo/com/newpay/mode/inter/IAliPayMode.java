package haipo.com.newpay.mode.inter;

import android.app.Activity;

import haipo.com.newpay.mode.bean.GlobalBean;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/18 15:01
 */
public interface IAliPayMode {

    /**
     * 获取订单信息
     * @param activity 上下文
     * @param payInfo 支付信息名称
     */
    void getData(Activity activity, String payInfo);

    /**
     * 设置授权码
     * @param auth_code
     */
    void setAuth_code(String auth_code);

    /**
     * 开始支付
     * @param function 功能类型
     * @param delayTime 延时时间,单位秒
     *
     */
    void aliPay(int function, long delayTime);

    /**
     * 获取功能
     * @return
     */
    int getFunction();

    /**
     * 微信支付
     * @param function
     * @param delayTime 延时时间,单位秒
     */
    void wxPay(int function, long delayTime);

    void cancle(int function);

    GlobalBean getGloble();

    void aliQuery();

    void aliCancle();

    /**
     * 设置数据
     * @param bean 全局通用的数据
     */
    void setData(GlobalBean bean);
}
