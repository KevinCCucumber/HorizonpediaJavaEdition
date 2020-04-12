package com.ac_companion.wegner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

//Admob-ID dieser App ist ca-app-pub-5899740383904630~9341712675
//Bottom_Banner ca-app-pub-5899740383904630/7815564939
//Fullscreen_unterbrecher ca-app-pub-5899740383904630/9096857012

//Ad-shit importieren
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

public class FishList extends AppCompatActivity {
    private static final String TAG = "FishCatchable";
    private CheckBox checkCurrent, checkToday, checkCaught;
    private ArrayList<Fish> realFishList = new ArrayList<>();
    FishListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fish_total = "";
        JSONArray fish_array = null;
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Started.");
        setContentView(R.layout.activity_fish_list);
        ListView mListView = findViewById(R.id.listFish);
        AdView mAdView;

        //Fisch Inputstream anlegen
        InputStream fish_stream = getResources().openRawResource(R.raw.fish);

        try {
            if (Util.getGlobalFishList().isEmpty()){
                realFishList = Util.setGlobalFishList(fish_stream);
            }else
            {
                realFishList = Util.getGlobalFishList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            //Listadapter erstellen
            ArrayList<Fish> filteredFishList = (ArrayList<Fish>) realFishList.clone();
            adapter = new FishListAdapter(this, R.layout.adapter_fish_view_layout, filteredFishList);
            mListView.setAdapter(adapter);

            //Beim antippen eines Eintrages im Listview soll die Details Activity geöffnet werden
            mListView.setOnItemClickListener((parent, view, position, id) -> {
                Fish fish = (Fish) mListView.getItemAtPosition(position);
                openFishDetails(fish);
                //Log.d(TAG, "Name " + insect.getName());
            });

            mListView.setOnItemLongClickListener((parent, view, position, id) -> {
                Fish fish = (Fish) mListView.getItemAtPosition(position);
                quickMarkFish(fish);
                return true;
            });


            //checkboxes initialisieren
            checkCurrent = findViewById(R.id.checkBoxCurrentFish);
            checkCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    refreshFilter(adapter);
                }
            });
            checkToday = findViewById(R.id.checkBoxCatchableTodayFish);
            checkToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    refreshFilter(adapter);
                }
            });
            checkCaught = findViewById(R.id.checkBoxFilterCaughtFish);
            checkCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshFilter(adapter);
            }
            });
        if(Util.adsEnabled) {
            //bottom_banner für ads laden
            mAdView = findViewById(R.id.bottombannerviewfish);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void refreshFilter(FishListAdapter adapter){
        //Einstellung für die Südhalbkugel aus den Sharedpreferences Laden
        boolean southernActive = Util.loadBoolFromPref("Settings","switchSouthern",MODE_PRIVATE,this);
        ArrayList <Fish> fishList = Util.filterTheList(realFishList, checkCurrent.isChecked(), southernActive, checkToday.isChecked(),checkCaught.isChecked(),"Fish",this);
        adapter.clear();


        adapter.isSouthernHemishereActive = southernActive;
        //Status des "momentan fangbar" laden
        adapter.isCurrentCatchableActive = checkCurrent.isChecked();
        //Status des "heute fangbar" laden
        adapter.isTodayCatchable = checkToday.isChecked();
        adapter.addAll(fishList);
    }

    private void openFishDetails(Fish fish){
        Intent intent = new Intent(FishList.this, FishDetails.class);
        intent.putExtra("FishDetails", fish.getId());
        FishList.this.startActivity(intent);
    }

    private void quickMarkFish(Fish fish){
        boolean isMarked = Util.loadBoolFromPref("Fish", Integer.toString(fish.getId()),MODE_PRIVATE,this);

        Util.saveBoolToPref("Fish", Integer.toString(fish.getId()), !isMarked,MODE_PRIVATE,this);

        adapter.notifyDataSetChanged();

    }

}
