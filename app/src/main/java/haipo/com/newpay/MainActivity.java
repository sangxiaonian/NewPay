package haipo.com.newpay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import haipo.com.newpay.mode.bean.GlobalBean;
import haipo.com.newpay.ui.AliScanActivity;
import haipo.com.newpay.utils.ConfigUtlis;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt_amount,bt_ali,bt_weixin,bt_aliSacn,bt_wexinScan;
    private EditText  et_amout;

    private GlobalBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_amount = (Button) findViewById(R.id.bt_amount);
        bt_ali = (Button) findViewById(R.id.bt_aliCode);
        bt_weixin= (Button) findViewById(R.id.bt_weixinCode);
        bt_weixin.setVisibility(View.GONE);
        bt_wexinScan = (Button) findViewById(R.id.bt_weixinScan);
        bt_wexinScan.setVisibility(View.GONE);
        bt_aliSacn = (Button) findViewById(R.id.bt_aliScan);
        bt_wexinScan.setOnClickListener(this);
        bt_aliSacn.setOnClickListener(this);
        bt_ali.setOnClickListener(this);
        bt_weixin.setOnClickListener(this);
        et_amout = (EditText) findViewById(R.id.et_amount);
        bt_amount.setOnClickListener(this);
        bean = new GlobalBean();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_amount:
                et_amout.setText("1");
                break;
            case R.id.bt_aliCode:
                getData(0);
                break;
            case R.id.bt_aliScan:
                getData(1);
                break;
            case R.id.bt_weixinCode:
                getData(2);
                break;
            case R.id.bt_weixinScan:
                getData(3);

                break;
        }

    }


    /**
     *
     * @param function 0:支付宝二维码 1:支付宝扫描二维码 3:微信二维码 4:微信扫描二维码
     */
    public void  getData(int function) {
        bean.function = function;
        bean.payAmount = et_amout.getText().toString();
        Intent intent = new Intent(this,AliScanActivity.class);
        intent.putExtra(ConfigUtlis.getConfig("payInfo"),bean);
        startActivity(intent);
    }









}
