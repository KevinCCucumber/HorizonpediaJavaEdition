package com.ac_companion.wegner;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Fish implements Catchable {
    private int price, id, sizeInt;
    private ArrayList<Integer> months_north = new ArrayList<>(), months_south = new ArrayList<>();
    private int [][] timespan;
    private boolean caught, hasFin, isNarrow;
    private String size;
    private HashMap<String,String> nameHash = new HashMap<>(), locationHash = new HashMap<>();

    public Fish(JSONObject fish) throws Exception{
        final String tagFish = "Fish Constructor";

        price = fish.getInt("price");

        id = fish.getInt("id");

        //Schattenwerte/Größe laden
        JSONObject shadows = fish.getJSONObject("shadow");
        sizeInt = shadows.getInt("size");
        hasFin = shadows.getBoolean("has_fin");
        isNarrow = shadows.getBoolean("is_narrow");

        //Namen und Fundorte in die Hashmaps laden
        JSONObject names = fish.getJSONObject("name");

        Iterator<String> keys = names.keys();
        while (keys.hasNext()){
            String key = keys.next();
            this.nameHash.put(key,names.getString(key));
        }

        JSONObject locations = fish.getJSONObject("location");

        keys = locations.keys();
        while (keys.hasNext()){
            String key = keys.next();
            this.locationHash.put(key,locations.getString(key));
        }

        //Monate auf Nord und Südhalbkugel
        JSONArray north = fish.getJSONArray("months_north");
        for(int i = 0;i<north.length();i++){
            if(north.getBoolean(i)){
                months_north.add(i+1);
            }
        }
        JSONArray south = fish.getJSONArray("months_south");
        for(int i = 0;i<south.length();i++){
            if(south.getBoolean(i)){
                months_south.add(i+1);
            }
        }

        //Fangzeiten setzen
        JSONArray time = fish.getJSONArray("time");
        timespan = new int [time.length()][];
        for(int i = 0;i<time.length();i++){
            JSONArray actualtime = time.getJSONArray(i);
            timespan[i] = new int[2]; //Hier wird an der Stelle i in timespan ein Array mit der Länge 2 initialisiert
            //Zeit besteht immer aus exakt 2 Elementen
            timespan[i][0]= actualtime.getInt(0);
            timespan[i][1]= actualtime.getInt(1);
        }
    }

    public int getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getMonths_north() {
        return months_north;
    }

    public ArrayList<Integer> getMonths_south() {
        return months_south;
    }

    public int[][] getTimespan() {
        return timespan;
    }

    public boolean getCaught() {
        return caught;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    public int getImgID(){
        int picid;
        try {
            Class res = R.drawable.class;
            Field field = res.getField("f" + id);
            picid = field.getInt(null);
        }
        catch (Exception e) {
            //Log.e("getIMGID Fish " + getName(), "Failure to get drawable id, using ID 2131099895 (nopic)", e);
            picid = R.drawable.nopic;
        }
        return picid;
    }

    public int getImgIDDetailed(){
        int picid;
        try {
            Class res = R.drawable.class;
            Field field = res.getField("f" + id + "_hi");
            picid = field.getInt(null);
        }
        catch (Exception e) {
            //Log.e("getIMGID Fish " + getName(), "Failure to get drawable id, using ID 2131099895 (nopic)", e);
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

    public String getLocation() {
        String language, location;
        language = Util.getLanguage();
        location = locationHash.get(language);
        return location;
    }

    public String getTimespanAsUhrzeit() {
        String uhrzeit="";
        for(int i = 0; i<timespan.length;i++) {
            if (!(i == 1)){
                uhrzeit = uhrzeit + timespan[i][0];
                uhrzeit = uhrzeit + "h - ";
                uhrzeit = uhrzeit + timespan[i][1];
                uhrzeit = uhrzeit + "h";
            }else{
                uhrzeit = uhrzeit + " + " + timespan[i][0];
                uhrzeit = uhrzeit + "h - ";
                uhrzeit = uhrzeit+timespan[i][1];
                uhrzeit = uhrzeit + "h";
            }
        }
        return uhrzeit;
    }

    public boolean isCatchable(int hour, int month, boolean southActive){
        return isCatchableAtHour(hour) && isCatchableAtMonth(month, southActive);
    }

    public boolean isCatchableAtHour(int hour) {
        boolean isCatchable = false, crossDay = false;

        for (int i = 0; i < timespan.length;i++){

            if(timespan[i][0]>timespan[i][1]){
                crossDay = true;
            }

            if (!crossDay && hour>=timespan[i][0] && hour <timespan[i][1]){
                isCatchable = true;
            }else if (crossDay && (hour>=timespan[i][0] || hour <timespan[i][1])){
                isCatchable = true;
            }
        }
        return isCatchable;
    }

    public boolean isCatchableAtMonth(int month, boolean southActive){
        boolean isCatchable = false;
        if(!southActive){
            for (int i = 0; i<months_north.size(); i++){
                if (month == months_north.get(i)) {
                    isCatchable = true;
                }
            }
        }else{
            for (int i = 0; i<months_south.size(); i++){
                if (month == months_south.get(i)) {
                    isCatchable = true;
                }
            }
        }

        return isCatchable;
    }

    public int isLastMonth (boolean catchableActive, boolean southActive){
        int isVanishing = 0, nextMonth;
        nextMonth = (Util.currentMonth() + 1)%12;
        if (catchableActive && !southActive && !months_north.contains(nextMonth)){
            isVanishing = 1;
        }
        if (catchableActive && southActive && !months_south.contains(nextMonth)){
            isVanishing = 1;
        }
        return isVanishing;
    }

    public void setSize (Context context){
        String language, size;
        language = Util.getLanguage();
            switch (sizeInt){
                case 1: size = context.getString(R.string.size1);
                case 2: size = context.getString(R.string.size2);
                case 3: size = context.getString(R.string.size3);
                case 4: size = context.getString(R.string.size4);
                case 5: size = context.getString(R.string.size5);
                case 6: size = context.getString(R.string.size6);
                default: size = "";
            }
            if (hasFin){
                size = size + context.getString(R.string.fin);
            }
            if(isNarrow){
                size = context.getString(R.string.narrow);
            }
    }

    public int getSizeInt (){
        return sizeInt;
    }
    public boolean hasFin(){
        return hasFin;
    }
    public boolean isNarrow(){
        return isNarrow;
    }
}
