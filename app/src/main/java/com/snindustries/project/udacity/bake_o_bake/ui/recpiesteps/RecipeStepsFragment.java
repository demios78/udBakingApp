package com.snindustries.project.udacity.bake_o_bake.ui.recpiesteps;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snindustries.project.udacity.bake_o_bake.R;
import com.snindustries.project.udacity.bake_o_bake.RecipeSteps;
import com.snindustries.project.udacity.bake_o_bake.StepDetailActivity;
import com.snindustries.project.udacity.bake_o_bake.databinding.RecpieStepsFragmentBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.AppDataBindingComponent;
import com.snindustries.project.udacity.bake_o_bake.utils.ListBindingAdapter;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Step;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeStepsFragment extends Fragment {

    public static RecipeStepsFragment newInstance() {
        return new RecipeStepsFragment();
    }

    RecipeStepsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecpieStepsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.recpie_steps_fragment, container, false, new AppDataBindingComponent());
         viewModel = ViewModelProviders.of(this).get(RecipeStepsViewModel.class);
        binding.setModel(viewModel);
        binding.setHandler(new Handler());
        binding.setLifecycleOwner(this);
        viewModel.getRecipe().observe(this, this::onSetTitle);
        return binding.getRoot();
    }

    private void onSetTitle(@Nullable Recipe recipe) {
        if (recipe != null && getActivity()!=null) {
            getActivity().setTitle(recipe.name);
        }
    }

    public static class RecipeStepsViewModel extends ViewModel {
        private final StepFragmentAdapter adapter;
        private final LiveData<Recipe> recipe;
        private final Repository repository = Repository.get();

        public RecipeStepsViewModel() {
            this.recipe = repository.getCurrentRecipe();
            adapter = new StepFragmentAdapter();
            recipe.observeForever(recipe -> adapter.addItems(recipe != null ? recipe.steps : null));
        }

        public StepFragmentAdapter getAdapter() {
            return adapter;
        }

        public LiveData<Recipe> getRecipe() {
            return recipe;
        }

        public void setCurrentStep(Integer id) {
            repository.applyCurrentStep(id);
        }
    }

    public static class StepFragmentAdapter extends ListBindingAdapter<Step, Handler> {

        public StepFragmentAdapter() {
            super(new ArrayList<>(), R.layout.recipe_step_item);
        }
    }

    public class Handler {
        public void onClick(View view, Step step) {
            Toast.makeText(view.getContext(), "Step: " + step.description, Toast.LENGTH_SHORT).show();

            viewModel.setCurrentStep(step.id);

            //IF in phone mode, start activity
            Intent intent = new Intent(getActivity(), StepDetailActivity.class);
            intent.putExtra("EXTRA_STEP_ID", step.id);

            Objects.requireNonNull(getActivity()).startActivity(intent);
        }
    }
}
