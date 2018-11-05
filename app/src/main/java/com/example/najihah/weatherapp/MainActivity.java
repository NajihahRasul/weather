package com.example.najihah.weatherapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.najihah.weatherapp.comm.Comm;
import com.example.najihah.weatherapp.comm.Helper.Helper;
import com.example.najihah.weatherapp.comm.Model.WeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

//declare location listener to get current device position
public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView textCity, currdate, Description, Humidity, Time, Celcius;
    ImageView imageView;

    LocationManager locationManager;
    static double lat, lng;
    String provider;
    int MY_PERMISSION = 0;
    WeatherMap wm = new WeatherMap();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//xml class

        getSupportActionBar().setIcon(R.drawable.weathericon);

        //declare id in xml into java
        textCity = (TextView) findViewById(R.id.textCity);
        currdate = (TextView) findViewById(R.id.currdate);
        imageView = (ImageView) findViewById(R.id.imageView);
        Humidity = (TextView) findViewById(R.id.Humidity);
        //Time = (TextView) findViewById(R.id.Time);
        Description = (TextView) findViewById(R.id.Description);

        Celcius = (TextView) findViewById(R.id.Celcius);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        //add permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{

                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION);
        }

        Location location = locationManager.getLastKnownLocation(provider);
        if (location == null)
            Log.e("TAG","No Location");
    }

    @Override
    protected void onPause() {//create on pause class

        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION);
        }
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() {//create onresume class

        super.onResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION);
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        new GetWeather().execute(Comm.apiRequest(String.valueOf(lat),String.valueOf(lng)));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //extend asynctask
    private class GetWeather extends AsyncTask<String,Void,String> {
        ProgressDialog progress = new ProgressDialog(MainActivity.this);


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setTitle("Please wait for a while...");
            progress.show();

        }


        @Override
        protected String doInBackground(String... params) {

            String stream = null;
            String urlString = params[0];
            Helper http = new Helper();
            stream = http.getHTTPData(urlString);
            return stream;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            if(s.contains("Error! City is not found")){//put dialog if city not found
                progress.dismiss();
                return;
            }

            //add gson library to function gson in app module
            Gson gson = new Gson();
            Type mType = new TypeToken<WeatherMap>(){}.getType();
            wm = gson.fromJson(s,mType);
            progress.dismiss();

            //to get weather info
            textCity.setText(String.format("%s,%s",wm.getName(),wm.getSys().getCountry()));
            currdate.setText(String.format("%s", Comm.getDateNow()));
            Description.setText(String.format("%s",wm.getWeather().get(0).getDescription()));
            Humidity.setText(String.format("%d%%",wm.getMain().getHumidity()));
            //Time.setText(String.format("%s/%s",Comm.unixTimeStampToDateTime(wm.getSys().getSunrise()),Comm.unixTimeStampToDateTime(wm.getSys().getSunset())));
            Celcius.setText(String.format("%.2f °C",wm.getMain().getTemp()));

            //add picasso library in app module
            Picasso.with(MainActivity.this)
                    .load(Comm.getImage(wm.getWeather().get(0).getIcon()))//load weather
                    .into(imageView);

        }

    }
}
