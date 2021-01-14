package com.example.astroweather_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.Date;

public class WeatherForecastFragment extends Fragment {
    Handler mHandler = new Handler();
    Runnable mTicker = null;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        TextView textWeatherForecast = view.findViewById(R.id.textWeatherForecast);
        TextView textDay1 = view.findViewById(R.id.textDay1);
        TextView textDay2 = view.findViewById(R.id.textDay2);
        TextView textDay3 = view.findViewById(R.id.textDay3);
        TextView textDay4 = view.findViewById(R.id.textDay4);
        TextView textDay5 = view.findViewById(R.id.textDay5);

        mTicker = new Runnable() {
            public void run() {

                //generate api link
                float latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
                float longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
                ApiConnection myApi = new ApiConnection(latitude, longitude);

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, myApi.finalUrl,
                        response -> {
                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                            textDay1.setText(myWeatherData.daily.get(0).dt);
                        }, error -> Toast.makeText(getActivity(), "An error occured!", Toast.LENGTH_SHORT));

                queue.add(stringRequest);
                mHandler.postDelayed(mTicker, Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "15")) * 60 * 1000);

            }
        };
        mTicker.run();


        return view;
    }
}