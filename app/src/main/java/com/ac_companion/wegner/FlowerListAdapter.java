package com.ac_companion.wegner;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class FlowerListAdapter extends ArrayAdapter<Flower> {
    private static final String TAG = "FlowerListAdapter";
    public boolean isSouthernHemishereActive, isCurrentCatchableActive, isTodayCatchable;
    private Context mContext;
    int mResource;


    public FlowerListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Flower> objects) {
        super(context, resource, objects);
        this.mContext = context;
        Log.d(TAG,"FlowerListAdapter started");
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, null);

        String price;
        String name = getItem(position).getName();
        String type = getItem(position).getFlowerType();

        //preisanzeige von -1 verhindern

        ImageView ivBild = convertView.findViewById(R.id.iconviewflower);
        TextView tvName = convertView.findViewById(R.id.nameviewflower);
        TextView tvType = convertView.findViewById(R.id.typeviewflower);
        ImageView tvRelative1_1 = convertView.findViewById(R.id.relative1view1);
        ImageView tvRelative1_2 = convertView.findViewById(R.id.relative1view2);
        ImageView tvRelative2_1 = convertView.findViewById(R.id.relative2view1);
        ImageView tvRelative2_2 = convertView.findViewById(R.id.relative2view2);
        ImageView tvRelative3_1 = convertView.findViewById(R.id.relative3view1);
        ImageView tvRelative3_2 = convertView.findViewById(R.id.relative3view2);
        TextView tvX0 = convertView.findViewById(R.id.viewX1);
        TextView tvX1 = convertView.findViewById(R.id.viewX2);
        TextView tvX2 = convertView.findViewById(R.id.viewX3);
        ImageView ivNeedsCan = convertView.findViewById(R.id.goldencan);

        ivBild.setImageResource(getItem(position).getImgID());
        tvName.setText(name);
        tvType.setText(type);

        if (getItem(position).getNeedsGoldenCan()){
            ivNeedsCan.setVisibility(View.VISIBLE);
            ivNeedsCan.setImageResource(R.drawable.toolwateringgold);
        }

        int relative1_1ID = -1, relative1_2ID = -1, relative2_1ID = -1, relative2_2ID = -1, relative3_1ID = -1, relative3_2ID = -1;
        ArrayList<Integer> relatives;
        relatives = getItem(position).getRelatives();

        for (int i = 0; i<relatives.size();i++) {
            switch (i){
                case 0:  relative1_1ID = relatives.get(i);
                    tvX0.setVisibility(View.VISIBLE);
                    tvRelative1_1.setImageResource(Util.globalFlowerList.get(relative1_1ID).getImgID());
                    break;
                case 1:  relative1_2ID = relatives.get(i);
                    tvRelative1_2.setImageResource(Util.globalFlowerList.get(relative1_2ID).getImgID());
                    break;
                case 2:  relative2_1ID= relatives.get(i);
                    tvRelative2_1.setImageResource(Util.globalFlowerList.get(relative2_1ID).getImgID());
                    tvX1.setVisibility(View.VISIBLE);
                    break;
                case 3:  relative2_2ID = relatives.get(i);
                    tvRelative2_2.setImageResource(Util.globalFlowerList.get(relative2_2ID).getImgID());
                    break;
                case 4:  relative3_1ID = relatives.get(i);
                    tvRelative3_1.setImageResource(Util.globalFlowerList.get(relative3_1ID).getImgID());
                    tvX2.setVisibility(View.VISIBLE);
                    break;
                case 5:  relative3_2ID = relatives.get(i);
                    tvRelative3_2.setImageResource(Util.globalFlowerList.get(relative3_2ID).getImgID());
                    break;
            }

        }



        boolean isCaught = Util.loadBoolFromPref("Flower", Integer.toString(getItem(position).getId()), Context.MODE_PRIVATE,mContext);
        //Log.d("FiSCHLIST", getItem(position).getName() + " " + isCaught);
        if (isCaught){
            //grasgrün = 8190976
            //emerald rgb(0,255,127)
            tvName.setTextColor(Color.rgb(80,200,120));
        }

        return convertView;
    }

}