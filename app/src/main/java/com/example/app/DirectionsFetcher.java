package com.example.app;


import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.List;

public class DirectionsFetcher extends AsyncTask<Void, Void, DirectionsResult> {
    private final LatLng origin;
    private final LatLng destination;
    private final GoogleMap map;
    private final String apiKey;

    public DirectionsFetcher(LatLng origin, LatLng destination, GoogleMap map, String apiKey) {
        this.origin = origin;
        this.destination = destination;
        this.map = map;
        this.apiKey = apiKey;
    }

    @Override
    protected DirectionsResult doInBackground(Void... voids) {
        try {
            // Set up the GeoApiContext
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(apiKey)
                    .build();

            // Request directions from the Google Directions API
            DirectionsResult directionsResult = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin(new com.google.maps.model.LatLng(origin.latitude, origin.longitude))
                    .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
                    .await();

            return directionsResult;
        } catch (ApiException | InterruptedException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(DirectionsResult directionsResult) {
        super.onPostExecute(directionsResult);

        if (directionsResult == null) {
            return;
        }

        // Get the first route from the directions result
        DirectionsRoute route = directionsResult.routes[0];

        // Draw the polyline on the map
        List<com.google.maps.model.LatLng> path = route.overviewPolyline.decodePath();
        for (com.google.maps.model.LatLng latLng : path) {
            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(latLng.lat, latLng.lng))
                    .width(10)
                    .color(Color.RED));
        }
    }
}
