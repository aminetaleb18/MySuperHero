package com.esiea.mysuperhero.presentation.model;

import com.google.gson.annotations.SerializedName;

public class RestBaseHeroResponse {

    @SerializedName("response")
    public String response;

    @SerializedName("id")
    public String Id;

    @SerializedName("name")
    public String name;

    @SerializedName("url")
    public String urlImage;

    @SerializedName("fav")
    public String fav;



}
