package com.futureinternet.cysmile.lte;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;


public class SpeedGpsService extends Service {


    private LocationManager lm;
    private LocationListener locationListener;
    private Integer data_points = 2; // how many data points to calculate for
    Double[][] positions;

    private LocationListener li;

    private Long[] times;


    public SpeedGpsService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        /*// two arrays for position and time.
        positions = new Double[data_points][2];
        times = new Long[data_points];

        // setting and running location manager
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();


        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getApplicationContext(), "请开启GPS导航...", Toast.LENGTH_SHORT).show();

        }*/

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (gpsEnabled) {
            li = new speed();
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, li);
        }


        return super.onStartCommand(intent, flags, startId);
    }


    private class MyLocationListener implements LocationListener {

        Integer counter = 0;
        Data data = new Data();

        public void onLocationChanged(Location loc) {
            if (loc != null) {

                Double d1;
                Long t1;
                Double speed = 0.0;
                d1 = 0.0;
                t1 = 0l;

                positions[counter][0] = loc.getLatitude();
                positions[counter][1] = loc.getLongitude();
                times[counter] = loc.getTime();

                try {
                    // get the distance and time between the current position, and the previous position.
                    // using (counter - 1) % data_points doesn't wrap properly
                    d1 = distance(positions[counter][0], positions[counter][1], positions[(counter + (data_points - 1)) % data_points][0], positions[(counter + (data_points - 1)) % data_points][1]);
                    t1 = times[counter] - times[(counter + (data_points - 1)) % data_points];
                } catch (NullPointerException e) {
                    //all good, just not enough data yet.
                }

                if (loc.hasSpeed()) {
                    speed = loc.getSpeed() * 1.0; // need to * 1.0 to get into a double for some reason...
                } else {
                    speed = d1 / t1; // m/s
                }

                counter = (counter + 1) % data_points;
                speed = speed * 3.6d;
                data.setGpsspeed(Double.toString(speed));


            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Log.i("GPS", "当前GPS状态为可见状态");
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Log.i("GPS", "当前GPS状态为服务区外状态");
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.i("GPS", "当前GPS状态为暂停服务状态");
                    break;
            }

        }

        @Override
        public void onProviderEnabled(String provider) {
            Location location = lm.getLastKnownLocation(provider);
        }

        @Override
        public void onProviderDisabled(String provider) {

        }


        // private functions
        private double distance(double lat1, double lon1, double lat2, double lon2) {
            // haversine great circle distance approximation, returns meters
            double theta = lon1 - lon2;
            double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60; // 60 nautical miles per degree of seperation
            dist = dist * 1852; // 1852 meters per nautical mile
            return (dist);
        }

        private double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        private double rad2deg(double rad) {
            return (rad * 180.0 / Math.PI);
        }
    }


    public class speed implements LocationListener {
        private Location previousLocation;
        Data data = new Data();


        @Override
        public void onLocationChanged(Location currentLocation) {
            // Float thespeed = loc.getSpeed() * MainActivity.METERS_PER_SECOND_TO_MILES_PER_HOUR;
            double speedMph = 0;

            if (previousLocation == null) {
                previousLocation = currentLocation;
            } else {
                speedMph = LocationUtils.speed(previousLocation, currentLocation);
                previousLocation = currentLocation;
            }

            double latitude = currentLocation.getLatitude();
            double longitude = currentLocation.getLongitude();

            Log.d("latitude", Double.toString(latitude));
            Log.d("longitude", Double.toString(longitude));
            data.setGpsspeed(Double.toString(speedMph));

        }

        @Override
        public void onProviderDisabled(String arg0) {
        }

        @Override
        public void onProviderEnabled(String arg0) {
        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }
    }

    public static class LocationUtils {
        public final static float METERS_PER_SECOND_TO_MILES_PER_HOUR = (float) 2.23694;

        public static double distance(Location one, Location two) {
            int R = 6371000;
            Double dLat = toRad(two.getLatitude() - one.getLatitude());
            Double dLon = toRad(two.getLongitude() - one.getLongitude());
            Double lat1 = toRad(one.getLatitude());
            Double lat2 = toRad(two.getLatitude());
            Double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            Double d = R * c;

            return d;
        }

        public static double speed(Location one, Location two) {
            double distance = LocationUtils.distance(one, two);
            Long diffns = two.getElapsedRealtimeNanos() - one.getElapsedRealtimeNanos();
            double seconds = diffns.doubleValue() / Math.pow(10, 9);

            double speedMph = distance / seconds;
            speedMph = speedMph * LocationUtils.METERS_PER_SECOND_TO_MILES_PER_HOUR;

            return speedMph;
        }

        private static double toRad(Double d) {
            return d * Math.PI / 180;
        }
    }

}
