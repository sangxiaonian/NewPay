package haipo.com.newpay.http;

import android.text.TextUtils;

import haipo.com.newpay.basic.CustomApplication;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/9 15:11
 */
public class RetrofitUtils<T> {

    private static RetrofitUtils utils;


    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        if (utils == null) {
            synchronized (RetrofitUtils.class) {
                if (utils == null) {
                    utils = new RetrofitUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 获取默认的Gson
     *
     * @param basUrl
     * @return
     */
    public HttpServer getClient(String basUrl) {
        return getClient(basUrl,null);
    }

    public HttpServer getClient(String basUrl,String pemName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(basUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOKClient(pemName))
                .build();
        return retrofit.create(HttpServer.class);
    }

    /**
     * 获取String的Client
     *
     * @param basUrl
     * @return
     */
    public HttpServer getStringClient(String basUrl) {
        return getStringClient(basUrl, null);
    }

    /**
     * 带有证书的https请求
     * @param basUrl 地址
     * @param pemName 证书名称
     * @return
     */
    public HttpServer getStringClient(String basUrl, String pemName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(basUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getOKClient(pemName))
                .build();

        return retrofit.create(HttpServer.class);
    }


    public OkHttpClient getOKClient(String penName) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okClient = new OkHttpClient.Builder();
        okClient.addInterceptor(logging);

        if (!TextUtils.isEmpty(penName)) {

            okClient.socketFactory(SSLContextUtil.getSSLContext(CustomApplication.getAppContext(), penName).getSocketFactory());

            okClient.hostnameVerifier(SSLContextUtil.HOSTNAME_VERIFIER);
        }

        return okClient.build();
    }
}
