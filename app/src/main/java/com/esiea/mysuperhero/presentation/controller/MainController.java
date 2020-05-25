package com.esiea.mysuperhero.presentation.controller;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.esiea.mysuperhero.Singletons;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.esiea.mysuperhero.presentation.model.RestBaseHeroResponse;
import com.esiea.mysuperhero.presentation.view.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esiea.mysuperhero.Constants.SUPER_HERO_NB;

public class MainController {
    private Gson gson;
    private SharedPreferences sharedPreferences;

    private List<Hero> heroList;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences)
    {
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){
        view.createList();
        Boolean data_retrived = getDataFromCache(Singletons.getHeroList());
        if (data_retrived == false){
            makeApiCall();
        }
    }

    private Boolean getDataFromCache (List<Hero> heroList) {

        Boolean data_retrieved = false;
        for (int i = 1;i < SUPER_HERO_NB;i++)
        {

            String jsonString = sharedPreferences.getString("jsonHeroList"+i, null);
            if (jsonString != null){
                data_retrieved = true;
                Type listType = new TypeToken<RestBaseHeroResponse>(){}.getType();
                Toast.makeText(view.getApplicationContext(),"list retrieved", Toast.LENGTH_LONG).show();
                RestBaseHeroResponse heroBase = gson.fromJson(jsonString, listType);

                Hero hero = new Hero();
                hero.setId(String.valueOf(heroBase.Id));
                hero.setName(heroBase.name);
                hero.setImageUrl(heroBase.urlImage);
                hero.setFavori(heroBase.fav);
                heroList.add(hero);
                view.showList();
            }
            else
            {
                return data_retrieved;
            }
        }
        return data_retrieved;
    }


    private void saveList(RestBaseHeroResponse response, String Id)
    {
        String jsonString = gson.toJson(response);
        sharedPreferences
                .edit()
                .putString("jsonHeroList"+Id, jsonString)
                .apply();
    }

    private void makeApiCall(){

        for (int i = 1; i < SUPER_HERO_NB; i++) {
            Call<RestBaseHeroResponse> call =  Singletons.getHeroApi().getHeroBaseResponse(String.valueOf(i));

            call.enqueue(new Callback<RestBaseHeroResponse>() {
                @Override
                public void onResponse (Call<RestBaseHeroResponse> call, Response<RestBaseHeroResponse> response) {
                    Log.d("call onFailure()", "ess");
                    if ( response.isSuccessful() && response.body() != null ) {

                        String name = response.body().name;
                        Hero hero = new Hero();
                        hero.setId(response.body().Id);
                        hero.setName(name);
                        hero.setImageUrl(response.body().urlImage);
                        hero.setFavori(response.body().fav);
                        if (response.body().fav == null)
                            response.body().fav = "0";
                        Toast.makeText(view.getApplicationContext(), hero.getId()+"/732\n"+name+"\n Please wait ...", Toast.LENGTH_SHORT).show();
                        Singletons.getHeroList().add(hero);

                        view.showList();
                        saveList(response.body(), response.body().Id);
                    } else {
                        view.showError();
                    }
                }

                @Override
                public void onFailure (Call<RestBaseHeroResponse> call, Throwable t) {
                    Log.d("call onFailure()", "Pas de connexion internet (onFailure(call)" + t);
                    view.showError();
                }
            });
        }

    }

    public void onItemClickHero(Hero heroItem){
        view.navigateToDetails(heroItem);
    }
}
