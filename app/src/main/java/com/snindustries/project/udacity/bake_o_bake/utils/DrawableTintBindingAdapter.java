package com.snindustries.project.udacity.bake_o_bake.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.Button;

/**
 * @author Shaaz Noormohammad
 * (c) 11/6/18
 */
public class DrawableTintBindingAdapter {

    @BindingAdapter(value = {"drawableTint"})
    public static void setTintToDrawable(Button view, @ColorInt int color) {
        for (Drawable drawable : view.getCompoundDrawables()) {
            if (drawable != null) {
                Drawable wrap = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(wrap, color);
            }
        }
    }
}
