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

public class FlowerDetails extends AppCompatActivity {
    private final String TAG = "FlowerDetails";
    private Flower localFlower;
    public int flowerId;
    public boolean isCaughtTicked;
    TextView tvName;
    CheckBox checkBoxIsCaught;
    //private Intent mIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View holen
        setContentView(R.layout.activity_flower_details);

        //Insekt id via getInt holen und damit Insekt aus der globalen Liste Laden
        Intent mIntent = getIntent();
        flowerId = mIntent.getIntExtra("FlowerDetails", 0);
        localFlower = Util.getGlobalFlowerList().get(flowerId);

        // UI Elemente vorbereiten
        tvName = findViewById(R.id.nameviewflowerdetails);
        checkBoxIsCaught = findViewById(R.id.checkBoxGotFlower);
        //Log.d(TAG, "Insektenname"+ localFlower.getName() + "GlobalesInsekt" + Util.getGlobalFlowerList().get(flowerId).getName() );


        //Checkbox got it initialisieren:
        checkBoxIsCaught = findViewById(R.id.checkBoxGotFlower);
        checkBoxIsCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveCaughtFlower();
            }
        });

        loadCaughtFlower();
        updateViews();

        //Inhalte setzen
        ImageView ivBild = findViewById(R.id.imagedetailsflower);

        tvName.setText(localFlower.getName());
        ivBild.setImageResource(localFlower.getImgIDDetailed());

        //Wenn der Blumentyp Hybrid ist, müssen hier die Eltern gesetzt werden:


    }

    public void saveCaughtFlower(){
        Util.saveBoolToPref("Flower",Integer.toString(flowerId),checkBoxIsCaught.isChecked(), MODE_PRIVATE,this);
    }
    public void loadCaughtFlower(){
        isCaughtTicked = Util.loadBoolFromPref("Flower", Integer.toString(flowerId),MODE_PRIVATE,this);
    }

    public void updateViews(){
        checkBoxIsCaught.setChecked(isCaughtTicked);
    }

}