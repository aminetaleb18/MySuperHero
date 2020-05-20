package com.esiea.mysuperhero.presentation.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestBaseHeroResponse {
    @SerializedName("response")
    public String response;

    @SerializedName("id")
    public String Id;

    @SerializedName("name")
    public String name;

    @SerializedName("intelligence")
    public String intelligence;

    @SerializedName("strength")
    public String strength;

    @SerializedName("speed")
    public String speed;

    @SerializedName("durability")
    public String durability;

    @SerializedName("power")
    public String power;

    @SerializedName("combat")
    public String combat;

}
