package com.snindustries.project.udacity.bake_o_bake.webservice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("shortDescription")
    @Expose
    public String shortDescription;
    @SerializedName("thumbnailURL")
    @Expose
    public String thumbnailURL;
    @SerializedName("videoURL")
    @Expose
    public String videoURL;

}
