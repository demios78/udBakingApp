package com.snindustries.project.udacity.bake_o_bake.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Shaaz Noormohammad
 * (c) 11/13/18
 */
public class BindingHeaderAdapter<V, H> extends ListBindingAdapter<V, H> {

    private static final int HEADER = 1;
    private View header;

    public BindingHeaderAdapter(@NonNull List<V> items, int layoutID) {
        super(items, layoutID);
    }


    @Nullable
    @Override
    protected H getHandler(int position) {
        if (header != null) {
            if (position == 0) {
                return null;
            } else {
                return super.getHandler(position - 1);
            }
        }
        return super.getHandler(position);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (header != null ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (header != null && position == 0) {
            return HEADER;
        }
        return super.getItemViewType(position);
    }

    @Override
    protected V getViewModel(int position) {
        if (header != null) {
            if (position == 0) {
                return null;
            } else {
                return super.getViewModel(position - 1);
            }
        }
        return super.getViewModel(position);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        if (header != null && position == 0) {
            //Do nothing
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new DummyViewHolder(header);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    public void setHeaderView(View header) {
        this.header = header;
    }

    private static class DummyViewHolder extends BindingViewHolder {

        DummyViewHolder(View header) {
            super(header);
        }

        @Override
        protected void bind() {
            //
        }

        @Override
        protected void onFailToRecycleView() {
            //
        }

        @Override
        protected void setHandler(Object handler) {
            //
        }

        @Override
        protected void setViewModel(Object viewModel) {
            //
        }

        @Override
        protected void unbind() {
            //
        }
    }
}
