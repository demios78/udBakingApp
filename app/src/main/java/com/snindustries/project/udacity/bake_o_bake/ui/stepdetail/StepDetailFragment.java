package com.snindustries.project.udacity.bake_o_bake.ui.stepdetail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snindustries.project.udacity.bake_o_bake.R;
import com.snindustries.project.udacity.bake_o_bake.databinding.StepDetailFragmentBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.AppDataBindingComponent;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Step;


public class StepDetailFragment extends Fragment {

    private StepDetailViewModel viewModel;

    public static StepDetailFragment newInstance() {
        return new StepDetailFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StepDetailFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.step_detail_fragment, container, false, new AppDataBindingComponent());
        viewModel = ViewModelProviders.of(this).get(StepDetailViewModel.class);
        binding.setHandler(new Handler());
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }

    public static class StepDetailViewModel extends ViewModel {
        private final LiveData<Step> step;

        public StepDetailViewModel() {
            step = Repository.get().getCurrentStep();
        }

        public LiveData<Step> getStep() {
            return step;
        }
    }

    public class Handler {
        public void onClick(View view) {

        }
    }
}
