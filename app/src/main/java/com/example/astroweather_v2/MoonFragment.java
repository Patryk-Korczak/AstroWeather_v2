package com.example.astroweather_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoonFragment extends Fragment {

    TextView latitude2;
    TextView longitude2;
    TextView moonrise;
    TextView moonset;
    TextView newmoon;
    TextView fullmoon;
    TextView moonphase;
    TextView synodicDay;
    //TextView counter;

    int count = 0;

    Handler mHandler = new Handler();
    Runnable mTicker = null;

    public MoonFragment() {
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
        View view = inflater.inflate(R.layout.fragment_moon, container, false);

        this.latitude2 = view.findViewById(R.id.latitude2);
        this.longitude2 = view.findViewById(R.id.longitude2);
        this.moonrise = view.findViewById(R.id.moonrise);
        this.moonset = view.findViewById(R.id.moonset);
        this.newmoon = view.findViewById(R.id.newmoon);
        this.fullmoon = view.findViewById(R.id.fullmoon);
        this.moonphase = view.findViewById(R.id.moonphase);
        this.synodicDay = view.findViewById(R.id.synodicDay);
        //this.counter = view.findViewById(R.id.counter);

        this.latitude2.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitude2.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.moonrise.setText("Moonrise time: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getMoonrise().toString()));
        this.moonset.setText("Moonset time: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getMoonset().toString());
        this.newmoon.setText("Next new moon: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getNextNewMoon().toString());
        this.fullmoon.setText("Next full moon: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getNextFullMoon()));
        this.moonphase.setText("Moon phase: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getIllumination()) + "%");
        this.synodicDay.setText("Synodic day: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getAge());
        //this.counter.setText(String.valueOf(count));

        mTicker = new Runnable() {
            public void run() {
                ((MainActivity)getActivity()).updateAstroData();
               latitude2.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
               longitude2.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
               moonrise.setText("Moonrise time: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getMoonrise().toString()));
               moonset.setText("Moonset time: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getMoonset().toString());
               newmoon.setText("Next new moon: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getNextNewMoon().toString());
               fullmoon.setText("Next full moon: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getNextFullMoon()));
               moonphase.setText("Moon phase: " + Math.floor(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getIllumination() * 100) + "%");
               synodicDay.setText("Synodic day: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getAge());
               //count++;
               //counter.setText(String.valueOf(count));

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
        this.latitude2.setText("Latitude: " + String.valueOf(((MainActivity)getActivity()).latitude));
        this.longitude2.setText("Longitude: " + String.valueOf(((MainActivity)getActivity()).longitude));
        this.moonrise.setText("Moonrise time: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getMoonrise().toString()));
        this.moonset.setText("Moonset time: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getMoonset().toString());
        this.newmoon.setText("Next new moon: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getNextNewMoon().toString());
        this.fullmoon.setText("Next full moon: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getNextFullMoon()));
        this.moonphase.setText("Moon phase: " + String.valueOf(((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getIllumination()) + "%");
        this.synodicDay.setText("Synodic day: " + ((MainActivity)getActivity()).myAstroCalculator.getMoonInfo().getAge());

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