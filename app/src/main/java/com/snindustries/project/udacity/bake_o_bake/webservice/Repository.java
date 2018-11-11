package com.snindustries.project.udacity.bake_o_bake.webservice;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.orhanobut.logger.Logger;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Shaaz Noormohammad
 * (c) 11/9/18
 */
public class Repository {

    private static Repository INSTANCE;
    private final MutableLiveData<Integer> query = new MutableLiveData<>();
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    LiveData<Recipe> currentRecipe = Transformations.map(query, new Function<Integer, Recipe>() {
        @Override
        public Recipe apply(Integer input) {
            return getRecipe(input);
        }
    });

    private Repository() {
        RecipeClient.get().getApi().getRecipies().enqueue(
                new Callback<List<Recipe>>() {
                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if (response.body() != null) {
                            recipes.postValue(response.body());
                            Logger.d(response.body());

                        } else {
                            Logger.e("No Response");
                        }
                    }
                }
        );
    }

    public static Repository get() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository();
                }
            }
        }
        return INSTANCE;
    }

    public void applyCurrentRecipe(Integer index) {
        query.postValue(index);
    }

    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    private Recipe getRecipe(Integer input) {
        return recipes.getValue().stream().filter(recipe -> recipe.id == input).collect(Collectors.toList()).get(0);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
