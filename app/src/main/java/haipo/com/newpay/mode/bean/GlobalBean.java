package haipo.com.newpay.mode.bean;

import android.os.Parcel;

public class GlobalBean extends BasicBean {

	/**
	 * 卡类型
	 */
	public int type;
	/**
	 * 数据
	 */
	public String data;

	public String auth_code;
	
	
	@Override
	public String toString() {
		return "GasBean [type=" + type + ", data=" + data + ", BillNo="
				+ BillNo + ", payAmount=" + payAmount + ", phoneNumber="
				+ phoneNumber + ", function=" + function + ", cardId=" + cardId
				+ "]";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(BillNo);
		dest.writeString(payAmount);
		dest.writeString(phoneNumber);
		dest.writeInt(function);
		dest.writeString(payAmount);
		dest.writeString(cardId);
		dest.writeString(data);
		dest.writeInt(type);
		dest.writeString(auth_code);
	}

	/**
	 * 对象的创建器，
	 */
	public static final Creator<GlobalBean> CREATOR = new Creator<GlobalBean>() {

		@Override
		public GlobalBean createFromParcel(Parcel source) {
			GlobalBean bean = new GlobalBean();
			bean.BillNo = source.readString();
			bean.payAmount = source.readString();
			bean.phoneNumber = source.readString();
			bean.function = source.readInt();
			bean.payAmount = source.readString();
			bean.cardId= source.readString();
			bean.data= source.readString();
			bean.type = source.readInt();
			bean.auth_code=source.readString();
			return bean;
		}

		@Override
		public GlobalBean[] newArray(int size) {
			return new GlobalBean[0];
		}
	};

}
