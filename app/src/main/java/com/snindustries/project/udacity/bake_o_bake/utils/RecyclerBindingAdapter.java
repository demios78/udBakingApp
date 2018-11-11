package com.snindustries.project.udacity.bake_o_bake.utils;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.snindustries.project.udacity.bake_o_bake.BR;


/**
 * Adapter that uses data binding in recyclers.
 *
 * @author Shaaz Noormohammad
 * (c) 2018
 */
public abstract class RecyclerBindingAdapter<V, H> extends RecyclerView.Adapter<RecyclerBindingAdapter.BindingViewHolder<V, H>> {

    private final int layoutID;
    private LayoutInflater layoutInflater;

    public RecyclerBindingAdapter(@LayoutRes int layoutID) {
        this.layoutID = layoutID;
    }

    protected abstract H getHandler(int position);

    protected abstract V getViewModel(int position);

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        if (getViewModel(position) != null) {
            holder.setViewModel(getViewModel(position));
        }
        if (getHandler(position) != null) {
            holder.setHandler(getHandler(position));
        }
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.layoutInflater = (layoutInflater == null ? LayoutInflater.from(parent.getContext()) : layoutInflater);
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, layoutID, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull BindingViewHolder<V, H> holder) {
        holder.itemView.clearAnimation();
        return super.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BindingViewHolder<V, H> holder) {
        super.onViewAttachedToWindow(holder);
        DataBindingUtil.bind(holder.itemView);
        holder.binding.executePendingBindings();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BindingViewHolder<V, H> holder) {
        super.onViewDetachedFromWindow(holder);
        holder.binding.unbind();
    }

    protected static class BindingViewHolder<V, H> extends RecyclerView.ViewHolder {

        protected final ViewDataBinding binding;

        protected BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void setHandler(H handler) {
            if (!binding.setVariable(BR.handler, handler)) {
                throw new IllegalStateException("Binding ${holder.binding} viewModel variable name should be 'handler'");
            }
        }

        protected void setViewModel(V viewModel) {
            if (!binding.setVariable(BR.model, viewModel)) {
                throw new IllegalStateException("Binding ${holder.binding} viewModel variable name should be 'model'");
            }
        }
    }
}
