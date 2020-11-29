package com.example.astroweather_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SunFragment extends Fragment {

    TextView latitude;
    TextView longitude;
    TextView sunriseTime;
    TextView sunriseAzimuth;
    TextView sunsetTime;
    TextView sunsetAzimuth;
    TextView civilSunrise;
    TextView civilSunset;

    Handler mHandler = new Handler();
    Runnable mTicker = null;

    public SunFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).updateAstroData();
        View view = inflater.inflate(R.layout.fragment_sun, container, false);
        this.latitude = view.findViewById(R.id.latitude);
        this.longitude = view.findViewById(R.id.longitude);
        this.sunriseTime = view.findViewById(R.id.sunriseTime);
        this.sunriseAzimuth = view.findViewById(R.id.sunriseAzimuth);
        this.sunsetTime = view.findViewById(R.id.sunsetTime);
        this.sunsetAzimuth = view.findViewById(R.id.sunsetAzimuth);
        this.civilSunrise = view.findViewById(R.id.civilSunrise);
        this.civilSunset = view.findViewById(R.id.civilSunset);

        this.latitude.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitude.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.sunriseTime.setText("Sunrise time: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getSunrise().toString());
        this.sunriseAzimuth.setText("Sunrise azimuth: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getAzimuthRise()));
        this.sunsetTime.setText("Sunset time: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getSunset().toString());
        this.sunsetAzimuth.setText("Sunset azimuth: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getAzimuthSet()));
        this.civilSunrise.setText("Civil sunrise: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getTwilightMorning().toString());
        this.civilSunset.setText("Civil sunset: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getTwilightEvening().toString());


        mTicker = new Runnable() {
            public void run() {
                ((MainActivity)getActivity()).updateAstroData();
                latitude.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
                longitude.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
                sunriseTime.setText("Sunrise time: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getSunrise().toString());
                sunriseAzimuth.setText("Sunrise azimuth: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getAzimuthRise()));
                sunsetTime.setText("Sunset time: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getSunset().toString());
                sunsetAzimuth.setText("Sunset azimuth: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getAzimuthSet()));
                civilSunrise.setText("Civil sunrise: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getTwilightMorning().toString());
                civilSunset.setText("Civil sunset: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getTwilightEvening().toString());

                mHandler.postDelayed(mTicker, Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "15")) * 60 * 1000);

            }
        };
        mTicker.run();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).updateAstroData();
        this.latitude.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitude.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.sunriseTime.setText("Sunrise time: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getSunrise().toString());
        this.sunriseAzimuth.setText("Sunrise azimuth: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getAzimuthRise()));
        this.sunsetTime.setText("Sunset time: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getSunset().toString());
        this.sunsetAzimuth.setText("Sunset azimuth: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getAzimuthSet()));
        this.civilSunrise.setText("Civil sunrise: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getTwilightMorning().toString());
        this.civilSunset.setText("Civil sunset: " + ((MainActivity)getActivity()).myAstroCalculator.getSunInfo().getTwilightEvening().toString());
        mHandler.post(mTicker);
    }

    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mTicker);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}