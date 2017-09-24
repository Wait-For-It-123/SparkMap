package com.sparkmap.sparkmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

/**
 * Created by Nate on 9/20/2017.
 */

public class Map extends AppCompatActivity implements OnInfoWindowClickListener {
    private GoogleMap mMap;
    private Location mLocation;
    private HashMap<String, String> mMarkerMap;
    private MainActivity mainActivity;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS__FINE_LOCATION = 101;
    public Map(GoogleMap passedMap, Location passedLocation, MainActivity passedActivity){
        mMap = passedMap;
        mLocation = passedLocation;
        mMarkerMap = mLocation.getMarkerMap();
        mMap.setOnInfoWindowClickListener(this);
        mainActivity = passedActivity;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(mainActivity, "This message", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param lat this is a latitude reading that will be passed in
     * @param lng this is a longitude reading that will be passed in
     * @param username this is the unique username of the user to pull
     *
     *                 This method should simply take in a latitude and longitude and add a marker to the map in that spot.
     *                 Then it should save that marker in the markerMap hashmap so that we know it was added (username, (lat,lng)).
     */
    public void addMapMarker(float lat, float lng, String username, String snippet){
        LatLng currentLocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(currentLocation)
                .title(username)
                .snippet(snippet));
        String s = Float.toString(lat);
        s = s+"\n";
        s = s + Float.toString(lng);
        s = s+"\n";
        mMarkerMap.put(username,s);
    }



    public void centerCam(){
        Toast.makeText(mainActivity, "Current Location", Toast.LENGTH_SHORT).show();
        FusedLocationProviderClient mFusedLocationClient = mLocation.getFusedLocationClient();
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS__FINE_LOCATION);
            }
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(mainActivity, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            float lat = (float) (location.getLatitude());
                            float lng = (float) (location.getLongitude());
                            LatLng CurrentLocation = new LatLng(lat, lng);
                            float zoomLevel = (float) 14.0;
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation, zoomLevel));
                        }
                    }
                });

    }
    public void createSpark(){
        Toast.makeText(mainActivity, "Create Spark", Toast.LENGTH_SHORT).show();
        FusedLocationProviderClient mFusedLocationClient = mLocation.getFusedLocationClient();
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS__FINE_LOCATION);
            }
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(mainActivity, new OnSuccessListener<android.location.Location>() {
                    @Override
                    public void onSuccess(android.location.Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            float lat = (float) (location.getLatitude());
                            float lng = (float) (location.getLongitude());
                            addMapMarker(lat,lng,"Test User","Test Snippet!");
                        }
                    }
                });
    }

    public void parseSpark(){

    }

    public void refreshSparks(){

    }

}
