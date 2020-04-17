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

public class InsectListAdapter extends ArrayAdapter<Insect> {
    private static final String TAG = "InsectListAdapter";
    public boolean isSouthernHemishereActive, isCurrentCatchableActive, isTodayCatchable;
    private Context mContext;
    int mResource;


    public InsectListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Insect> objects) {
        super(context, resource, objects);
        this.mContext = context;
        Log.d(TAG,"InsectListAdapter started");
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
        ImageView ivBild = convertView.findViewById(R.id.iconview);
        TextView tvPrice = convertView.findViewById(R.id.priceview);
        TextView tvLocation = convertView.findViewById(R.id.locationview);
        TextView tvName = convertView.findViewById(R.id.nameview);
        TextView tvTimes = convertView.findViewById(R.id.timeview);
        TextView tvLastMonth = convertView.findViewById(R.id.lastmonthview);

        ivBild.setImageResource(getItem(position).getImgID());
        tvPrice.setText(price + "★");
        tvLocation.setText(location);
        tvName.setText(name);
        tvTimes.setText(times);


        if(getItem(position).isLastMonth(isCurrentCatchableActive || isTodayCatchable, isSouthernHemishereActive) == 0){
            //tvLastMonth.setVisibility(View.INVISIBLE);
            tvLastMonth.setWidth(0);
        }
        else{
            //tvLastMonth.setVisibility(View.VISIBLE);
        }

        boolean isCaught = Util.loadBoolFromPref("Insects", Integer.toString(getItem(position).getId()), Context.MODE_PRIVATE,mContext);
        //Log.d("INSEKTLIST", getItem(position).getName() + " " + isCaught);
        if (isCaught){
            //grasgrün = 8190976
            //emerald rgb(0,255,127)
            tvName.setTextColor(Color.rgb(80,200,120));
        }

        return convertView;
    }

}