package haipo.com.newpay.http;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/14 17:11
 */
public class OkHttpUtils {

    public static final MediaType JSON
            = MediaType.parse("*/*; charset=utf-8");

    public static Call getClient(String url, String body){

        OkHttpClient client = new OkHttpClient();

        return client.newCall(creatBodyRequest(url,body));

    }

    public static Request creatBodyRequest(String url,String body){
        RequestBody body1 = RequestBody.create(JSON,body);
        Request request = new Request.Builder()
                .url(url)
                .post(body1)
                .build();

        return request ;

    };
}
