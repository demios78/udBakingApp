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
import android.widget.TableLayout;
import android.widget.TextView;

import com.snindustries.project.udacity.bake_o_bake.R;
import com.snindustries.project.udacity.bake_o_bake.StepDetailActivity;
import com.snindustries.project.udacity.bake_o_bake.databinding.RecpieStepsFragmentBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.AppDataBindingComponent;
import com.snindustries.project.udacity.bake_o_bake.utils.ListBindingAdapter;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Ingredient;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Step;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class RecipeStepsFragment extends Fragment {

    RecpieStepsFragmentBinding binding;
    RecipeStepsViewModel viewModel;

    public static RecipeStepsFragment newInstance() {
        return new RecipeStepsFragment();
    }

    private View makeIngredientsView(@Nullable Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        View view = getLayoutInflater().inflate(R.layout.ingredients_table, (ViewGroup) binding.getRoot(), false);
        for (Ingredient ingredient : recipe.ingredients) {
            View tableRow = getLayoutInflater().inflate(R.layout.ingredients_table_row, view.findViewById(R.id.ingredients_table), false);
            ((TextView) tableRow.findViewById(R.id.ingredient)).setText(ingredient.ingredient);
            ((TextView) tableRow.findViewById(R.id.quantity)).setText(df.format(ingredient.quantity));
            ((TextView) tableRow.findViewById(R.id.measure)).setText(ingredient.measure);
            ((TableLayout) view.findViewById(R.id.ingredients_table)).addView(tableRow);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(RecipeStepsViewModel.class);
        binding.setModel(viewModel);
        binding.setHandler(new Handler());
        binding.setLifecycleOwner(this);
        StepFragmentAdapter adapter = new StepFragmentAdapter();
        binding.recycler.setAdapter(adapter);
        viewModel.getRecipe().observe(this, recipe -> {
            onSetTitle(recipe);
            adapter.replaceAll(recipe.steps);
            //binding.viewContainer.addView(makeIngredientsView(recipe));
            adapter.setHeaderView(makeIngredientsView(recipe));
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recpie_steps_fragment, container, false, new AppDataBindingComponent());
        return binding.getRoot();
    }

    private void onSetTitle(@Nullable Recipe recipe) {
        if (recipe != null && getActivity() != null) {
            getActivity().setTitle(recipe.name);
        }
    }

    public static class RecipeStepsViewModel extends ViewModel {
        private final LiveData<Recipe> recipe;
        private final Repository repository = Repository.get();

        public RecipeStepsViewModel() {
            this.recipe = repository.getCurrentRecipe();
        }

        public LiveData<Recipe> getRecipe() {
            return recipe;
        }

        public void setCurrentStep(Integer id) {
            repository.applyCurrentStep(id);
        }
    }

    public static class StepFragmentAdapter extends ListBindingAdapter<Step, Handler> {

        public static final int HEADER = 1;

        private View header;

        public StepFragmentAdapter() {
            super(new ArrayList<>(), R.layout.recipe_step_item);
        }

        @Nullable
        @Override
        protected Handler getHandler(int position) {
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
        public int getItemViewType(int position) {
            if (header != null && position == 0) {
                return HEADER;
            }
            return super.getItemViewType(position);
        }

        @Override
        protected Step getViewModel(int position) {
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

        void setHeaderView(View header) {
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

    public class Handler {
        public void onClick(View view, Step step) {
            viewModel.setCurrentStep(step.id);

            if (getResources().getInteger(R.integer.grid_columns) == 1) {
                //IF in phone mode, start activity
                Intent intent = new Intent(getActivity(), StepDetailActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        }
    }
}
