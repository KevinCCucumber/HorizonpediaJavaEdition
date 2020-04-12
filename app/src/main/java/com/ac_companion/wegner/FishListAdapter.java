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

public class FishListAdapter extends ArrayAdapter<Fish> {
    private static final String TAG = "FishListAdapter";
    public boolean isSouthernHemishereActive, isCurrentCatchableActive, isTodayCatchable;
    private Context mContext;
    int mResource;


    public FishListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Fish> objects) {
        super(context, resource, objects);
        this.mContext = context;
        Log.d(TAG,"FishListAdapter started");
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, null);

        String price;
        String name = getItem(position).getName();
        String location = getItem(position).getLocation();
        String times = getItem(position).getTimespanAsUhrzeit();

        //preisanzeige von -1 verhindern
        if(getItem(position).getPrice() > 0 ){
            price = Integer.toString(getItem(position).getPrice());
        }else
        {
            price = "?";
        }

        //Größe des Fisches ermitteln:
        String size = "";
        int sizeInt = getItem(position).getSizeInt();

        boolean isNarrow = getItem(position).isNarrow(), hasFin = getItem(position).hasFin();
        switch (sizeInt){
            case 1: size = size + mContext.getString(R.string.size1);
            break;
            case 2: size = size + mContext.getString(R.string.size2);
            break;
            case 3: size = size + mContext.getString(R.string.size3);
            break;
            case 4: size = size + mContext.getString(R.string.size4);
            break;
            case 5: size = size + mContext.getString(R.string.size5);
            break;
            case 6: size = size + mContext.getString(R.string.size6);
            break;
            case -1: size = "";
        }
        if (hasFin){
            size = size +  " " + mContext.getString(R.string.fin);
        }
        if(isNarrow){
            size = "" + mContext.getString(R.string.narrow);
        }

        ImageView ivBild = convertView.findViewById(R.id.iconviewfish);
        TextView tvPrice = convertView.findViewById(R.id.priceviewfish);
        TextView tvLocation = convertView.findViewById(R.id.locationviewfish);
        TextView tvName = convertView.findViewById(R.id.nameviewfish);
        TextView tvTimes = convertView.findViewById(R.id.timeviewfish);
        TextView tvLastMonth = convertView.findViewById(R.id.lastmonthviewfish);
        TextView tvSize = convertView.findViewById(R.id.sizeviewfish);

        ivBild.setImageResource(getItem(position).getImgID());
        tvPrice.setText(price + "★");
        tvLocation.setText(location);
        tvName.setText(name);
        tvTimes.setText(times);
        tvSize.setText(size);

        //Log.d("FISHSIZE", "s " + size);

        if(getItem(position).isLastMonth(isCurrentCatchableActive || isTodayCatchable, isSouthernHemishereActive) == 0){
            //tvLastMonth.setVisibility(View.INVISIBLE);
            tvLastMonth.setWidth(0);
        }
        else{
            //tvLastMonth.setVisibility(View.VISIBLE);
        }

        boolean isCaught = Util.loadBoolFromPref("Fish", Integer.toString(getItem(position).getId()), Context.MODE_PRIVATE,mContext);
        //Log.d("FiSCHLIST", getItem(position).getName() + " " + isCaught);
        if (isCaught){
            //grasgrün = 8190976
            //emerald rgb(0,255,127)
            tvName.setTextColor(Color.rgb(80,200,120));
        }

        return convertView;
    }

}