package haipo.com.newpay.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自定义控件，用来进行页面滑动
 */
public class SlideLayoutNonePoint extends ViewGroup {
    private Scroller mScroller;

    private int mCurScreen;
    private int mDefaultScreen = 0;

    public SlideLayoutNonePoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayoutNonePoint(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mScroller = new Scroller(context);

        mCurScreen = mDefaultScreen;
        ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
                // 很明显 0 - 320 | 320 - 640 | 640 - 960 ...（假设屏幕宽320）
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("SlideLayoutWithPoint only canmCurScreen run at EXACTLY mode!");
        }

        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {

        }

        // The children are given the same width and height as the SlideLayoutWithPoint
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }

        // x坐标 y坐标 移动到第几屏
        scrollTo(mCurScreen * width, 0);
    }

    public void snapToScreen(int whichScreen) {
        // get the valid layout page
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (whichScreen * getWidth())) {
            final int delta = whichScreen * getWidth() - getScrollX();

            // 开始滚动 x，y，x方向移动量，y方向移动量，滚动持续时间，负数往左滚
            mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 1);
            mCurScreen = whichScreen;
            postInvalidate(); // Redraw the layout
        }
    }

    public void snapToScreenQuick(int whichScreen) {
        // get the valid layout page
        whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
        if (getScrollX() != (whichScreen * getWidth())) {
            final int delta = whichScreen * getWidth() - getScrollX();

            // 开始滚动 x，y，x方向移动量，y方向移动量，滚动持续时间，负数往左滚
            mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) / 10);
            mCurScreen = whichScreen;
            postInvalidate(); // Redraw the layout
        }
    }

    /**
     * 获得当前屏幕位置
     *
     * @return
     */
    public int getCurrentScreenIndex() {
        return mCurScreen;
    }

    @Override
    public void computeScroll() {
        // 如果返回true，表示动画还没有结束 因为前面startScroll，所以只有在startScroll完成时 才会为false
        if (mScroller.computeScrollOffset()) {
            // 产生了动画效果 每次滚动一点
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    public void setCurrentIndex(int index){
    	mCurScreen=index;
    	postInvalidate();
    }

}
