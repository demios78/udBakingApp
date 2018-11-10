package com.snindustries.project.udacity.bake_o_bake.webservice;

import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Shaaz Noormohammad
 * (c) 11/9/18
 */
public interface RecipeApi {

    //http://go.udacity.com/android-baking-app-json
    @GET("android-baking-app-json")
    Call<List<Recipe>> getRecipies();

}
