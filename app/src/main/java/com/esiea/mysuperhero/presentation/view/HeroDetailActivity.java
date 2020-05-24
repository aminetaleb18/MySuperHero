package com.esiea.mysuperhero.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.esiea.mysuperhero.R;
import com.esiea.mysuperhero.Singletons;
import com.esiea.mysuperhero.presentation.controller.DetailController;
import com.esiea.mysuperhero.presentation.model.Hero;
import com.esiea.mysuperhero.presentation.model.HeroDetail;
import com.squareup.picasso.Picasso;

public class HeroDetailActivity extends AppCompatActivity {

    private String name;
    private String url_image;
    private String Id;

    private ImageView imageView;

    private TextView textId;
    private TextView textName;
    private TextView textCombat;
    private TextView textStrength;
    private TextView textSpeed;
    private TextView textIntelligence;
    private TextView textDurability;
    private TextView textPuissance;
    private DetailController detailController;
    private CheckBox addFavori;

    public interface OnItemClickListener{
        void itemClick(Hero heroItem);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_hero);

        imageView = (ImageView)findViewById(R.id.im_hero);
        textId = (TextView)findViewById(R.id.id);
        textName = (TextView)findViewById(R.id.name);
        textCombat = (TextView)findViewById(R.id.combat);
        textStrength = (TextView)findViewById(R.id.strength);
        textSpeed = (TextView)findViewById(R.id.speed);
        textIntelligence = (TextView)findViewById(R.id.intelligence);
        textDurability = (TextView)findViewById(R.id.durability);
        textPuissance = (TextView)findViewById(R.id.power);
        addFavori = (CheckBox)findViewById(R.id.add_fav);
        Intent intent = getIntent();
        String heroBase = intent.getStringExtra("herobase");
        Hero hero = Singletons.getGson().fromJson(heroBase, Hero.class);

        Id = hero.getId();
        url_image = hero.getUrl();
        name = hero.getName();

        addFavori.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub

                if ( isChecked ) {
                    //       Toast.makeText(getApplicationContext(), "Add To Favori", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplication(), "Remove From Favori", Toast.LENGTH_LONG).show();
                }
            }});

        detailController = new DetailController(
                this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(this)
        );

        detailController.onStart(Id,url_image);


    }

    public void showError () {
        Toast.makeText(getApplicationContext(), "API Error for detail", Toast.LENGTH_SHORT).show();
    }

    public void showDetail (HeroDetail heroDetail) {
        String imageUri = heroDetail.getUrlIm();
        Picasso.get().load(imageUri).into(imageView);
        textId.setText( heroDetail.getId());
        textName.setText( heroDetail.getName());
        textCombat.setText( heroDetail.getCombat());
        textStrength.setText( heroDetail.getStrength());
        textSpeed.setText( heroDetail.getSpeed());
        textIntelligence.setText( heroDetail.getIntelligence());
        textDurability.setText( heroDetail.getDurability());
        textPuissance.setText( heroDetail.getPower());


    }
}
