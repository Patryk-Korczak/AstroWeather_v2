package com.example.astroweather_v2;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
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
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();

                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                            date.setTime((long)myWeatherData.daily.get(0).dt*1000);
                            textDay1.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(0).temp.day) + " C" +", " + myWeatherData.daily.get(0).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(1).dt*1000);
                            textDay2.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(1).temp.day) + " C" +", " + myWeatherData.daily.get(1).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(2).dt*1000);
                            textDay3.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(2).temp.day) + " C" +", " + myWeatherData.daily.get(2).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(3).dt*1000);
                            textDay4.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(3).temp.day) + " C" +", " + myWeatherData.daily.get(3).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(4).dt*1000);
                            textDay5.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(4).temp.day) + " C" +", " + myWeatherData.daily.get(4).weather.get(0).description);
                        }, new Response.ErrorListener() {
                         @Override
                            public void onErrorResponse(VolleyError error) {
                             if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                                 Toast.makeText(getContext(), "No internet connection, showing latest data available.", Toast.LENGTH_SHORT).show();
                                 String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");

                                 Date date = new Date();
                                 Gson gson = new Gson();
                                 WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                                 date.setTime((long)myWeatherData.daily.get(0).dt*1000);
                                 textDay1.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(0).temp.day) + " C" +", " + myWeatherData.daily.get(0).weather.get(0).description);
                                 date.setTime((long)myWeatherData.daily.get(1).dt*1000);
                                 textDay2.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(1).temp.day) + " C" +", " + myWeatherData.daily.get(1).weather.get(0).description);
                                 date.setTime((long)myWeatherData.daily.get(2).dt*1000);
                                 textDay3.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(2).temp.day) + " C" +", " + myWeatherData.daily.get(2).weather.get(0).description);
                                 date.setTime((long)myWeatherData.daily.get(3).dt*1000);
                                 textDay4.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(3).temp.day) + " C" +", " + myWeatherData.daily.get(3).weather.get(0).description);
                                 date.setTime((long)myWeatherData.daily.get(4).dt*1000);
                                 textDay5.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(4).temp.day) + " C" +", " + myWeatherData.daily.get(4).weather.get(0).description);
                             }
                         }
                     });

                queue.add(stringRequest);
                mHandler.postDelayed(mTicker, Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("refresh", "15")) * 60 * 1000);

            }
        };
        mTicker.run();


        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mTicker);
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView textWeatherForecast = getView().findViewById(R.id.textWeatherForecast);
        TextView textDay1 = getView().findViewById(R.id.textDay1);
        TextView textDay2 = getView().findViewById(R.id.textDay2);
        TextView textDay3 = getView().findViewById(R.id.textDay3);
        TextView textDay4 = getView().findViewById(R.id.textDay4);
        TextView textDay5 = getView().findViewById(R.id.textDay5);

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
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();

                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                            date.setTime((long)myWeatherData.daily.get(0).dt*1000);
                            textDay1.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(0).temp.day) + " C" +", " + myWeatherData.daily.get(0).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(1).dt*1000);
                            textDay2.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(1).temp.day) + " C" +", " + myWeatherData.daily.get(1).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(2).dt*1000);
                            textDay3.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(2).temp.day) + " C" +", " + myWeatherData.daily.get(2).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(3).dt*1000);
                            textDay4.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(3).temp.day) + " C" +", " + myWeatherData.daily.get(3).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(4).dt*1000);
                            textDay5.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(4).temp.day) + " C" +", " + myWeatherData.daily.get(4).weather.get(0).description);
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                            //Toast.makeText(getContext(), "No internet connection, showing latest data available.", Toast.LENGTH_SHORT).show();
                            String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");

                            Date date = new Date();
                            Gson gson = new Gson();
                            WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                            date.setTime((long)myWeatherData.daily.get(0).dt*1000);
                            textDay1.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(0).temp.day) + " C" +", " + myWeatherData.daily.get(0).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(1).dt*1000);
                            textDay2.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(1).temp.day) + " C" +", " + myWeatherData.daily.get(1).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(2).dt*1000);
                            textDay3.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(2).temp.day) + " C" +", " + myWeatherData.daily.get(2).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(3).dt*1000);
                            textDay4.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(3).temp.day) + " C" +", " + myWeatherData.daily.get(3).weather.get(0).description);
                            date.setTime((long)myWeatherData.daily.get(4).dt*1000);
                            textDay5.setText(date.toString().substring(0,10) + ": " + String.valueOf(myWeatherData.daily.get(4).temp.day) + " C" +", " + myWeatherData.daily.get(4).weather.get(0).description);
                        }
                    }
                });

                queue.add(stringRequest);
                mHandler.post(mTicker);

            }
        };
    }


}