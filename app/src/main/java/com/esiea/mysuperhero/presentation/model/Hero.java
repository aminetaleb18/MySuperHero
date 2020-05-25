package com.esiea.mysuperhero.presentation.model;

public class Hero {

    private String name;

    private String url;

    private String Id;

    private String isFavori;

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

    public String getFavori () {
        return isFavori;
    }

    public void setFavori (String isFavori) {

        this.isFavori = isFavori;
        if (isFavori == null)
            this.isFavori = "0";
    }
}
