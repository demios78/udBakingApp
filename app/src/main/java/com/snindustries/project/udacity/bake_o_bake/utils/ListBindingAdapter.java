package com.snindustries.project.udacity.bake_o_bake.utils;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Recycler View is bound using this adapter.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public class ListBindingAdapter<V, H> extends RecyclerBindingAdapter {

    private final H handler;
    private final List<V> items;

    public ListBindingAdapter(@NonNull List<V> items, @NonNull H handler, int layoutID) {
        super(layoutID);
        this.items = items;
        this.handler = handler;
    }

    public void addItem(V item) {
        this.items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    @Override
    protected H getHandler(int position) {
        return handler;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    protected V getViewModel(int position) {
        return items.get(position);
    }

    public void removeItem(V item) {
        int index = this.items.indexOf(item);
        if (index >= 0) {
            items.remove(item);
            notifyItemRemoved(index);
        }
    }

    public void updateItems(List<V> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }
}
