package haipo.com.newpay.basic;

import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.ZApplication;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import haipo.com.newpay.utils.ConfigUtlis;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/15 13:43
 */
public class CustomApplication extends ZApplication {

    private static Context context;

    /**
     * 获取Application的Context
     *
     * @return
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * 配置信息
     */
    public Hashtable<String, String> property = new Hashtable<>();

    /**
     * 加载配置信息
     *
     * @param fileName 配置信息名称
     */
    public void loadProperty(String fileName) {
        property = ConfigUtlis.getInstance().loadConfig(fileName, this);
    }

    /**
     * 获取配置信息
     *
     * @return
     */
    public Hashtable<String, String> getProperty() {
        return property;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Logger.init("PING");
        ZXingLibrary.initDisplayOpinion(this);
        loadProperty("zfbinfo.properties");
    }


    /**
     * 从内存中获取文件
     *
     * @param context
     * @param fileName
     * @param path
     * @return
     */
    public static boolean copyFileFromAssets(Context context, String fileName,
                                             String path) {
        boolean copyIsFinish = false;
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);

            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG", "[copyFileFromAssets] IOException " + e.toString());
        }
        return copyIsFinish;
    }


}
