package com.snindustries.project.udacity.bake_o_bake.utils;

/**
 * Allows recyclerviews to be databound.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public class AppDataBindingComponent implements android.databinding.DataBindingComponent {

    @Override
    public RecyclerViewDataBindingAdapter getRecyclerViewDataBindingAdapter() {
        return new RecyclerViewDataBindingAdapter();
    }
}
