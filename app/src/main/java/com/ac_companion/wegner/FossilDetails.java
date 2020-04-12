package com.ac_companion.wegner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FossilDetails extends AppCompatActivity {
    private final String TAG = "FossilDetails";
    private Fossil localFossile;
    public int fossilId;
    public boolean isCaughtTicked;
    TextView tvName;
    CheckBox checkBoxIsCaught;
    //private Intent mIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View holen
        setContentView(R.layout.activity_fossil_details);

        //Fossil id via getInt holen und damit Fossil aus der globalen Liste Laden
        Intent mIntent = getIntent();
        fossilId = mIntent.getIntExtra("FossilDetails", 0);
        localFossile = Util.getGlobalFossilList().get(fossilId);

        // UI Elemente vorbereiten
        tvName = findViewById(R.id.nameViewDetailsFossil);
        checkBoxIsCaught = findViewById(R.id.checkBoxGotFossil);
        //Die folgende Zeile ist ein überbleibsel 2focher COdekonvertierung und wird aus Künstlerischen Zwecken so belassen xD
        //Log.d(TAG, "Insektenname"+ localFish.getName() + "GlobalesInsekt" + Util.getGlobalFishList().get(fishId).getName() );


        //Checkbox got it initialisieren:
        checkBoxIsCaught = findViewById(R.id.checkBoxGotFossil);
        checkBoxIsCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveCaughtFossils();
            }
        });

        loadCaughtFossils();
        updateViews();

        //Inhalte setzen
        ImageView ivBild = findViewById(R.id.imageDetailsFossil);
        TextView tvPrice = findViewById(R.id.priceDetailsFossil);

        tvName.setText(localFossile.getName());
        ivBild.setImageResource(localFossile.getImgIDDetailed());
        tvPrice.setText(localFossile.getPrice() + "★");

    }

    public void saveCaughtFossils(){
        Util.saveBoolToPref("Fossil",Integer.toString(fossilId),checkBoxIsCaught.isChecked(), MODE_PRIVATE,this);
    }
    public void loadCaughtFossils(){
        isCaughtTicked = Util.loadBoolFromPref("Fossil", Integer.toString(fossilId),MODE_PRIVATE,this);
    }

    public void updateViews(){
        checkBoxIsCaught.setChecked(isCaughtTicked);
    }

}