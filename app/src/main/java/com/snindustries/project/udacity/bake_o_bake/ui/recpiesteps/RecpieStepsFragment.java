package com.snindustries.project.udacity.bake_o_bake.ui.recpiesteps;

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
import com.snindustries.project.udacity.bake_o_bake.databinding.RecpieStepsFragmentBinding;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

public class RecpieStepsFragment extends Fragment {

    private RecpieStepsFragmentBinding binding;
    private RecipeStepsViewModel mViewModel;

    public static RecpieStepsFragment newInstance() {
        return new RecpieStepsFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RecipeStepsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recpie_steps_fragment, container, false);
        return binding.getRoot();
    }

    public static class RecipeStepsViewModel extends ViewModel {
        private final LiveData<Recipe> recipe;
        // TODO: Implement the ViewModel
        private final Repository repository = Repository.get();

        public RecipeStepsViewModel() {
            this.recipe = repository.getCurrentRecipe();
        }

        public LiveData<Recipe> getRecipe() {
            return recipe;
        }
    }
}
