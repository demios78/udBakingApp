package com.snindustries.project.udacity.bake_o_bake.utils;

import android.databinding.*;
import android.text.TextUtils;
import android.widget.ImageView;

import com.snindustries.project.udacity.bake_o_bake.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Images are loaded using Picasso library.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public class PicassoDatabindingAdapter {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            Picasso.get()
                    .load(R.drawable.ic_camera_roll)
                    .placeholder(R.drawable.ic_camera_roll)
                    .into(view);
            return;
        }
        Picasso.get()
                .load(new File(imageUrl))
                .placeholder(R.drawable.ic_camera_roll)
                .error(R.drawable.ic_broken_image)
                .into(view, new Callback() {
                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onSuccess() {

                            }
                        }
                );
    }

}
