package com.example.najihah.weatherapp.comm.Model;

/**
 * Created by Najihah on 11/4/2018. //the coordinate of place
 */

public class Coordinator {
    private double lat;
    private double lon;

    public Coordinator(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    //setter and getter for coordinate
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}