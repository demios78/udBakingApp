package com.snindustries.project.udacity.bake_o_bake.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

/**
 * Recycler View is bound using this adapter.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public class HeaderListAdapter<HM, V, H> extends ListBindingAdapter<V,H> {

    private final List<V> items;
    private H handler;
    private View Header;

    public HeaderListAdapter(List<HM> headerList, @NonNull List<V> items, int layoutID) {
        super(items, layoutID);
        this.items = items;
    }

    public void addItem(V item) {
        this.items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addItem(@Nullable List<V> items) {
        if (items == null) {
            return;
        }
        int oldList = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(oldList, items.size());
    }

   public void replaceAll(@Nullable List<V> items) {
        if (items == null) {
            return;
        }
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }


    @Override
    @Nullable
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

    public void setHandler(H handler) {
        this.handler = handler;
    }

    public void updateItems(List<V> items) {
        this.items.clear();
        if (items != null) {
            this.items.addAll(items);
        }
        notifyDataSetChanged();
    }


}
