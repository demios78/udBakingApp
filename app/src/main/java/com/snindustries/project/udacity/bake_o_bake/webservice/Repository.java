package com.snindustries.project.udacity.bake_o_bake.webservice;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.os.Debug;

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

    private MutableLiveData<List<Recipe>> recipies = new MutableLiveData<>();

    public Repository() {
        RecipeClient.get().getApi().getRecipies().enqueue(
                new Callback<List<Recipe>>() {
                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {

                    }

                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        if (response.body() != null) {
                            recipies.postValue(response.body());
                        } else {

                        }
                    }
                }
        );
    }

    public LiveData<List<Recipe>> getRecipies() {
        return recipies;
    }
}
