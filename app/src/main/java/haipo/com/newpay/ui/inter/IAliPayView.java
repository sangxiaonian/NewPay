package haipo.com.newpay.ui.inter;

import android.graphics.Bitmap;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/18 14:55
 */
public interface IAliPayView {

    /**
     * 显示进度条
     * @param tag 进度条标示
     */
    void showProgress(String tag);


    /**
     * 隐藏进度条
     */
    void dismiss();

    /**
     * 错误界面
     * @param failReason
     */
    void showFail(String failReason);

    /**
     * 成功界面
     * @param o
     */
    void showSuccess(String o);

    /**
     * 调往扫码页面
     */
    void jumpToScan();

    /**
     * 显示二维码
     * @param bar_bitmap
     */
    void showBar(Bitmap bar_bitmap);
}
