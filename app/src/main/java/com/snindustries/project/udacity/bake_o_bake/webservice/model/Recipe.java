package com.snindustries.project.udacity.bake_o_bake.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("ingredients")
    @Expose
    public List<Ingredient> ingredients = null;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("servings")
    @Expose
    public Integer servings;
    @SerializedName("steps")
    @Expose
    public List<Step> steps = null;

}
