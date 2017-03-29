package haipo.com.newpay.basic;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import haipo.com.newpay.R;
import haipo.com.newpay.utils.AppUtils;


public class TimeoutShow implements View.OnClickListener {
    private static final long TIMEOUT_SECOND = 60000 * 5;
    private Activity activity = null;
    private AlertDialog alertDialog = null;
    private Timer timer = null;
    private TextView tvTimeoutMessage = null;

    public TimeoutShow(Activity activity) {
        this.activity = activity;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            // Handler处理消息
            if (msg.what > 0) {
                tvTimeoutMessage.setText(msg.what + " 秒");
            } else {
                // 结束Timer计时器
                timer.cancel();
                timer = null;
                closeTimer();
                alertDialog.dismiss();
                AppUtils.jumpToMain(activity);
            }
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showTimeoutDialog();
        }
    };

    public void startTimer() {
        handler.postDelayed(runnable, TIMEOUT_SECOND);
    }

    public void resetTimer() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, TIMEOUT_SECOND);
    }

    public void closeTimer() {
        handler.removeCallbacks(runnable);
    }

    private void startTicks() {
        //创建一个Timer定时器
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            // 倒数10秒
            int i = 10;

            @Override
            public void run() {
                // 定义一个消息传过去
                Message msg = new Message();
                msg.what = i--;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(timerTask, 0, 1000);// 0秒后开始倒计时，倒计时间隔为1秒
    }

    private void showTimeoutDialog() {
        if (!activity.isFinishing()) {
            closeTimer();
            buildTimeoutWindow();
            startTicks();
        }
    }

    private void buildTimeoutWindow() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.show_timeout_activity, null);
        tvTimeoutMessage = (TextView) layout.findViewById(R.id.tvTimeoutMessage);
        ((ImageButton) layout.findViewById(R.id.imgButtonTimeout)).setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        alertDialog = builder.create();
        alertDialog.setView(layout, 0, 0, 0, 0);
        alertDialog.show();

        Window window = alertDialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 800;
        params.height = 450;
        params.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(params);

    }

    @Override
    public void onClick(View v) {
        timer.cancel();
        timer = null;
        resetTimer();
        alertDialog.dismiss();
    }


}
