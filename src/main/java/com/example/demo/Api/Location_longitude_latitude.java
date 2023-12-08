package com.example.demo.Api;

import java.text.DecimalFormat;

public class Location_longitude_latitude {
    public String getLocation(String latitude, String longitude) {

        DecimalFormat df = new DecimalFormat("#.##");

        String formattedLatitude = df.format(Double.parseDouble(latitude));

        String formattedLongitude = df.format(Double.parseDouble(longitude));

        String Location=formattedLongitude + "," + formattedLatitude;

        System.out.println(Location);

        return Location;

    }


}
