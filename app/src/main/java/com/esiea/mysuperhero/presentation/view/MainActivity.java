package com.esiea.mysuperhero.presentation.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.esiea.mysuperhero.R;
import com.esiea.mysuperhero.presentation.data.HeroApi;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.esiea.mysuperhero.presentation.model.RestBaseHeroResponse;
import com.esiea.mysuperhero.presentation.view.ListAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.esiea.mysuperhero.Constants.BASE_URL;
import static com.esiea.mysuperhero.Constants.SUPER_HERO_NB;
import static com.esiea.mysuperhero.Constants.SUPER_HERO_PREFS;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<String> input;
    SwipeRefreshLayout mySwipeRefreshLayout;
    private Gson gson;
    private SharedPreferences sharedPreferences;

    private List<Hero> heroList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(SUPER_HERO_PREFS, MODE_PRIVATE);

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        doYourUpdate();
                    }
                }
        );

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Hero> heroList = getDataFromCache();
        if (heroList != null){
            showList(heroList);
        }
        else{
            makeApiCall();
        }

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        input.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        //  mAdapter.notifyDataSetChanged();
    }

    private List<Hero> getDataFromCache () {

        for (int i = 1;i < SUPER_HERO_NB;i++)
        {
            String jsonString = sharedPreferences.getString("jsonHeroList"+Integer.toString(i), null);
            if (jsonString != null){
                heroList = new ArrayList<>();
                Type listType = new TypeToken<RestBaseHeroResponse>(){}.getType();
                Toast.makeText(getApplicationContext(),"list retrieved", Toast.LENGTH_LONG).show();
                RestBaseHeroResponse heroBase = gson.fromJson(jsonString, listType);
                Hero hero = new Hero();
                hero.setName(heroBase.name);
                heroList.add(hero);
            }
        }
        return heroList;
    }

    private void doYourUpdate() {
        // TODO implement a refresh
        mySwipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }

   // private void showList(List<Hero> heroList){
   private void showList(List<Hero> heroList){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListAdapter(heroList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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

        heroList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        HeroApi pokeApi = retrofit.create(HeroApi.class);

        for (int i = 1; i < SUPER_HERO_NB; i++) {
            Call<RestBaseHeroResponse> call = pokeApi.getHeroBaseResponse(Integer.toString(i));

            call.enqueue(new Callback<RestBaseHeroResponse>() {
                @Override
                public void onResponse (Call<RestBaseHeroResponse> call, Response<RestBaseHeroResponse> response) {
                    Log.d("call onFailure()", "ess");
                    if ( response.isSuccessful() && response.body() != null ) {

                        String name = response.body().name;
                        Hero hero = new Hero();
                        hero.setName(name);
                        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                        heroList.add(hero);
                        showList(heroList);
                        saveList(response.body(), response.body().Id);
                    } else {
                        showError();
                    }
                }

                @Override
                public void onFailure (Call<RestBaseHeroResponse> call, Throwable t) {
                    Log.d("call onFailure()", "Pas de connexion internet (onFailure(call)" + t);
                    showError();
                }
            });
        }
        Toast.makeText(getApplicationContext(),"All Data Heroes Saved", Toast.LENGTH_LONG).show();
    }

    private void showError(){
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

}
