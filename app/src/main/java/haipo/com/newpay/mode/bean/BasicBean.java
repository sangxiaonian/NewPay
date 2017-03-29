package haipo.com.newpay.mode.bean;

import android.os.Parcelable;

public abstract class BasicBean  implements Parcelable {
	/**
	 * 账单号
	 */
	public String BillNo="";
	
	/**
	 * 支付金额 单位:分
	 */
	public String payAmount = "";
	
	/**
	 * 手机号
	 */
	public String phoneNumber = "";
	
	/**
	 *功能模块
	 *   0 支付宝扫码支付:1 支付宝条码支付 2 微信扫码支付:3 ,微信条码支付
	 */
	public int function=-1;

	/**
	 * 卡号
	 */
	public String cardId;


	@Override
	public String toString() {
		return "BasicBean [BillNo=" + BillNo + ", payAmount=" + payAmount
				+ ", phoneNumber=" + phoneNumber + ", function=" + function
				+ "]";
	} 
	
	
	
	
	
}
