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

import org.w3c.dom.Text;

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
        TextView tvRelative1 = convertView.findViewById(R.id.relative1view);
        TextView tvRelative2 = convertView.findViewById(R.id.relative2view);
        TextView tvX = convertView.findViewById(R.id.viewX);
        ImageView ivNeedsCan = convertView.findViewById(R.id.goldencan);

        ivBild.setImageResource(getItem(position).getImgID());
        tvName.setText(name);
        tvType.setText(type);

        if (!getItem(position).getRelatives().isEmpty()){
            tvRelative1.setVisibility(View.VISIBLE);
            tvRelative2.setVisibility(View.VISIBLE);
            tvX.setVisibility(View.VISIBLE);
        }
        if (getItem(position).getNeedsGoldenCan()){
            ivNeedsCan.setVisibility(View.VISIBLE);
            ivNeedsCan.setImageResource(R.drawable.toolwateringgold);
        }
        int relative1ID, relative2ID;
        ArrayList<Integer> relatives;
        relatives = getItem(position).getRelatives();
        relative1ID = relatives.get(0);
        relative2ID = relatives.get(1);
        Util.getGlobalFlowerList().get(relative1ID).getName();

        tvRelative1.setText(Util.getGlobalFlowerList().get(relative1ID).getName());
        tvRelative2.setText(Util.getGlobalFlowerList().get(relative2ID).getName());
        tvX.setVisibility(View.VISIBLE);


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