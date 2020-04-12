package com.ac_companion.wegner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Fossil implements Catchable, Serializable {
    private int price, id;
    private ArrayList<Integer> months_north = new ArrayList<>(), months_south = new ArrayList<>();
    private int [][] timespan;
    private boolean caught;
    private HashMap<String,String> nameHash = new HashMap<>(), locationHash = new HashMap<>();

    public Fossil(JSONObject fossil) throws Exception{
        final String tagFossil = "Insect Constructor";

        price = fossil.getInt("price");

        id = fossil.getInt("id");

        //Namen in die Hashmap laden
        JSONObject names = fossil.getJSONObject("name");

        Iterator<String> keys = names.keys();
        while (keys.hasNext()){
            String key = keys.next();
            this.nameHash.put(key,names.getString(key));
        }

    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public int getImgID(){
        int picid;
        try {
            Class res = R.drawable.class;
            Field field = res.getField("fo" + id);
            picid = field.getInt(null);
        }
        catch (Exception e) {
            //Log.e("getIMGID Insect " + getName(), "Failure to get drawable id, using ID 2131099895 (nopic)", e);
            picid = R.drawable.nopic;
        }
        return picid;
    }
    public int getImgIDDetailed(){
        int picid;
        try {
            Class res = R.drawable.class;
            Field field = res.getField("fo" + id);
            picid = field.getInt(null);
        }
        catch (Exception e) {
            //Log.e("getIMGID Insect " + getName(), "Failure to get drawable id, using ID 2131099895 (nopic)", e);
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
    public boolean isCatchable(int a, int b, boolean c){ //Diese Funktion muss nur des Interfaces wegen verwendet werden
        return true;
    }
    public boolean isCatchableAtMonth(int a, boolean b){ //Diese Funktion muss nur des Interfaces wegen verwendet werden
        return true;
    }

}
