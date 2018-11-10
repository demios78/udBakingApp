package com.snindustries.project.udacity.bake_o_bake;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.snindustries.project.udacity.bake_o_bake.databinding.ActivityHomeBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.ListBindingAdapter;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        binding.setModel(viewModel);
        binding.setHandler(new Handler());
        binding.setLifecycleOwner(this);
        setSupportActionBar(binding.toolbar);

    }

    public static class BakingListAdapter extends ListBindingAdapter<Recipe, Handler> {
        public BakingListAdapter(@NonNull List<Recipe> items, @NonNull Handler handler, int layoutID) {
            super(items, handler, layoutID);
        }
    }

    public static class Handler {

    }

    public static class ViewModel extends AndroidViewModel {
        private final BakingListAdapter adapter;
        private final LiveData<List<Recipe>> recipies;

        public ViewModel(@NonNull Application application) {
            super(application);
            recipies = new Repository().getRecipes();
            adapter = new BakingListAdapter(new ArrayList<>(), new Handler(), R.layout.recipe_card_item);
            recipies.observeForever(adapter::addItems);
        }

        public BakingListAdapter getAdapter() {
            return adapter;
        }
    }


}
