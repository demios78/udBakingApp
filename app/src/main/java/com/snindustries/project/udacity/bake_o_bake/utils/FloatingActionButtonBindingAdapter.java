package com.snindustries.project.udacity.bake_o_bake.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.design.widget.FloatingActionButton;

/**
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public class FloatingActionButtonBindingAdapter {

    @BindingAdapter({"srcImage"})
    public static void srcCompat(FloatingActionButton button, @DrawableRes int resource) {
        button.setImageResource(resource);
    }

    @BindingAdapter({"srcImage"})
    public static void srcCompat(FloatingActionButton button, Drawable resource) {
        button.setImageDrawable(resource);
    }
}
