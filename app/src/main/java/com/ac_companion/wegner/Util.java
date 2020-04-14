package com.ac_companion.wegner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Util {
    public static Context context;
    public static ArrayList<Insect> globalInsectList = new ArrayList<Insect>();
    public static ArrayList<Fish> globalFishList = new ArrayList<Fish>();
    public static ArrayList<Fossil> globalFossilList = new ArrayList<Fossil>();
    public static ArrayList<Flower> globalFlowerList = new ArrayList<Flower>();

    //Globaler Werbebannerstatus:
    public static final boolean adsEnabled = true; /////////////////////////////////////////////////////////

    public static String getLanguage(){
        String language ="";
        //Sprache bestimmen
        Locale primaryLocale = Util.context.getResources().getConfiguration().getLocales().get(0);
        language = primaryLocale.getISO3Language();
        //System.out.println(language);
        return language;
    }

    public static <T extends Catchable> ArrayList<T> filterTheList(ArrayList<T> completeAnimalList, boolean isCurrentlyCatchable, boolean isSouthernHemisphere, boolean isTodayCatchable, boolean filterCaught, String settingsName, Context mContext){
        final String TAG = "refreshFilter";
        int currentMonth = Util.currentMonth(), currentHour = Util.currentTime(); //Die Monate fangen bei 0 an zu zählen, deshalb +1
        boolean isCaught;

        //Log.d(TAG,"Monat: " + currentMonth + " Stunde: " + currentHour + " Southern?: " + checkSouthern.isActivated() );

        ArrayList<T> filteredAnimalList = (ArrayList<T>) completeAnimalList.clone();

        for (int i = filteredAnimalList.size()-1; i>=0;i--){
            T currentAnimal = filteredAnimalList.get(i);

            isCaught =  Util.loadBoolFromPref(settingsName, Integer.toString(currentAnimal.getId()), Context.MODE_PRIVATE,mContext);

            //Log.d(TAG,"Tier " + currentAnimal.getName() + " ist momentan fangbar: " + currentAnimal.isCatchable(currentHour,currentMonth,isSouthernHemisphere) + " ist diesen Monat fangbar " + currentAnimal.isCatchableAtMonth(currentMonth,isSouthernHemisphere));
            //Wenn die Leistung zu kagge is, dann sollte checkCurrent.isChecked() in ein separates if-Statemen außerhalb der Schleife
            if((isCurrentlyCatchable && !currentAnimal.isCatchable(currentHour, currentMonth, isSouthernHemisphere)) || (isTodayCatchable && !currentAnimal.isCatchableAtMonth(currentMonth, isSouthernHemisphere)) || (filterCaught && isCaught) ) {
                filteredAnimalList.remove(i);
            }
        }

        return filteredAnimalList;
    }

    public static int currentTime (){
        int currentHour;
        Calendar currentTime = Calendar.getInstance();
        currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        return currentHour;
    }

    public static int currentMonth(){
        Calendar currentTime = Calendar.getInstance();
        int currentMonth = currentTime.get(Calendar.MONTH)+1; //Die Monate fangen bei 0 an zu zählen, deshalb +1
        return currentMonth;
    }

    public static ArrayList<Insect> setGlobalInsectList (InputStream insects_stream) throws Exception {
        String insects_total = "";
        JSONArray insects_array = null;
        //Insekten Inputstream anlegen
        BufferedReader reader = new BufferedReader(new InputStreamReader(insects_stream));
            //JSON laden
            String insects_string = reader.readLine();
            while (insects_string != null) {
                insects_total = insects_total + insects_string;
                insects_string = reader.readLine();
            }
            insects_array = new JSONArray(insects_total);

            // Objekte erstellen
            for (int i = 0; i < insects_array.length(); i++) {

                //JSON durchsuchen
                JSONObject currentins = insects_array.getJSONObject(i);
                System.out.println(currentins.getInt("price"));
                //Jedes Insekt aus dem InsektenArray wird hier instanziert damit es ein Objekt für den ListAdapter bekommt
                Insect e = new Insect(currentins);
                //Insekt zur Basisliste hinzufügen
                globalInsectList.add(e);

        }
        return globalInsectList;
     }
    public static ArrayList<Insect> getGlobalInsectList(){
        return globalInsectList;
     }

    public static ArrayList<Fish> setGlobalFishList (InputStream fish_stream) throws Exception {
        String fish_total = "";
        JSONArray fish_array = null;
        //Insekten Inputstream anlegen
        BufferedReader reader = new BufferedReader(new InputStreamReader(fish_stream));
        //JSON laden
        String fish_string = reader.readLine();
        while (fish_string != null) {
            fish_total = fish_total + fish_string;
            fish_string = reader.readLine();
        }
        fish_array = new JSONArray(fish_total);

        // Objekte erstellen
        for (int i = 0; i < fish_array.length(); i++) {

            //JSON durchsuchen
            JSONObject currentfish = fish_array.getJSONObject(i);
            //Jedes Insekt aus dem InsektenArray wird hier instanziert damit es ein Objekt für den ListAdapter bekommt
            Fish e = new Fish(currentfish);
            //Insekt zur Basisliste hinzufügen
            globalFishList.add(e);

        }
        return globalFishList;
    }
    public static ArrayList<Fish> getGlobalFishList(){
        return globalFishList;
    }

    public static ArrayList<Fossil> setGlobalFossilList (InputStream fossil_stream) throws Exception {
        String fossil_total = "";
        JSONArray fossil_array = null;
        //Insekten Inputstream anlegen
        BufferedReader reader = new BufferedReader(new InputStreamReader(fossil_stream));
        //JSON laden
        String fossil_string = reader.readLine();
        while (fossil_string != null) {
            fossil_total = fossil_total + fossil_string;
            fossil_string = reader.readLine();
        }
        fossil_array = new JSONArray(fossil_total);

        // Objekte erstellen
        for (int i = 0; i < fossil_array.length(); i++) {

            //JSON durchsuchen
            JSONObject currentfos = fossil_array.getJSONObject(i);
            //Jedes Fossil aus dem FossilArray wird hier instanziert damit es ein Objekt für den ListAdapter bekommt
            Fossil e = new Fossil(currentfos);
            //Insekt zur Basisliste hinzufügen
            globalFossilList.add(e);

        }
        return globalFossilList;
    }
    public static ArrayList<Fossil> getGlobalFossilList(){
        return globalFossilList;
    }

    public static ArrayList<Flower> setGlobalFlowerList (InputStream flower_stream) throws Exception {
        String flower_total = "";
        JSONArray flower_array = null;
        //Blumen Inputstream anlegen
        BufferedReader reader = new BufferedReader(new InputStreamReader(flower_stream));
        //JSON laden
        String flower_string = reader.readLine();
        while (flower_string != null) {
            flower_total = flower_total + flower_string;
            flower_string = reader.readLine();
        }
        flower_array = new JSONArray(flower_total);

        // Objekte erstellen
        for (int i = 0; i < flower_array.length(); i++) {

            //JSON durchsuchen
            JSONObject currentflo = flower_array.getJSONObject(i);
            //Jede Blume aus dem FlowerArray wird hier instanziert damit es ein Objekt für den ListAdapter bekommt
            Flower e = new Flower(currentflo);
            //Blumen zur Basisliste hinzufügen
            globalFlowerList.add(e);

        }
        return globalFlowerList;
    }

    public static ArrayList<Flower> getGlobalFlowerList(){
        return globalFlowerList;
    }



    //prefnames: "Settings", "Insects", "Fishes", "Fossils", savedObjectName = ObjectID aus Json
    public static void saveBoolToPref(String prefName, String savedObjectName, Boolean objectBool, int mode, Context context){
        //Shared preferences initialisieren
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, mode);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(savedObjectName, objectBool);

        editor.apply();

        //Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    public static boolean loadBoolFromPref(String prefName, String savedObjectName, int mode, Context context){
        boolean objectBool = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences(prefName, mode);
        objectBool = sharedPreferences.getBoolean(savedObjectName, false);
        return objectBool;
    }

    public boolean getAdsEnabled(){
        return adsEnabled;
    }
}
