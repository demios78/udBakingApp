package com.snindustries.project.udacity.bake_o_bake.webservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import com.orhanobut.logger.Logger;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Step;

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
    private final MutableLiveData<Integer> recipeQuery = new MutableLiveData<>();
    private final MutableLiveData<Integer> stepQuery = new MutableLiveData<>();
    private LiveData<Step> currentStep = Transformations.map(stepQuery, this::getStep);
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private LiveData<Recipe> currentRecipe = Transformations.map(recipeQuery, this::getRecipe);


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
        recipeQuery.postValue(index);
    }

    public void applyCurrentStep(Integer id) {

    }

    public LiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    public LiveData<Step> getCurrentStep() {
        return currentStep;
    }

    private Recipe getRecipe(Integer input) {
        return recipes.getValue().stream().filter(recipe -> recipe.id.equals(input)).collect(Collectors.toList()).get(0);
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    private Step getStep(Integer input) {
        return getCurrentRecipe().getValue().steps.stream().filter(step -> step.id.equals(input)).collect(Collectors.toList()).get(0);
    }
}
