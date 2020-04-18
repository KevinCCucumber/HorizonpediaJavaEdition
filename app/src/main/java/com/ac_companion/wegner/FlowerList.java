package com.ac_companion.wegner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

//Admob-ID dieser App ist ca-app-pub-5899740383904630~9341712675
//Bottom_Banner ca-app-pub-5899740383904630/7815564939
//Fullscreen_unterbrecher ca-app-pub-5899740383904630/9096857012
//Ad-shit importieren

public class FlowerList extends AppCompatActivity {
    private static final String TAG = "FlowerCatchable";
    private CheckBox checkCurrent, checkToday, checkCaught;
    private ArrayList<Flower> realFlowerList = new ArrayList<>();
    FlowerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String flower_total = "";
        JSONArray flower_array = null;
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Started.");
        setContentView(R.layout.activity_flower_list);
        ListView mListView = findViewById(R.id.listFlower);
        AdView mAdView;

        //Fisch Inputstream anlegen
        InputStream flower_stream = getResources().openRawResource(R.raw.flowers);

        try {
            if (Util.getGlobalFlowerList().isEmpty()){
                Util.setGlobalFilteredFlowerList(Util.setGlobalFlowerList(flower_stream));
            }else
            {
                Util.setGlobalFilteredFlowerList(Util.getGlobalFlowerList());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Listadapter erstellen
        ArrayList<Flower> filteredFlowerList = (ArrayList<Flower>) Util.getGlobalfilteredFlowerList().clone();
        adapter = new FlowerListAdapter(this, R.layout.adapter_flower_view_layout, filteredFlowerList);
        mListView.setAdapter(adapter);

        //Beim antippen eines Eintrages im Listview soll die Details Activity geöffnet werden
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Flower flower = (Flower) mListView.getItemAtPosition(position);
            Log.d(TAG, "Clicked on entry " + position + " with ID " + flower.getId() + " " + flower.getName());
            openFlowerDetails(flower, position);
            //Log.d(TAG, "Name " + insect.getName());
        });

        mListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Flower flower = (Flower) mListView.getItemAtPosition(position);
            quickMarkFlower(flower);
            return true;
        });


        //checkboxes initialisieren
        /*checkCurrent = (CheckBox) findViewById(R.id.checkBoxCurrentFlower);
        checkCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshFilter(adapter);
            }
        });*/
        /*checkToday = (CheckBox) findViewById(R.id.checkBoxCatchableTodayFlower);
        checkToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshFilter(adapter);
            }
        });*/
        checkCaught = findViewById(R.id.checkBoxFilterCaughtFlower);
        checkCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshFilter(adapter);
            }
        });

        //bottom_banner für ads laden
        if(Util.adsEnabled) {
            mAdView = findViewById(R.id.bottombannerviewflower);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void refreshFilter(FlowerListAdapter adapter){
        //Liste mit eisntellungen Filtern
        ArrayList <Flower> flowerList = Util.filterTheList(Util.getGlobalfilteredFlowerList(), false, false, false,checkCaught.isChecked(),"Flower",this);
        adapter.clear();


        //adapter.isSouthernHemishereActive = southernActive;
        //Status des "momentan fangbar" laden
        //adapter.isCurrentCatchableActive = checkCurrent.isChecked();
        //Status des "heute fangbar" laden
        //adapter.isTodayCatchable = checkToday.isChecked();
        adapter.addAll(flowerList);
    }

    private void openFlowerDetails(Flower flower, int listPosition){
        Intent intent = new Intent(FlowerList.this, FlowerDetails.class);
        intent.putExtra("FlowerDetails", flower.getId());
        FlowerList.this.startActivity(intent);
    }

    private void quickMarkFlower(Flower flower){
        boolean isMarked = Util.loadBoolFromPref("Flower", Integer.toString(flower.getId()),MODE_PRIVATE,this);

        Util.saveBoolToPref("Flower", Integer.toString(flower.getId()), !isMarked,MODE_PRIVATE,this);

        adapter.notifyDataSetChanged();

    }

}
