package com.demo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import com.example.cardmanagedemo.R;
import java.util.HashMap;

public class CardLinearLayout extends LinearLayout {

    private int expandIndex = -1;
    private HashMap<Integer, Bounds> boundsHashMap;
    private float carEvPercnet = 0.5f;//每个卡片的错开距离是一张卡片高度的百分比
    private float animationPercent = 0;
    private int animationDuration = 300;
    private OnAnimationListen onAnimationListen;
    private Animation animationShadowOut, animationShadowIn;

    public CardLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        boundsHashMap = new HashMap<>();
        animationShadowOut = AnimationUtils.loadAnimation(getContext(), com.google.android.material.R.anim.abc_fade_out);
        animationShadowOut.setDuration(animationDuration);

        animationShadowIn = AnimationUtils.loadAnimation(getContext(), com.google.android.material.R.anim.abc_fade_in);
        animationShadowIn.setDuration(animationDuration);


    }

    public Bounds getBunds(int index) {
        if (boundsHashMap.containsKey(index)) {
            return boundsHashMap.get(index);
        } else {
            Bounds bounds = new Bounds(index);
            boundsHashMap.put(index, bounds);
            return bounds;
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        Bounds bounds = getBunds(getChildCount());
    }


    boolean mesured = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mesured == true) {//只需要测量一次
            return;
        }
        mesured = true;
        int childCount = getChildCount();
        int rootWidth = getWidth();
        int rootHeight = getHeight();

        if (childCount > 0) {
            View child0 = getChildAt(0);
            int modeWidth = MeasureSpec.getMode(child0.getMeasuredWidth());
            int sizeWidth = MeasureSpec.getSize(child0.getMeasuredWidth());

            int modeHeight = MeasureSpec.getMode(child0.getMeasuredHeight());
            int sizeHeight = MeasureSpec.getSize(child0.getMeasuredHeight());

            if (childCount > 0) {
                for (int i = 0; i < childCount; i++) {
                    View childView = getChildAt(i);
                    childView.measure(MeasureSpec.makeMeasureSpec(sizeWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(sizeHeight, MeasureSpec.EXACTLY));
                    int top = (int) (i * (sizeHeight * carEvPercnet));
                    getBunds(i).setTop(top);
                    getBunds(i).setCurrentTop(top);
                    getBunds(i).setLastCurrentTop(top);
                    getBunds(i).setHeight(sizeHeight);
                }

            }

        }
    }

    @Override
    protected void onLayout(boolean changed, int sl, int st, int sr, int sb) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View view = getChildAt(i);
                int mWidth = view.getMeasuredWidth();
                int mw = MeasureSpec.getSize(mWidth);
                int l = 0, r = l + mw;
                view.layout(l, getBunds(i).getCurrentTop(), r, getBunds(i).getCurrentTop() + getBunds(i).getHeight());
            }
        }
    }


    private int lastExpand = expandIndex;
    boolean isFirst = true;

    public void expandItem(int willExpandIndex) {

        for (int i = 0; i < getChildCount(); i++) {
            Bounds bounds = getBunds(i);
            if (i <= willExpandIndex) {
                //这样会造成前面的视图突然变化
                int targetTop = (int) (bounds.getTop());
                bounds.setTargetTop(targetTop);
            } else {
                if (i == (willExpandIndex + 1)) {
                    int targetTop = (int) (bounds.getTop() + bounds.getHeight() * carEvPercnet);
                    bounds.setTargetTop(targetTop);
                } else {
                    bounds.setTargetTop((int) (getBunds(i - 1).getTargetTop() + bounds.getHeight() * carEvPercnet));
                }
            }
        }


        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1.f);
        valueAnimator.setDuration(animationDuration);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                for (int i = 0; i < getChildCount(); i++) {
                    Bounds bns = getBunds(i);
                    bns.setLastCurrentTop(bns.getCurrentTop());
                    //应该新增新增一个记住最后的位置
                    isFirst = false;
                }
            }
        });
        valueAnimator.addUpdateListener(animation -> {
            animationPercent = (float) animation.getAnimatedValue();
            //计算出动画应该出现的位置
            for (int i = 0; i < getChildCount(); i++) {
                Bounds bns = getBunds(i);
                if (isFirst) {
                    int dis = bns.getTargetTop() - bns.getTop();
                    int delta = (int) (dis * animationPercent);
                    int currentTop = bns.getTop() + delta;
                    bns.setCurrentTop(currentTop);
                } else {

                    int dis = bns.getTargetTop() - bns.getLastCurrentTop();
                    int delta = (int) (dis * animationPercent);
                    int currentTop = bns.getLastCurrentTop() + delta;
                    bns.setCurrentTop(currentTop);
                }
            }
            requestLayout();
        });
        valueAnimator.start();
        if (isFirst) {
            View v = getChildAt(getChildCount() - 1).findViewById(R.id.viewWalletContainer);
            MyAnimationUtils.animationHideview(v, AnimationUtils.loadAnimation(getContext(), com.google.android.material.R.anim.abc_slide_out_bottom));
        }

        this.dealShadow(willExpandIndex);
        this.expandIndex = willExpandIndex;

    }

    private void dealShadow(int willExpandIndex) {

        if (expandIndex > 0) {
            MyAnimationUtils.animationShowView(getChildAt(expandIndex - 1).findViewById(R.id.imageViewShadow), animationShadowOut);
            MyAnimationUtils.animationShowView(getChildAt(expandIndex).findViewById(R.id.imageViewTopCover), animationShadowOut);
        }

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (i == (willExpandIndex - 1)) {
                MyAnimationUtils.animationShowView(view.findViewById(R.id.imageViewShadow), animationShadowIn);
            } else {
                view.findViewById(R.id.imageViewShadow).setVisibility(View.INVISIBLE);
            }
            if (i == willExpandIndex) {

//                MyAnimationUtils.animationShowView(  view.findViewById(R.id.imageViewTopCover),animationShadowIn);

                view.findViewById(R.id.imageViewTopCover).setVisibility(View.VISIBLE);


            } else {
                view.findViewById(R.id.imageViewTopCover).setVisibility(View.INVISIBLE);
            }


        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public static class OnAnimationListen {
        public void onChange(float percent, int top) {
        }
    }

}
