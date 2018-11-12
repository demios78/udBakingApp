package com.snindustries.project.udacity.bake_o_bake.webservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Step;

import java.util.List;
import java.util.Objects;
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
    private final MutableLiveData<Integer> recipeQuery = new MutableLiveData<>();
    private final MutableLiveData<Integer> stepQuery = new MutableLiveData<>();
    private LiveData<Step> currentStep = Transformations.map(stepQuery, this::getStep);
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private LiveData<Recipe> currentRecipe = Transformations.switchMap(recipeQuery, input -> {
        MutableLiveData<Recipe> out = new MutableLiveData<>();
        out.postValue(getRecipe(input));
        return out;
    });

    private Repository() {
        RecipeClient.get().getApi().getRecipies().enqueue(new GetRecipeList());
        currentRecipe.observeForever(this::resetStepOnRecipeChange);
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
        recipeQuery.postValue(index);
    }

    public void applyCurrentStep(Integer id) {
        stepQuery.postValue(id);
    }

    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    public LiveData<Step> getCurrentStep() {
        return currentStep;
    }

    private Recipe getRecipe(Integer input) {
        return Objects.requireNonNull(recipes.getValue())
                .stream().filter(recipe -> recipe.id.equals(input))
                .collect(Collectors.toList())
                .get(0);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    private Step getStep(Integer input) {
        return Objects.requireNonNull(getCurrentRecipe().getValue())
                .steps.stream()
                .filter(step -> step.id.equals(input))
                .collect(Collectors.toList())
                .get(0);
    }

    private void resetStepOnRecipeChange(@Nullable Recipe recipe) {
        if (recipe != null && recipe.steps != null && recipe.steps.size() > 0 && recipe.steps.get(0).id != null) {
            applyCurrentStep(recipe.steps.get(0).id);
        }
    }

    private class GetRecipeList implements Callback<List<Recipe>> {
        @Override
        public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
            t.printStackTrace();
        }

        @Override
        public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
            if (response.body() != null) {
                recipes.postValue(response.body());
                Logger.d(response.body());

            } else {
                Logger.e("No Response");
            }
        }
    }
}
