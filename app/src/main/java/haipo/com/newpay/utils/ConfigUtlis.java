package haipo.com.newpay.utils;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import haipo.com.newpay.basic.CustomApplication;


/**
 * Description：加载和操作配置信息的类
 *
 * @Author：桑小年
 * @Data：2016/11/9 10:10
 */
public class ConfigUtlis {

    private static ConfigUtlis configUtlis;
    private Properties properties;
    private ConfigUtlis(){
          properties = new Properties();
    }

    public static ConfigUtlis getInstance() {

        if (configUtlis == null) {
            synchronized (ConfigUtlis.class) {
                if (configUtlis == null) {
                    configUtlis = new ConfigUtlis();
                }
            }
        }

        return configUtlis;
    }


    /**
     * 加载配置信息
     * @param fileName 配置文件名称
     * @param context 上下文
     * @return
     */
    public  Hashtable<String,String> loadConfig(String fileName, Context context) {
        Hashtable<String,String> table = new Hashtable<String,String>();
        try {
            InputStream is = context.getAssets().open(fileName);
            properties.load(is);
            Set<Object> keySet = properties.keySet();

            Logger.i( "执行到了");
            for (Object key : keySet) {
                table.put(key.toString(),properties.get(key).toString());
                Logger.i( key.toString()+" = "+properties.get(key).toString());
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * 获取所有配置参数
     * @return
     */
    public static Hashtable<String ,String> getAlltConfig(){
        return  ((CustomApplication)CustomApplication.getAppContext()).getProperty();
    }

    /**
     * 根据key值获取系统参数
     * @param key key值
     * @return
     */
    public static String getConfig(String key){
        return  getAlltConfig().get(key);
    }


    public static String getAssessPem(String pemName){
        String result = "";
        try {
            InputStream is = CustomApplication.getAppContext().getAssets().open(pemName);
            ByteArrayOutputStream bos =new ByteArrayOutputStream();




            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len=is.read(bytes))>0){
                bos.write(bytes,0,len);
            }
           result= bos.toString();
            FileReader fr = new FileReader(result);
            ByteArrayOutputStream bos1 =new ByteArrayOutputStream();


        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.i(result);

        return result;
    }


}
