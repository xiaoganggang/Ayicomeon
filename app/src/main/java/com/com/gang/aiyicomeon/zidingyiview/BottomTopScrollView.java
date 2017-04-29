package com.com.gang.aiyicomeon.zidingyiview;

/**
 * Created by Administrator on 2017/1/25.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 能够监听是否滑动到底部和顶部的scrollView
 */
public class BottomTopScrollView extends ScrollView {

    public BottomTopScrollView(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BottomTopScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomTopScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();

        //计算子view的高度
        childHeightCount = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int measuredHeight = childAt.getMeasuredHeight();
            LayoutParams params = (LayoutParams) childAt.getLayoutParams();
            childHeightCount += measuredHeight + params.topMargin + params.bottomMargin;
        }
    }

    private int childHeightCount;

    /**
     * clampedX : 表示在x方向是否可以移动 ,能移动false ,不能移动 true
     * clampedY : 表示在y方向是否可以移动
     * <p>
     * 通过上面的参数和scrollxx参数就可以知道其是否滑到底部和滑到顶部
     */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        //LogUtil.e("BottomTopScrollView", "childHeightCount : "+childHeightCount);
        if (scrollY <= 0) {
            mScrollState = ScrollState.TOP_STATE;
            if (scrollY >= childHeightCount - getMeasuredHeight()) {
                mScrollState = ScrollState.BOTH_STATE;//表示scrollview 要比childs要高,不需要scrollview 去滑动
            }
        } else if (scrollY > 0) {
            mScrollState = ScrollState.SCROLL_STATE;
            if (scrollY >= childHeightCount - getMeasuredHeight()) {
                mScrollState = ScrollState.BOTTOM_STATE;//表示scrollview 要比childs要高,不需要scrollview 去滑动
            }
        }
        //LogUtil.e("BottomTopScrollView", "scrollstate : " + mScrollState + "/ scrollY:" + scrollY);
    }

    private ScrollState mScrollState;

    public ScrollState getScrollState() {
        //LogUtil.e("BottomTopScrollView", "childHeightCount : "+childHeightCount + "/ measuredHeight : " + getMeasuredHeight());
        if (childHeightCount <= getMeasuredHeight()) {
            return ScrollState.BOTH_STATE;
        }
        return mScrollState;
    }
    /**
     * 滑动位置的状态  TOP_STATE 到达顶部, BOTTOM_STATE 到达底部 , BOTH_STATE 即到达底部也达到了顶部, SCROLL_STATE 可滑动状态
     */
    public enum ScrollState {
        TOP_STATE, BOTTOM_STATE, BOTH_STATE, SCROLL_STATE;
    }
}