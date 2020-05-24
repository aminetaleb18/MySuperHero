package com.esiea.mysuperhero.presentation.model;

public class Hero {

    private String name;

    private String url;

    private String Id;

    public String getName(){
        return name;
    }

    public String getUrl(){
        return url;
    }

    public String getId(){
        return Id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImageUrl(String imUrl){
        this.url = imUrl;
    }

    public void setId(String Id){
        this.Id = Id;
    }
}
