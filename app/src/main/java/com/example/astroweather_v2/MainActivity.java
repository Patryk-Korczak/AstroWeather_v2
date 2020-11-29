package com.example.astroweather_v2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    float longitude;
    float latitude;
    float refreshTime;

    ViewPager2 myViewPager;
    ViewPager2Adapter viewPager2Adapter;
    Fragment mySunFragment;
    Fragment myMoonFragment;

    AstroCalculator.Location myAstroLocation;
    AstroCalculator myAstroCalculator;
    AstroDateTime myAstroDateTime;
    long deviceTime = System.currentTimeMillis();
    Date myDate = Calendar.getInstance().getTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.my_toolbar));

        try {
            latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("latitude", "0"));
            longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("longitude", "0"));
            refreshTime = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("refresh", "15"));
        } catch (Exception e) {
            latitude = 0;
            longitude = 0;
            refreshTime = 15;
        }

        updateAstroData();

        myViewPager = findViewById(R.id.viewPager);
        if(myViewPager != null) {
            viewPager2Adapter = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle());
            viewPager2Adapter.addFragment(myMoonFragment = new MoonFragment());
            viewPager2Adapter.addFragment(mySunFragment = new SunFragment());
            myViewPager.setAdapter(viewPager2Adapter);
        } else {

            mySunFragment = new SunFragment();
            myMoonFragment = new MoonFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mySunFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, myMoonFragment)
                    .commit();
        }

        TextClock myClock = findViewById(R.id.textClock);
        myClock.setFormat24Hour("MMM dd, yyyy kk:mm:ss");
        myClock.setFormat12Hour("MMM dd, yyyy kk:mm:ss");
        myClock.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btnSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        myViewPager = findViewById(R.id.viewPager);
        if(myViewPager != null) {
            viewPager2Adapter = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle());
            viewPager2Adapter.addFragment(myMoonFragment = new MoonFragment());
            viewPager2Adapter.addFragment(mySunFragment = new SunFragment());
            myViewPager.setAdapter(viewPager2Adapter);
        } else {

            mySunFragment = new SunFragment();
            myMoonFragment = new MoonFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mySunFragment)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment2, myMoonFragment)
                    .commit();
        }
        updateAstroData();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("latitude")) {
            latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("latitude", "0"));
        }

        if (key.equals("longitude")) {
            longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("longitude", "0"));
        }

        if (key.equals("refresh")) {
            refreshTime = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(this).getString("refresh", "15"));
        }

        updateAstroData();
    }

    public void updateAstroData() {
        //update Astro to current time and coordinates.
        this.myAstroDateTime = new AstroDateTime(Integer.parseInt(new SimpleDateFormat("yyyy", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("MM", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("dd", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("hh", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("mm", Locale.getDefault()).format(deviceTime)),
                Integer.parseInt(new SimpleDateFormat("ss", Locale.getDefault()).format(deviceTime)),
                this.myDate.getTimezoneOffset(), true);

        this.myAstroLocation = new AstroCalculator.Location(this.latitude, this.longitude);

        this.myAstroCalculator = new AstroCalculator(this.myAstroDateTime, this.myAstroLocation);
    }
}