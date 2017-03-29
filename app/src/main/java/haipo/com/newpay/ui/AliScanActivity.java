package haipo.com.newpay.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import haipo.com.newpay.R;
import haipo.com.newpay.basic.BaseAcivity;
import haipo.com.newpay.presenter.AliPayPresenter;
import haipo.com.newpay.presenter.inter.IAliPayPresenter;
import haipo.com.newpay.ui.inter.IAliPayView;
import haipo.com.newpay.utils.ConfigUtlis;

public class AliScanActivity extends BaseAcivity implements IAliPayView{

    private static final int REQUEST_CODE = 1;
    private ImageView imageView;
    private IAliPayPresenter payPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_scan);
        initView();
        payPresenter = new AliPayPresenter(this);
        payPresenter.getData(this, ConfigUtlis.getConfig("payInfo"));

    }

    private void initView() {

        imageView = (ImageView) findViewById(R.id.tv_ali);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                String result = bundle.getString(CodeUtils.RESULT_STRING);
                payPresenter.setAuth_code(result);

            }else {
                finish();
            }
        }else {
            finish();
        }
    }

    @Override
    public void showProgress(String tag) {

        Logger.e("bbbbbbbbbbbbbbbbbbbb");

    }

    @Override
    public void dismiss() {
        Logger.e("aaaaaaaaaaaaa");
//        dialog.dismiss();
    }

    @Override
    public void showFail(String failReason) {

    }


    @Override
    public void showSuccess(String o) {
        startActivity(new Intent(this,ReceiptSuccessActivity.class));
        finish();
    }

    @Override
    public void jumpToScan() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void showBar(Bitmap bar_bitmap) {
        imageView.setImageBitmap(bar_bitmap);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        payPresenter.cancle("");
    }


}
