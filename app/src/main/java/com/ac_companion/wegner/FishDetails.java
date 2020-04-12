package com.ac_companion.wegner;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class FishDetails extends AppCompatActivity {
    private final String TAG = "FishDetails";
    private Fish localFish;
    public int fishId;
    public boolean isCaughtTicked;
    TextView tvName;
    CheckBox checkBoxIsCaught;
    //private Intent mIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View holen
        setContentView(R.layout.activity_fish_details);

        //Insekt id via getInt holen und damit Insekt aus der globalen Liste Laden
        Intent mIntent = getIntent();
        fishId = mIntent.getIntExtra("FishDetails", 0);
        localFish = Util.getGlobalFishList().get(fishId);

        // UI Elemente vorbereiten
        tvName = findViewById(R.id.nameviewfishdetails);
        checkBoxIsCaught = findViewById(R.id.checkBoxGotFish);
        //Log.d(TAG, "Insektenname"+ localFish.getName() + "GlobalesInsekt" + Util.getGlobalFishList().get(fishId).getName() );


        //Checkbox got it initialisieren:
        checkBoxIsCaught = findViewById(R.id.checkBoxGotFish);
        checkBoxIsCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveCaughtFish();
            }
        });

        loadCaughtFish();
        updateViews();

        //Größe aus dem Fisch laden
        String size = "";
        int sizeInt = localFish.getSizeInt();
        boolean isNarrow = localFish.isNarrow(), hasFin = localFish.hasFin();
        switch (sizeInt){
            case 1: size = size + getString(R.string.size1);
                break;
            case 2: size = size + getString(R.string.size2);
                break;
            case 3: size = size + getString(R.string.size3);
                break;
            case 4: size = size + getString(R.string.size4);
                break;
            case 5: size = size + getString(R.string.size5);
                break;
            case 6: size = size + getString(R.string.size6);
                break;
            case -1: size = "";
        }
        if (hasFin){
            size = size +  " " + getString(R.string.fin);
        }
        if(isNarrow){
            size = "" + getString(R.string.narrow);
        }

        //Inhalte setzen
        ImageView ivBild = findViewById(R.id.imagedetailsfish);
        TextView tvPrice = findViewById(R.id.pricedetailsfish);
        TextView tvLocation = findViewById(R.id.locationdetailfish);
        TextView tvTimes = findViewById(R.id.timedetailfish);
        TextView tvSize = findViewById(R.id.sizedetailsfish);

        tvName.setText(localFish.getName());
        ivBild.setImageResource(localFish.getImgIDDetailed());
        tvPrice.setText(localFish.getPrice() + "★");
        tvLocation.setText(localFish.getLocation());
        tvTimes.setText(localFish.getTimespanAsUhrzeit());
        tvSize.setText(size);

        //Monatsübersicht setzen
        boolean southernActive = Util.loadBoolFromPref("Settings","switchSouthern",MODE_PRIVATE,this);

        TextView tvJanuar = findViewById(R.id.januar);
        TextView tvFebruar = findViewById(R.id.februar);
        TextView tvMärz = findViewById(R.id.märz);
        TextView tvApril = findViewById(R.id.april);
        TextView tvMai = findViewById(R.id.mai);
        TextView tvJuni = findViewById(R.id.juni);
        TextView tvJuli = findViewById(R.id.juli);
        TextView tvAugust = findViewById(R.id.august);
        TextView tvSeptember = findViewById(R.id.september);
        TextView tvOktober = findViewById(R.id.oktober);
        TextView tvNovember = findViewById(R.id.november);
        TextView tvDezember = findViewById(R.id.dezember);

        ArrayList<Integer> monthsActive;
        if(southernActive){
            monthsActive = localFish.getMonths_south();
        }else{
            monthsActive = localFish.getMonths_north();
        }

        for (int i = 0; i < monthsActive.size();i++){
            if(monthsActive.get(i)==1){
                tvJanuar.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==2){
                tvFebruar.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==3){
                tvMärz.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==4){
                tvApril.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==5){
                tvMai.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==6){
                tvJuni.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==7){
                tvJuli.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==8){
                tvAugust.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==9){
                tvSeptember.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==10){
                tvOktober.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==11){
                tvNovember.setBackgroundColor(Color.rgb(80,200,120));
            }
            if(monthsActive.get(i)==12){
                tvDezember.setBackgroundColor(Color.rgb(80,200,120));
            }
        }


    }

    public void saveCaughtFish(){
        Util.saveBoolToPref("Fish",Integer.toString(fishId),checkBoxIsCaught.isChecked(), MODE_PRIVATE,this);
    }
    public void loadCaughtFish(){
        isCaughtTicked = Util.loadBoolFromPref("Fish", Integer.toString(fishId),MODE_PRIVATE,this);
    }

    public void updateViews(){
        checkBoxIsCaught.setChecked(isCaughtTicked);
    }

}