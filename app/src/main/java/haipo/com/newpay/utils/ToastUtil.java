package haipo.com.newpay.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtil {
	private static Toast toast = null;

	public static void showTextToast(Context context, String msg) {
		if (toast == null) {
			toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
	public static void showTextToastById(Context context, int msg) {
		if (toast == null) {
			toast = Toast.makeText(context, context.getResources().getString(msg), Toast.LENGTH_SHORT);
		} else {
			toast.setText(msg);
		}
		toast.show();
	}
}
