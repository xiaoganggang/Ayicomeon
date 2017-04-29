package com.com.gang.aiyicomeon.zidingyiview;

/**
 * Created by Administrator on 2017/1/25.
 */

import com.example.administrator.ayicomeon.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 类似于qq中的界面,能够上下的拉动
 */
public class PullToRefreshScrollView extends LinearLayout {
    private BottomTopScrollView mScrollView;
    private float downY;
    private Scroller mScroller;
    private int mScaledTouchSlop;
    private boolean isInControl = false;
    private final int REFRESH_MIN_SCROLL_HEIGH = 100;

    public PullToRefreshScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

   @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = findViewById(R.id.id_pull_to_refresh_scrollview);
        if (!(view instanceof BottomTopScrollView)) {
            throw new IllegalArgumentException("must be used by scrollview to viewgroup's child view !");
        }
        mScrollView = (BottomTopScrollView) view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LayoutParams layoutParams = (LayoutParams) mScrollView.getLayoutParams();
        layoutParams.height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        layoutParams.weight = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        //  LogUtil.e("PullToRefreshScrollView", "onTouchEvent " + event.getX());
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                float dY = (moveY - downY) / 1.5f;
                scrollBy(0, (int) -dY);
                downY = moveY;
                isInControl = false;
                break;

            case MotionEvent.ACTION_UP:
                float downY = event.getY();
                float scrollY = getScrollY();
                handlerPullToRefresh(scrollY);
                mScroller.startScroll(0, (int) scrollY, 0, (int) -scrollY, (int) Math.abs(scrollY));
                invalidate();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void handlerPullToRefresh(float scrollY) {
        if (scrollY < -REFRESH_MIN_SCROLL_HEIGH && mListener != null) {
            mListener.onRefresh();
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                float dY = moveY - downY;
                int scrollY = getScrollY();

                if (Math.abs(dY) > mScaledTouchSlop) {
                    if (!isInControl && dY > 0 && (BottomTopScrollView.ScrollState.TOP_STATE.equals(mScrollView.getScrollState()) ||
                            BottomTopScrollView.ScrollState.BOTH_STATE.equals(mScrollView.getScrollState())) && scrollY == 0) { // 上滑 ,满足该条件可下拉刷新
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);

                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    } else if (!isInControl && dY < 0 && (BottomTopScrollView.ScrollState.BOTTOM_STATE.equals(mScrollView.getScrollState()) ||
                            BottomTopScrollView.ScrollState.BOTH_STATE.equals(mScrollView.getScrollState())) && scrollY == 0) {//下滑,满足该条件可上拉加载
                        isInControl = true;
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        MotionEvent ev2 = MotionEvent.obtain(ev);
                        dispatchTouchEvent(ev);

                        ev2.setAction(MotionEvent.ACTION_DOWN);
                        return dispatchTouchEvent(ev2);
                    }
                }
                break;

            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // LogUtil.e("PullToRefreshScrollView", "onInterceptTouchEvent");
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                float dY = moveY - downY;
                if (Math.abs(dY) > mScaledTouchSlop) {
                    if (dY > 0 && (BottomTopScrollView.ScrollState.TOP_STATE.equals(mScrollView.getScrollState()) ||
                            BottomTopScrollView.ScrollState.BOTH_STATE.equals(mScrollView.getScrollState()))) { // 上滑 ,满足该条件可下拉刷新
                        return true;
                    } else if (dY < 0 && (BottomTopScrollView.ScrollState.BOTTOM_STATE.equals(mScrollView.getScrollState()) ||
                            BottomTopScrollView.ScrollState.BOTH_STATE.equals(mScrollView.getScrollState()))) {//下滑,满足该条件可上拉加载
                        return true;
                    }
                }
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public interface OnPullToRefreshListener {
        public void onRefresh();
    }

    private OnPullToRefreshListener mListener;

    public void setOnPullToRefreshListener(OnPullToRefreshListener listener) {
        this.mListener = listener;
    }
}