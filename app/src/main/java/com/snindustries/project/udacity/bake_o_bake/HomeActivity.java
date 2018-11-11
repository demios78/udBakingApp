package com.snindustries.project.udacity.bake_o_bake;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.snindustries.project.udacity.bake_o_bake.databinding.ActivityHomeBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.AppDataBindingComponent;
import com.snindustries.project.udacity.bake_o_bake.utils.ListBindingAdapter;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    private ViewModel viewModel;

    private HomeActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home, new AppDataBindingComponent());
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        binding.setModel(viewModel);
        binding.setHandler(new Handler());
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbar);
    }


    public static class BakingListAdapter extends ListBindingAdapter<Recipe, Handler> {
        public BakingListAdapter(@NonNull List<Recipe> items, int layoutID) {
            super(items, layoutID);
        }
    }

    public static class ViewModel extends AndroidViewModel {
        private final BakingListAdapter adapter;
        private final LiveData<List<Recipe>> recipes;
        private final Repository repository;

        public ViewModel(@NonNull Application application) {
            super(application);
            repository = Repository.get();
            recipes = repository.getRecipes();
            adapter = new BakingListAdapter(new ArrayList<>(), R.layout.recipe_card_item);
            recipes.observeForever(adapter::addItems);
        }

        public BakingListAdapter getAdapter() {
            return adapter;
        }

        public void setCurrentRecipe(Integer id) {
            repository.applyCurrentRecipe(id);
        }
    }

    public class Handler {

        public void onClick(View view, Recipe recipe) {
            Toast.makeText(view.getContext(), "Rec " + recipe.name, Toast.LENGTH_SHORT).show();

            viewModel.setCurrentRecipe(recipe.id);

            //IF in phone mode, start activity
            Intent intent = new Intent(getActivity(), RecpieSteps.class);
            intent.putExtra("EXTRA_RECIPE_ID", recipe.id);

            getActivity().startActivity(intent);


        }

    }


}
