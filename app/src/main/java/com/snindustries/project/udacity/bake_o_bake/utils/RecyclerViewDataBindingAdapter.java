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
     * @param data         must be explicitly passed in
     */
    @BindingAdapter(value = {"adapter", "data"})
    public void bindRecyclerViewToAdapter(RecyclerView recyclerView, ListBindingAdapter adapter, List<?> items) {
        recyclerView.setAdapter(adapter);
        adapter.updateItems(items);
    }
}