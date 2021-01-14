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
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;


public class CurrentWeatherFragment extends Fragment {

    Handler mHandler = new Handler();
    Runnable mTicker = null;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        //bind views
        TextView textCurrentWeather = view.findViewById(R.id.textCurrentWeather);
        TextView textTemperature = view.findViewById(R.id.textTemperature);
        TextView textFeelsLike = view.findViewById(R.id.textFeelsLike);
        TextView textPressure = view.findViewById(R.id.textPressure);
        TextView textClouds = view.findViewById(R.id.textClouds);
        TextView textDescription = view.findViewById(R.id.textDescription);
        TextView textWindSpeed = view.findViewById(R.id.textWindSpeed);



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
                            Gson gson = new Gson();
                            WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                            textTemperature.setText("Temperature: " + String.valueOf(myWeatherData.current.temp) + "C");
                            textFeelsLike.setText("Feels like: " + String.valueOf(myWeatherData.current.feels_like) + "C");
                            textPressure.setText("Pressure: " + String.valueOf(myWeatherData.current.pressure));
                            textClouds.setText("Clouds: " + String.valueOf(myWeatherData.current.clouds) + "%");
                            textDescription.setText("Description: " + String.valueOf(myWeatherData.current.weather.get(0).description));
                            textWindSpeed.setText("Wind speed: " + String.valueOf(myWeatherData.current.wind_speed) + " km/h");
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                            Toast.makeText(getContext(), "No internet connection, showing latest data available.", Toast.LENGTH_SHORT).show();
                            String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");
                            Gson gson = new Gson();
                            WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                            textTemperature.setText("Temperature: " + String.valueOf(myWeatherData.current.temp) + "C");
                            textFeelsLike.setText("Feels like: " + String.valueOf(myWeatherData.current.feels_like) + "C");
                            textPressure.setText("Pressure: " + String.valueOf(myWeatherData.current.pressure));
                            textClouds.setText("Clouds: " + String.valueOf(myWeatherData.current.clouds) + "%");
                            textDescription.setText("Description: " + String.valueOf(myWeatherData.current.weather.get(0).description));
                            textWindSpeed.setText("Wind speed: " + String.valueOf(myWeatherData.current.wind_speed) + " km/h");
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
    public void onResume() {
        super.onResume();
        TextView textCurrentWeather = getView().findViewById(R.id.textCurrentWeather);
        TextView textTemperature = getView().findViewById(R.id.textTemperature);
        TextView textFeelsLike = getView().findViewById(R.id.textFeelsLike);
        TextView textPressure = getView().findViewById(R.id.textPressure);
        TextView textClouds = getView().findViewById(R.id.textClouds);
        TextView textDescription = getView().findViewById(R.id.textDescription);
        TextView textWindSpeed = getView().findViewById(R.id.textWindSpeed);

        //generate api link
        float latitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("latitude", "0"));
        float longitude = Float.parseFloat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString("longitude", "0"));
        ApiConnection myApi = new ApiConnection(latitude, longitude);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, myApi.finalUrl,
                response -> {
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("data", response).apply();
                    Gson gson = new Gson();
                    WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                    textTemperature.setText("Temperature: " + String.valueOf(myWeatherData.current.temp) + "C");
                    textFeelsLike.setText("Feels like: " + String.valueOf(myWeatherData.current.feels_like) + "C");
                    textPressure.setText("Pressure: " + String.valueOf(myWeatherData.current.pressure));
                    textClouds.setText("Clouds: " + String.valueOf(myWeatherData.current.clouds) + "%");
                    textDescription.setText("Description: " + String.valueOf(myWeatherData.current.weather.get(0).description));
                    textWindSpeed.setText("Wind speed: " + String.valueOf(myWeatherData.current.wind_speed) + " km/h");
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError || error instanceof AuthFailureError || error instanceof NoConnectionError || error instanceof TimeoutError) {
                    //Toast.makeText(getContext(), "No internet connection, showing latest data available.", Toast.LENGTH_SHORT).show();

                    String response = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("data", "");
                    Gson gson = new Gson();
                    WeatherData myWeatherData = gson.fromJson(response, WeatherData.class);
                    textTemperature.setText("Temperature: " + String.valueOf(myWeatherData.current.temp) + "C");
                    textFeelsLike.setText("Feels like: " + String.valueOf(myWeatherData.current.feels_like) + "C");
                    textPressure.setText("Pressure: " + String.valueOf(myWeatherData.current.pressure));
                    textClouds.setText("Clouds: " + String.valueOf(myWeatherData.current.clouds) + "%");
                    textDescription.setText("Description: " + String.valueOf(myWeatherData.current.weather.get(0).description));
                    textWindSpeed.setText("Wind speed: " + String.valueOf(myWeatherData.current.wind_speed) + " km/h");
                }
            }
        });

        queue.add(stringRequest);
        mHandler.post(mTicker);
    }

    @Override
    public void onPause(){
        super.onPause();
        mHandler.removeCallbacks(mTicker);
    }



}