package com.snindustries.project.udacity.bake_o_bake.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("ingredient")
    @Expose
    public String ingredient;
    @SerializedName("measure")
    @Expose
    public String measure;
    @SerializedName("quantity")
    @Expose
    public Float quantity;

}
