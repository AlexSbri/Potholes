package com.example.potholes.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;

public class PosizioneService {
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest ;
    private LocationCallback locationCallback;
    private Location posizione;

    @SuppressLint("MissingPermission")
    public Location getPosizione() {
        return posizione;
    }

    public PosizioneService(Context context){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        instantiateLocationRequest();
        instantiateLocationCallback();
    }

    public void instantiateLocationCallback(){
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                synchronized (PosizioneService.this) {
                    posizione = locationResult.getLastLocation();
                    PosizioneService.this.notifyAll();
                    }
                }
        };
    }

    public void instantiateLocationRequest(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(50);
        locationRequest.setInterval(1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
    }

    @SuppressLint("MissingPermission")
    public void startLocation(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper());
    }

    public void stopLocation(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}