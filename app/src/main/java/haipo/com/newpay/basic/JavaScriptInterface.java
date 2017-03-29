package haipo.com.newpay.basic;

import android.os.Handler;
import android.webkit.JavascriptInterface;



/**
 * Title: JavaScriptInterface.java Description:
 *
 * @author ping
 * @version V1.0
 */
public class JavaScriptInterface {

    private Handler handler;

    public JavaScriptInterface(Handler handler) {
        this.handler = handler;
    }

    @JavascriptInterface
    public void paysuccess() {
        handler.sendEmptyMessage(1);
    }
    
    @JavascriptInterface
    public void payfail() {
    	   handler.sendEmptyMessage(2);
	}

}
