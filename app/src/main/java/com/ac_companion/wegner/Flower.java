package com.ac_companion.wegner;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Flower implements Catchable {
    private int id;
    private ArrayList<Integer> relativesIDs = new ArrayList<>();
    private boolean requiresBredFlower, requiresGoldenWateringCan;
    private HashMap<String, String> nameHash = new HashMap<>();

    public Flower(JSONObject flower) throws Exception {
        final String tagFlower = "Flower Constructor";


        id = flower.getInt("id");

        //Namen und Fundorte in die Hashmaps laden
        JSONObject names = flower.getJSONObject("name");

        Iterator<String> keys = names.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            this.nameHash.put(key, names.getString(key));
        }

        JSONArray sources = flower.getJSONArray("sources");
        for (int i = 0; i< sources.length(); i++){
            JSONObject source = sources.getJSONObject(i);
            JSONArray relatives = source.getJSONArray("flowers");
            for (int j = 0; j < relatives.length(); j++) {
                relativesIDs.add(relatives.getInt(j));
            }
            requiresBredFlower = source.getBoolean("requires_cultivated_flowers");

            requiresGoldenWateringCan = source.getBoolean("requires_gold_watering_can");
        }
        Log.d("FLOWER", getName() + " ID " + id + getFlowerType() + " " + relativesIDs);
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getRelatives() {
        return relativesIDs;
    }


    public int getImgID() {
        int picid;
        try {
            Class res = R.drawable.class;
            Field field = res.getField("fl" + id);
            picid = field.getInt(null);
        } catch (Exception e) {
            //Log.e("getIMGID Flower " + getName(), "Failure to get drawable id, using ID 2131099895 (nopic)", e);
            picid = R.drawable.nopic;
        }
        return picid;
    }

    public int getImgIDDetailed() {
        int picid;
        try {
            Class res = R.drawable.class;
            Field field = res.getField("fl" + id + "");
            picid = field.getInt(null);
        } catch (Exception e) {
            //Log.e("getIMGID Flower " + getName(), "Failure to get drawable id, using ID 2131099895 (nopic)", e);
            picid = R.drawable.nopic;
        }
        return picid;
    }

    public String getName() {
        String language, name;
        language = Util.getLanguage();
        name = nameHash.get(language);
        return name;
    }

    public boolean getNeedsGoldenCan(){return requiresGoldenWateringCan;}

    public boolean getNeedsBredFlower(){return requiresBredFlower;}

    public String getFlowerType(){
        String flowerType;

        if(relativesIDs.isEmpty()){
            flowerType = "Main";
        }else{
            flowerType = "Hybrid";
        }

        return flowerType;
    }



    public boolean isCatchable(int hour, int month, boolean southActive) {
        return isCatchableAtHour(hour) && isCatchableAtMonth(month, southActive);
    }

    public boolean isCatchableAtHour(int hour) {
        return true;
    }

    public boolean isCatchableAtMonth(int month, boolean southActive) {
        return true;
    }

}
