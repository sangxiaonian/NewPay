package haipo.com.newpay.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import haipo.com.newpay.utils.AppUtils;


/**
 * Created by ping on 2016/4/29.
 */
public class BaseAcivity extends Activity {

    private int pageCount;
    private SlideLayoutNonePoint pageSlideLayout;
    public int pageIndex;
    public Activity cnt;

    private  TimeoutShow timeoutShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cnt = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (timeoutShow != null) {
            timeoutShow.closeTimer();
            timeoutShow = null;
        }
        timeoutShow = new  TimeoutShow(this);
        timeoutShow.startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timeoutShow != null) {
            timeoutShow.closeTimer();
            timeoutShow = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeoutShow != null) {
            timeoutShow.closeTimer();
            timeoutShow = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_UP
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            timeoutShow.resetTimer();
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 初始化SlideLayoutNonePoint
     *
     * @param id         控件Id
     * @param pageCounts 总页面数
     */
    public void setSlideContentView(int id, int pageCounts) {
        pageCount = pageCounts;
        pageSlideLayout = (SlideLayoutNonePoint) findViewById(id);
    }

    /**
     * 滑动到第page个页面
     *
     * @param page 页面数
     */
    public void jumbToPage(int page) {

        if (pageSlideLayout != null && isValidPageIndex(page)) {
            pageSlideLayout.snapToScreen(page);
            pageIndex = page;
        }

    }

    /**
     * 判断页面是否超过
     *
     * @param index
     * @return
     */
    private boolean isValidPageIndex(int index) {
        return (index >= 0) && (index < pageCount);
    }

    /**
     * 结束当前页面，跳往主页面
     *
     * @param con
     */
    public void jumpToMain(Activity con) {
        timeoutShow.closeTimer();
        AppUtils.jumpToMain(this);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        timeoutShow.resetTimer();
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            jumpToMain(cnt);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void setCurrentIndex(int index) {
        if (isValidPageIndex(index)) {
            pageSlideLayout.setCurrentIndex(index);
        }
    }

    @Override
    public void finish() {
        super.finish();

    }

    /**
     * 跳转到系一个界面的执行动画
     */
    public void next() {

    }

}
