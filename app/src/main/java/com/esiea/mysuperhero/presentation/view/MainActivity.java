package com.esiea.mysuperhero.presentation.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
    private SearchView searchView;

    SwipeRefreshLayout mySwipeRefreshLayout;
    private MainController controller;

    private CheckBox favori_list;

    private List<Hero> heroList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        favori_list = findViewById(R.id.favori_list);
        favori_list.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub

                if ( isChecked ) {
                    mAdapter.setListFavori(true);
                    showList();
                } else {
                    mAdapter.setListFavori(false);
                    showList();
                }
            }});

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

