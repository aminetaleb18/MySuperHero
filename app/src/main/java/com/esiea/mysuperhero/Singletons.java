package com.esiea.mysuperhero;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.esiea.mysuperhero.presentation.data.HeroApi;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.esiea.mysuperhero.presentation.view.ListAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.esiea.mysuperhero.Constants.BASE_URL;
import static com.esiea.mysuperhero.Constants.SUPER_HERO_PREFS;

public class Singletons {

    private static Gson gsonInstance = null;

    private static HeroApi heroApiInstance;

    private static SharedPreferences sharedPreferencesInstance;

    private static ListAdapter listAdapterInstance;

    private static LinearLayoutManager linearLayoutInstance;

    private static ArrayList listInstance;

    public static Gson getGson(){
        if (gsonInstance == null){
            return new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static HeroApi getHeroApi(){
        if (heroApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            heroApiInstance = retrofit.create(HeroApi.class);
        }
        return heroApiInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if (sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences(SUPER_HERO_PREFS, MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }

    public static LinearLayoutManager getLinearLayoutInstance(Context context){
        if (linearLayoutInstance == null){
            linearLayoutInstance = new LinearLayoutManager(context);
        }
        return linearLayoutInstance;
    }

    public static ListAdapter getListAdapter(List<Hero> heroList, ListAdapter.OnItemClickListener listener){
        if (listAdapterInstance == null){
            listAdapterInstance = new ListAdapter(heroList, listener);
        }
        return listAdapterInstance;
    }

    public static ArrayList<Hero> getHeroList(){
        if (listInstance == null){
            listInstance = new ArrayList();
        }
        return listInstance;
    }

    public static Hero getHeroPosition(int position){
        if (listInstance == null){
            listInstance = new ArrayList();
        }

        return (Hero)listInstance.get(position);
    }



}
