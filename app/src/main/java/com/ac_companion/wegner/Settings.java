package com.ac_companion.wegner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class Settings extends AppCompatActivity {
    private Switch settingSouthern;
    public static final String SHARED_PREFS = "Settings";
    public static final String SETTINGSOUTHERN = "switchSouthern";
    private boolean switchSouthernOnOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        settingSouthern = findViewById(R.id.switchsouthern);

        settingSouthern.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveData();
            }
        });

        loadData();
        updateViews();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SETTINGSOUTHERN, settingSouthern.isChecked());

        editor.apply();

        Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        switchSouthernOnOff = sharedPreferences.getBoolean(SETTINGSOUTHERN, false);
    }

    public void updateViews(){
        settingSouthern.setChecked(switchSouthernOnOff);
    }
}