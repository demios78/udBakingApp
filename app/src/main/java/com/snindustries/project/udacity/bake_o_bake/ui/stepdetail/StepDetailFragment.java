package com.snindustries.project.udacity.bake_o_bake.ui.stepdetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snindustries.project.udacity.bake_o_bake.R;


public class StepDetailFragment extends Fragment {

    private StepDetailViewModel mViewModel;

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StepDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.step_detail_fragment, container, false);
    }

    public static class StepDetailViewModel extends ViewModel {
        // TODO: Implement the ViewModel
    }
}
