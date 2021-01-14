package com.example.astroweather_v2;

import java.util.List;

public class WeatherData {
    float lat;
    float lon;
    String timezone;
    int timezone_offset;
    Current current;
    List<Daily> daily;
}
