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

public class FossilListAdapter extends ArrayAdapter<Fossil> {
    private static final String TAG = "FossilListAdapter";
    public boolean isSouthernHemishereActive, isCurrentCatchableActive, isTodayCatchable;
    private Context mContext;
    int mResource;


    public FossilListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Fossil> objects) {
        super(context, resource, objects);
        this.mContext = context;
        Log.d(TAG,"FossilListAdapter started");
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, null);

        String price;
        String name = getItem(position).getName();

        //preisanzeige von -1 verhindern
        if(getItem(position).getPrice() > 0 ){
            price = Integer.toString(getItem(position).getPrice());
        }else
        {
            price = "?";
        }
        ImageView ivBild = convertView.findViewById(R.id.iconViewFossils);
        TextView tvPrice = convertView.findViewById(R.id.priceViewFossils);
        TextView tvName = convertView.findViewById(R.id.nameViewFossils);

        ivBild.setImageResource(getItem(position).getImgID());
        tvPrice.setText(price + "★");
        tvName.setText(name);

        boolean isCaught = Util.loadBoolFromPref("Fossil", Integer.toString(getItem(position).getId()), Context.MODE_PRIVATE,mContext);
        //Log.d("FiSCHLIST", getItem(position).getName() + " " + isCaught);
        if (isCaught){
            //grasgrün = 8190976
            //emerald rgb(0,255,127)
            tvName.setTextColor(Color.rgb(80,200,120));
        }

        return convertView;
    }

}