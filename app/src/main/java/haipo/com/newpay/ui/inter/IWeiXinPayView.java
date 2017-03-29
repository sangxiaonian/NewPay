package haipo.com.newpay.ui.inter;


import haipo.com.newpay.mode.bean.BasicBean;

public interface IWeiXinPayView {

    /**
     * 展示基本信息
     *
     * @param bean
     */
    void showInfors(BasicBean bean);

    /**
     * 跳向失败页面
     *
     * @param code
     * @param msg
     */
    void doFail(String code, String msg, int function);

    /**
     * 展示二维码
     *
     * @param imgUrl
     */
    void showCode(String imgUrl);

    /**
     * 显示进度条
     *
     * @param msg
     */
    void showPro(String msg);

    /**
     * 隐藏进度条
     */
    void dismissPro();

    /**
     * 跳向成功页面
     *
     * @param function
     */
    void dosuccess(int function);
}
