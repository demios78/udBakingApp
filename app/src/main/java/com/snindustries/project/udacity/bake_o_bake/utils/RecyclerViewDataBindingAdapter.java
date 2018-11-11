package com.snindustries.project.udacity.bake_o_bake.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Binds adapter to recycler.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public class RecyclerViewDataBindingAdapter {

    /**
     * Binds the data to the {@link android.support.v7.widget.RecyclerView.Adapter}, sets the
     * recycler view on the adapter, and performs some other recycler view initialization.
     *
     * @param recyclerView passed in automatically with the data binding
     * @param adapter      must be explicitly passed in
     * @param items        must be explicitly passed in
     * @param handler      handles actions on data
     */
    @BindingAdapter(value = {"adapter",  "handler"})
    public void bindRecyclerViewToAdapter(RecyclerView recyclerView, ListBindingAdapter adapter,  Object handler) {
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

        if (adapter != null && handler != null) {
            adapter.setHandler(handler);
        }
    }
}