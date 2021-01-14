package com.example.astroweather_v2;

import android.content.Context;

import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ApiConnection {

    //https://api.openweathermap.org/data/2.5/onecall?lat=50&lon=50&exclude=minutely,hourly,alerts&appid=eca4111eacf18a557ba3c3bf66439030
    String apiKey = "eca4111eacf18a557ba3c3bf66439030";
    String urlBase = "https://api.openweathermap.org/data/2.5/onecall?";
    float longitude;
    float latitude;
    String finalUrl;


    ApiConnection(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.finalUrl = urlBase + "lat=" + latitude + "&lon=" + longitude + "&exclude=minutely,hourly,alerts&appid=" + apiKey + "&units=metric";
    }

}
