package haipo.com.newpay.mode.inter;

import android.os.Parcelable;

public interface IWeiXinData {

	/**
	 * 设置支付信息
	 * @param extra
	 */
	void setPayInfor(Parcelable extra);

	/**
	 * 获取基本信息
	 * @return
	 */
	Parcelable getData();

	/**
	 * 创建二维码链接
	 */
	void creatCode();

	/**
	 * 获取查询支付状态的请求体
	 * @return
	 */
	String getQueryBody();

	
}
