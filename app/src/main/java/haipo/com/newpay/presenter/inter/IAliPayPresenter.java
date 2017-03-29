package haipo.com.newpay.presenter.inter;

import android.app.Activity;

import rx.Subscriber;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/1118 14:57
 */
public interface IAliPayPresenter   {

    /**
     * 获取支付信息
     * @param payInfo
     */
    void getData(Activity context, String payInfo);

    /**
     * 设置授权码
     * @param result
     */
    void setAuth_code(String result);

    /**
     * 获取到上衣页面传递过来的数据时候调用
     * @param function 数据类型
     */
    void onGetData(int function);

    void cancle(String tag);

    Subscriber<? super String> getQuerySub();
}
