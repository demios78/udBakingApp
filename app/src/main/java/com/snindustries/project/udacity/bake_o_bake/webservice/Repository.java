package com.snindustries.project.udacity.bake_o_bake.webservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.orhanobut.logger.Logger;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Shaaz Noormohammad
 * (c) 11/9/18
 */
public class Repository {

    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();

    public Repository() {
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

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
