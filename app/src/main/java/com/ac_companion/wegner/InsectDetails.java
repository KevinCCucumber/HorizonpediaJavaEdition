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

public class InsectDetails extends AppCompatActivity {
    private final String TAG = "InsectDetails";
    private Insect localInsect;
    public int insectId;
    public boolean isCaughtTicked;
    TextView tvName;
    CheckBox checkBoxIsCaught;
    //private Intent mIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //View holen
        setContentView(R.layout.activity_insect_details);

        //Insekt id via getInt holen und damit Insekt aus der globalen Liste Laden
        Intent mIntent = getIntent();
        insectId = mIntent.getIntExtra("InsectDetails", 0);
        localInsect = Util.getGlobalInsectList().get(insectId);

        // UI Elemente vorbereiten
        tvName = findViewById(R.id.nameviewinsectdetails);
        checkBoxIsCaught = findViewById(R.id.checkBoxGotInsect);
        //Log.d(TAG, "Insektenname"+ localInsect.getName() + "GlobalesInsekt" + Util.getGlobalInsectList().get(insectId).getName() );


        //Checkbox got it initialisieren:
        checkBoxIsCaught = findViewById(R.id.checkBoxGotInsect);
        checkBoxIsCaught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveCaughtInsect();
            }
        });

        loadCaughtInsect();
        updateViews();

        //Inhalte setzen
        ImageView ivBild = findViewById(R.id.imagedetailsinsect);
        TextView tvPrice = findViewById(R.id.pricedetailsinsect);
        TextView tvLocation = findViewById(R.id.locationdetailinsect);
        TextView tvTimes = findViewById(R.id.timedetailinsect);

        tvName.setText(localInsect.getName());
        ivBild.setImageResource(localInsect.getImgIDDetailed());
        tvPrice.setText(localInsect.getPrice() + "★");
        tvLocation.setText(localInsect.getLocation());
        tvTimes.setText(localInsect.getTimespanAsUhrzeit());

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
            monthsActive = localInsect.getMonths_south();
        }else{
            monthsActive = localInsect.getMonths_north();
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

    public void saveCaughtInsect(){
        Util.saveBoolToPref("Insects",Integer.toString(insectId),checkBoxIsCaught.isChecked(), MODE_PRIVATE,this);
    }
    public void loadCaughtInsect(){
        isCaughtTicked = Util.loadBoolFromPref("Insects", Integer.toString(insectId),MODE_PRIVATE,this);
    }

    public void updateViews(){
        checkBoxIsCaught.setChecked(isCaughtTicked);
    }

}
