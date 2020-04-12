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

public class FossilList extends AppCompatActivity {
    private static final String TAG = "FossilCatchable";
    private CheckBox checkCaught;
    private ArrayList<Fossil> realFossilList = new ArrayList<>();
    FossilListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String fossil_total = "";
        JSONArray fossil_array = null;
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Started.");
        setContentView(R.layout.activity_fossil_list);
        ListView mListView = findViewById(R.id.listFossile);
        AdView mAdView;

        //Fisch Inputstream anlegen
        InputStream fossil_stream = getResources().openRawResource(R.raw.fossils);

        try {
            if (Util.getGlobalFossilList().isEmpty()){
                realFossilList = Util.setGlobalFossilList(fossil_stream);
            }else
            {
                realFossilList = Util.getGlobalFossilList();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            //Listadapter erstellen
            ArrayList<Fossil> filteredFossileList = (ArrayList<Fossil>) realFossilList.clone();
            adapter = new FossilListAdapter(this, R.layout.adapter_fossil_view_layout, filteredFossileList);
            mListView.setAdapter(adapter);

            //Beim antippen eines Eintrages im Listview soll die Details Activity geöffnet werden
            mListView.setOnItemClickListener((parent, view, position, id) -> {
                Fossil fossil = (Fossil) mListView.getItemAtPosition(position);
                openFossilDetails(fossil);
                //Log.d(TAG, "Name " + insect.getName());
            });

            mListView.setOnItemLongClickListener((parent, view, position, id) -> {
                Fossil fossil = (Fossil) mListView.getItemAtPosition(position);
                quickMarkFossil(fossil);
                return true;
            });


            //checkboxes initialisieren
            checkCaught = findViewById(R.id.checkBoxFilterCaughtFossil);
            checkCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                refreshFilter(adapter);
            }
            });
        if(Util.adsEnabled) {
            //bottom_banner für ads laden
            mAdView = findViewById(R.id.bottomBannerViewFossile);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void refreshFilter(FossilListAdapter adapter){
        //Einstellung für die Südhalbkugel aus den Sharedpreferences Laden
        boolean southernActive = Util.loadBoolFromPref("Settings","switchSouthern",MODE_PRIVATE,this);
        ArrayList <Fossil> fossilList = Util.filterTheList(realFossilList,false, false, false,checkCaught.isChecked(),"Fossil",this);
        adapter.clear();


        adapter.isSouthernHemishereActive = southernActive;
        //Status des "momentan fangbar" laden
        adapter.isCurrentCatchableActive = false;
        //Status des "heute fangbar" laden
        adapter.isTodayCatchable = false;
        adapter.addAll(fossilList);
    }

    private void openFossilDetails(Fossil fossil){
        Intent intent = new Intent(FossilList.this, FossilDetails.class);
        intent.putExtra("FossilDetails", fossil.getId());
        FossilList.this.startActivity(intent);
    }

    private void quickMarkFossil(Fossil fossil){
        boolean isMarked = Util.loadBoolFromPref("Fossil", Integer.toString(fossil.getId()),MODE_PRIVATE,this);

        Util.saveBoolToPref("Fossil", Integer.toString(fossil.getId()), !isMarked,MODE_PRIVATE,this);

        adapter.notifyDataSetChanged();

    }

}
