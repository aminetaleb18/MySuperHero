package com.esiea.mysuperhero.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.esiea.mysuperhero.R;
import com.esiea.mysuperhero.Singletons;
import com.esiea.mysuperhero.presentation.controller.MainController;
import com.esiea.mysuperhero.presentation.model.Hero;

import java.util.List;

import static com.esiea.mysuperhero.Constants.HERO_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imageView;

    SwipeRefreshLayout mySwipeRefreshLayout;
    private MainController controller;

    private List<Hero> heroList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(this)
        );
        controller.onStart();
    }


    private void doYourUpdate () {
        // TODO implement a refresh
        mySwipeRefreshLayout.setRefreshing(false); // Disables the refresh icon
    }

    public void showError () {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    public void  createList () {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = Singletons.getLinearLayoutInstance(this);
        recyclerView.setLayoutManager(layoutManager);
        heroList = Singletons.getHeroList();

        ListAdapter.OnItemClickListener listener = new ListAdapter.OnItemClickListener() {
            @Override
            public void itemClick (Hero heroItem) {
                controller.onItemClickHero(heroItem);
            }
        };

        mySwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh () {
                        doYourUpdate();
                    }
                }
        );

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove (RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }

                    @Override
                    public void onSwiped (RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        heroList.remove(viewHolder.getAdapterPosition());
                        final Hero currentHero = Singletons.getHeroList().get(viewHolder.getAdapterPosition());

                        controller.onItemClickHero(currentHero);
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        mAdapter = Singletons.getListAdapter(heroList, listener);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void showList () {
        mAdapter.notifyDataSetChanged();
    }

    public void navigateToDetails (Hero heroItem) {
        Toast.makeText(getApplicationContext(), heroItem.getName(), Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(this, HeroDetailActivity.class);
        myIntent.putExtra("herobase", Singletons.getGson().toJson(heroItem));
        this.startActivityForResult(myIntent, HERO_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HERO_REQUEST_CODE)
        {
            String isfavori=data.getStringExtra("isfavori");
            mAdapter.setFavori(this, isfavori);
            showList();
        }
    }
}

