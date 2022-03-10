package com.demo.widget;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by wangzy on 15/6/5.
 */
public class MyAnimationUtils {


    public static void playAnimationOnView(View view, int animation) {

        view.startAnimation(AnimationUtils.loadAnimation(view.getContext(), animation));
    }


    public static void animationShowView(final View view, final Animation showAnimation) {

        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        if (null == showAnimation) {
            view.setVisibility(View.VISIBLE);
        }

        showAnimation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        view.startAnimation(showAnimation);
    }

    public static void animationShowViewNoListener(final View view, final Animation showAnimation) {

        if (view.getVisibility() == View.VISIBLE) {
            return;
        }

        if (null == showAnimation) {
            view.setVisibility(View.VISIBLE);
        }

        view.setVisibility(View.VISIBLE);
        view.startAnimation(showAnimation);
    }


    public static void animationHideview(final View view, final Animation showAnimation) {
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        if (null == showAnimation) {
            view.setVisibility(View.GONE);
        }

        showAnimation.setAnimationListener(new AnimationListenerAdapter() {
            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(showAnimation);
    }



    public static void animationHideviewNoListener(final View view, final Animation showAnimation) {
        if (view.getVisibility() != View.VISIBLE) {
            return;
        }
        if (null == showAnimation) {
            view.setVisibility(View.GONE);
        }

        view.startAnimation(showAnimation);
    }

}
