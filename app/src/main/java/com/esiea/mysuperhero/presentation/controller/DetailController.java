package com.esiea.mysuperhero.presentation.controller;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.esiea.mysuperhero.Singletons;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.esiea.mysuperhero.presentation.model.HeroDetail;
import com.esiea.mysuperhero.presentation.model.RestBaseHeroResponse;
import com.esiea.mysuperhero.presentation.model.RestDetailsHeroResponse;
import com.esiea.mysuperhero.presentation.view.HeroDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailController {

    private Gson gson;
    private SharedPreferences sharedPreferences;

    private List<Hero> heroList;
    private HeroDetailActivity view;

    public DetailController(HeroDetailActivity detailActivity, Gson gson, SharedPreferences sharedPreferences)
    {
        this.view = detailActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart (String Id, String url_image){
        Boolean data_retrived = getDataFromCache(Id, url_image);
        if (!data_retrived){
            makeApiCall(Id, url_image);
        }
    }

    private Boolean getDataFromCache (String Id, String url_image) {

        Boolean data_retrieved = false;
        String jsonString = sharedPreferences.getString("jsonHeroDetail"+Id, null);
        if (jsonString != null){
            data_retrieved = true;
            Type listType = new TypeToken<RestDetailsHeroResponse>(){}.getType();
            Toast.makeText(view.getApplicationContext(),"Hero retrieved", Toast.LENGTH_LONG).show();
            RestDetailsHeroResponse heroDetailResponse = gson.fromJson(jsonString, listType);
            HeroDetail heroDetail = new HeroDetail();
            heroDetail.setId(Id);
            heroDetail.setName(heroDetailResponse.name);
            heroDetail.setUrlIm(url_image);
            heroDetail.setId(Id);
            heroDetail.setUrlIm(url_image);
            heroDetail.setIntelligence (heroDetailResponse.intelligence);
            heroDetail.setStrength (heroDetailResponse.strength);
            heroDetail.setSpeed (heroDetailResponse.speed);
            heroDetail.setDurability (heroDetailResponse.durability);
            heroDetail.setPower (heroDetailResponse.power);
            heroDetail.setCombat (heroDetailResponse.combat);
            view.showDetail(heroDetail);
        }
        else{
            return data_retrieved;
        }

        return data_retrieved;
    }


    private void saveList(RestDetailsHeroResponse response, String Id)
    {
        String jsonString = gson.toJson(response);
        sharedPreferences
                .edit()
                .putString("jsonHeroDetail"+Id, jsonString)
                .apply();
    }

    private void makeApiCall (final String Id, final String url_image){

            Call<RestDetailsHeroResponse> call =  Singletons.getHeroApi().getHeroDetailResponse(Id);

            call.enqueue(new Callback<RestDetailsHeroResponse>() {
                @Override
                public void onResponse (Call<RestDetailsHeroResponse> call, Response<RestDetailsHeroResponse> response) {
                    Log.d("call onFailure()", "ess");
                    if ( response.isSuccessful() && response.body() != null ) {

                        String name = response.body().name;
                        HeroDetail heroDetail = new HeroDetail();
                        heroDetail.setId(Id);
                        heroDetail.setName(name);
                        heroDetail.setUrlIm(url_image);
                        heroDetail.setIntelligence (response.body().intelligence);
                        heroDetail.setStrength (response.body().strength);
                        heroDetail.setSpeed (response.body().speed);
                        heroDetail.setDurability (response.body().durability);
                        heroDetail.setPower (response.body().power);
                        heroDetail.setCombat (response.body().combat);


                        view.showDetail(heroDetail);
                        Toast.makeText(view.getApplicationContext(), name+":"+response.body().strength, Toast.LENGTH_SHORT).show();

                        saveList(response.body(), response.body().Id);
                    } else {
                        view.showError();
                    }
                }

                @Override
                public void onFailure (Call<RestDetailsHeroResponse> call, Throwable t) {
                    Log.d("call onFailure()", "Pas de connexion internet (onFailure(call)" + t);
                    view.showError();
                }
            });

        Toast.makeText(view.getApplicationContext(),"All Data Heroes Saved", Toast.LENGTH_LONG).show();
    }

    public void onItemFavClickHero (Hero heroItem) {
        RestBaseHeroResponse restBase = new RestBaseHeroResponse();
        restBase.fav = heroItem.getFavori();
        restBase.Id = heroItem.getId();
        restBase.name = heroItem.getName();
        restBase.urlImage = heroItem.getUrl();
        restBase.response = "success";

        Type listType = new TypeToken<RestBaseHeroResponse>(){}.getType();
        String jsonString = gson.toJson(restBase, listType);
        sharedPreferences
                .edit()
                .putString("jsonHeroList"+heroItem.getId(), jsonString)
                .apply();
    }
}
