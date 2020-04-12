package com.ac_companion.wegner;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;

//Ad-shit importieren
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

//Test-Banner ID ca-app-pub-3940256099942544/6300978111
//Admob-ID dieser App ist ca-app-pub-5899740383904630~9341712675
//Bottom_Banner ca-app-pub-5899740383904630/7815564939
//Fullscreen_unterbrecher ca-app-pub-5899740383904630/9096857012

public class MainActivity extends AppCompatActivity {
    private ImageButton butF, butI, butFo, butFl, butS, butD;
    public String language;
    //private Context context;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Util.context=this.getApplicationContext();
        //Sprache abrufen
        language = Util.getLanguage();

        //mobile ads laden *würg*
        MobileAds.initialize(this, initializationStatus -> {
        });
        //bottom_banner laden
        if(Util.adsEnabled) {
            mAdView = findViewById(R.id.bottombannerviewmain);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
        //Unterbrechungswerbung vorbereiten
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Buttons initialisieren

        //Fische
        butF = findViewById(R.id.buttonFish);
        butF.setOnClickListener(v -> {
            openFishView();
            //prüfen ob die unterbrechungswerbung geladen ist, wenn ja, dann zeige sie
            /*if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
        });
        //Insekten
        butI = findViewById(R.id.buttonInsects);
        butI.setOnClickListener(v -> {
            openInsectView();
            //prüfen ob die unterbrechungswerbung geladen ist, wenn ja, dann zeige sie
           /* if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
        });
        //Fossils
        butFo = findViewById(R.id.buttonFossils);
        butFo.setOnClickListener(v -> {
            openFossilView();
            //prüfen ob die unterbrechungswerbung geladen ist, wenn ja, dann zeige sie
           /* if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
        });
        //Flowers
        butFo = findViewById(R.id.buttonFlowers);
        butFo.setOnClickListener(v -> {
            openFlowerView();
            //prüfen ob die unterbrechungswerbung geladen ist, wenn ja, dann zeige sie
           /* if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
        });
        //Settings
        butS = findViewById(R.id.buttonSettings);
        butS.setOnClickListener(v -> {
            openSettingsView();
            //prüfen ob die unterbrechungswerbung geladen ist, wenn ja, dann zeige sie
           /* if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }*/
        });
        //Discord
        butD = findViewById(R.id.buttonDiscord);
        butD.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://discord.gg/4Zcye47"); // missing 'http://' will cause crash
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

    }

    public void openFishView() {
        Intent intent = new Intent(this, FishList.class);
        MainActivity.this.startActivity(intent);
    }
    public void openInsectView() {
        Intent intent = new Intent(this, InsectList.class);
        MainActivity.this.startActivity(intent);
    }
    public void openFossilView() {
        Intent intent = new Intent(this, FossilList.class);
        MainActivity.this.startActivity(intent);
    }
    public void openFlowerView() {
        Intent intent = new Intent(this, FlowerList.class);
        MainActivity.this.startActivity(intent);
    }
    public void openSettingsView() {
        Intent intent = new Intent(this, Settings.class);
        MainActivity.this.startActivity(intent);
    }
}
