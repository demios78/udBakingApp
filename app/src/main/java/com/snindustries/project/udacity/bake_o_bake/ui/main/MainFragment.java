package com.snindustries.project.udacity.bake_o_bake.ui.main;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snindustries.project.udacity.bake_o_bake.NetworkIdlingResource;
import com.snindustries.project.udacity.bake_o_bake.R;
import com.snindustries.project.udacity.bake_o_bake.databinding.MainFragmentBinding;
import com.snindustries.project.udacity.bake_o_bake.utils.AppDataBindingComponent;
import com.snindustries.project.udacity.bake_o_bake.utils.ListBindingAdapter;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    //TODO add Network state broadcast receiver and restart network fetch

    private MainFragmentBinding binding;
    private NetworkIdlingResource idlingResource;
    private ViewModel viewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public NetworkIdlingResource getIdlingResource() {
        return idlingResource;
    }

    public void setIdlingResource(NetworkIdlingResource idlingResource) {
        this.idlingResource = idlingResource;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        binding.setModel(viewModel);
        binding.setHandler(new MainRecipeHandler(this, viewModel));
        binding.setLifecycleOwner(this);
        BakingListAdapter adapter = new BakingListAdapter();
        viewModel.recipes.observe(this, adapter::replaceAll);
        binding.recycler.setAdapter(adapter);
        binding.toolbar.setTitle(R.string.app_name);
        //TODO DividerItemDecoration that doesn't decorate last item.
        //binding.recycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        viewModel.recipes.observe(this, recipes -> getIdlingResource().setIsIdleNow(recipes != null));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false, new AppDataBindingComponent());
        return binding.getRoot();
    }

    public static class BakingListAdapter extends ListBindingAdapter<Recipe, MainRecipeHandler> {
        public BakingListAdapter() {
            super(new ArrayList<>(), R.layout.recipe_card_item);
        }
    }

    public static class ViewModel extends AndroidViewModel {
        private final LiveData<List<Recipe>> recipes;
        private final Repository repository;

        public ViewModel(@NonNull Application application) {
            super(application);
            repository = Repository.get();
            recipes = repository.getRecipes();
        }

        public LiveData<List<Recipe>> getRecipes() {
            return recipes;
        }

        public void setCurrentRecipe(Integer id) {
            repository.applyCurrentRecipe(id);
        }
    }

}
