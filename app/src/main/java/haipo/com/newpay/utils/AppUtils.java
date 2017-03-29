package haipo.com.newpay.utils;

import android.app.Activity;
import android.content.Intent;

import haipo.com.newpay.MainActivity;


/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/14 13:59
 */
public class AppUtils {
    public static void jumpToMain(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
