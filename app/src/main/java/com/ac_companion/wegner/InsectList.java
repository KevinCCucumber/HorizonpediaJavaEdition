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

public class InsectList extends AppCompatActivity {
    private static final String TAG = "InsectsCatchable";
    private CheckBox checkCurrent, checkToday, checkCaught;
    private ArrayList<Insect> realInsectsList = new ArrayList<>();
    InsectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String insects_total = "";
        JSONArray insects_array = null;
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Started.");
        setContentView(R.layout.activity_insect_list);
        ListView mListView = findViewById(R.id.listInsects);
        AdView mAdView;

        //Insekten Inputstream anlegen
        InputStream insects_stream = getResources().openRawResource(R.raw.insects);

        try {
            if (Util.getGlobalInsectList().isEmpty()){
                realInsectsList = Util.setGlobalInsectList(insects_stream);
            }else
            {
                realInsectsList = Util.getGlobalInsectList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            //Listadapter erstellen
            ArrayList<Insect> filteredInsectList = (ArrayList<Insect>) realInsectsList.clone();
            adapter = new InsectListAdapter(this, R.layout.adapter_insect_view_layout, filteredInsectList);
            mListView.setAdapter(adapter);

            //Beim antippen eines Eintrages im Listview soll die Details Activity geöffnet werden
            mListView.setOnItemClickListener((parent, view, position, id) -> {
                Insect insect = (Insect) mListView.getItemAtPosition(position);
                openInsectDetails(insect);
                //Log.d(TAG, "Name " + insect.getName());
            });

            mListView.setOnItemLongClickListener((parent, view, position, id) -> {
                Insect insect = (Insect) mListView.getItemAtPosition(position);
                quickMarkInsect(insect);
                return true;
            });


            //checkboxes initialisieren
            checkCurrent = findViewById(R.id.checkBoxCurrentInsect);
            checkCurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    refreshFilter(adapter);
                }
            });
            checkToday = findViewById(R.id.checkBoxCatchableTodayInsect);
            checkToday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    refreshFilter(adapter);
                }
            });
            checkCaught = findViewById(R.id.checkBoxFilterCaughtInsect);
            checkCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshFilter(adapter);
            }
            });
        if(Util.adsEnabled) {
            //bottom_banner für ads laden
            mAdView = findViewById(R.id.bottombannerviewinsects);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void refreshFilter(InsectListAdapter adapter){
        //Einstellung für die Südhalbkugel aus den Sharedpreferences Laden
        boolean southernActive = Util.loadBoolFromPref("Settings","switchSouthern",MODE_PRIVATE,this);
        ArrayList <Insect> insectList = Util.filterTheList(realInsectsList, checkCurrent.isChecked(), southernActive, checkToday.isChecked(), checkCaught.isChecked(), "Insects",this);
        adapter.clear();


        adapter.isSouthernHemishereActive = southernActive;
        //Status des "momentan fangbar" laden
        adapter.isCurrentCatchableActive = checkCurrent.isChecked();
        //Status des "heute fangbar" laden
        adapter.isTodayCatchable = checkToday.isChecked();
        adapter.addAll(insectList);
    }

    private void openInsectDetails(Insect insect){
        Intent intent = new Intent(InsectList.this, InsectDetails.class);
        intent.putExtra("InsectDetails", insect.getId());
        InsectList.this.startActivity(intent);
    }
    private void quickMarkInsect(Insect insect){
        boolean isMarked = Util.loadBoolFromPref("Insects", Integer.toString(insect.getId()),MODE_PRIVATE,this);

        Util.saveBoolToPref("Insects", Integer.toString(insect.getId()), !isMarked,MODE_PRIVATE,this);

        adapter.notifyDataSetChanged();
    }

}
