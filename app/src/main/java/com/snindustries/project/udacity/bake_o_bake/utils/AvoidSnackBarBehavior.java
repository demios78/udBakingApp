package com.snindustries.project.udacity.bake_o_bake.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Used by menu to avoid elements that popup on screen.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
@SuppressWarnings("unused")
public class AvoidSnackBarBehavior extends CoordinatorLayout.Behavior<View> {

    public AvoidSnackBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }


}
