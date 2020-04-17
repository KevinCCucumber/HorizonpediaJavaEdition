package com.ac_companion.wegner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
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
        int listPosition;
        listPosition = mIntent.getIntExtra("FlowerDetails", 0);
        //flowerId = Util.getGlobalFlowerList().get(listPosition).getId();
        localFlower = Util.getGlobalFlowerList().get(listPosition);
        flowerId = localFlower.getId();

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
        TextView tvName = findViewById(R.id.nameviewflowerdetails);
        TextView tvType = findViewById(R.id.typeviewflowerdetails);
        ImageView tvRelative1_1 = findViewById(R.id.relative1view1details);
        ImageView tvRelative1_2 = findViewById(R.id.relative1view2details);
        ImageView tvRelative2_1 = findViewById(R.id.relative2view1details);
        ImageView tvRelative2_2 = findViewById(R.id.relative2view2details);
        ImageView tvRelative3_1 = findViewById(R.id.relative3view1details);
        ImageView tvRelative3_2 = findViewById(R.id.relative3view2details);
        TextView tvX0 = findViewById(R.id.viewX1details);
        TextView tvX1 = findViewById(R.id.viewX2details);
        TextView tvX2 = findViewById(R.id.viewX3details);
        TextView tvstar0 = findViewById(R.id.viewstar1details);
        TextView tvstar1 = findViewById(R.id.viewstar2details);
        ImageView ivNeedsCan = findViewById(R.id.goldencandetails);

        ivBild.setImageResource(localFlower.getImgID());
        tvName.setText(localFlower.getName());
        tvType.setText(localFlower.getFlowerType());

        if (localFlower.getNeedsGoldenCan()){
            ivNeedsCan.setVisibility(View.VISIBLE);
            ivNeedsCan.setImageResource(R.drawable.toolwateringgold);
        }
        if(localFlower.getNeedsBredFlower()){
            tvstar0.setVisibility(View.VISIBLE);
            tvstar1.setVisibility(View.VISIBLE);
        }

        int relative1_1ID = -1, relative1_2ID = -1, relative2_1ID = -1, relative2_2ID = -1, relative3_1ID = -1, relative3_2ID = -1;
        ArrayList<Integer> relatives;
        relatives = localFlower.getRelatives();

        for (int i = 0; i<relatives.size();i++) {
            switch (i){
                case 0:  relative1_1ID = relatives.get(i);
                    tvX0.setVisibility(View.VISIBLE);
                    tvRelative1_1.setImageResource(Util.getFlowerfromList(relative1_1ID).getImgID());
                    break;
                case 1:  relative1_2ID = relatives.get(i);
                    tvRelative1_2.setImageResource(Util.getFlowerfromList(relative1_2ID).getImgID());
                    break;
                case 2:  relative2_1ID= relatives.get(i);
                    tvRelative2_1.setImageResource(Util.getFlowerfromList(relative2_1ID).getImgID());
                    tvX1.setVisibility(View.VISIBLE);
                    break;
                case 3:  relative2_2ID = relatives.get(i);
                    tvRelative2_2.setImageResource(Util.getFlowerfromList(relative2_2ID).getImgID());
                    break;
                case 4:  relative3_1ID = relatives.get(i);
                    tvRelative3_1.setImageResource(Util.getFlowerfromList(relative3_1ID).getImgID());
                    tvX2.setVisibility(View.VISIBLE);
                    break;
                case 5:  relative3_2ID = relatives.get(i);
                    tvRelative3_2.setImageResource(Util.getFlowerfromList(relative3_2ID).getImgID());
                    break;
            }

        }

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