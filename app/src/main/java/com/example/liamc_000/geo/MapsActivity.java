package com.example.liamc_000.geo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.maps.android.PolyUtil;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import com.google.android.gms.location.LocationListener;
import com.parse.ParseUser;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import junit.framework.Assert;
import static java.lang.Math.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MapsActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener,GoogleMap.OnMarkerClickListener,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,LocationListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private ArrayList<LatLng> arrayPoints = null;
    private GoogleMap mMap;
    boolean markerClicked;
    PolylineOptions rectOptions;
    Polyline polyline;
    Button clear,save;

    LatLng point;
    ParseGeoPoint geopoints;
    ParseObject map = new ParseObject("maps");
   // LatLng me;
    Location location;
    FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Marker now;
    Location mylocation;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        arrayPoints = new ArrayList<LatLng>();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        clear=(Button) findViewById(R.id.mapclear);
        save=(Button) findViewById(R.id.mapsave);



        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest =new LocationRequest();
        locationRequest.setInterval(7000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMap.clear();
                arrayPoints.clear();
                markerClicked = false;
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (arrayPoints.size() >= 3) {

                    ParseUser currentuser=ParseUser.getCurrentUser();
                    String currentusername= currentuser.getUsername();

                    map.add("User",currentusername);
                    map.saveInBackground();

                    Toast.makeText(getApplicationContext(), "Map Saved", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getApplicationContext(),
                            "At least 3 points needed to save",
                            Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
       enableMyLocation();
        mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        markerClicked = false;

 }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }







    @Override
    public void onMapClick(LatLng point) {

    }




    @Override
    public void onMapLongClick(LatLng point) {
        mMap.addMarker(new MarkerOptions().position(point).title(point.toString()));
        arrayPoints.add(point);

        markerClicked = false;
        geopoints = new ParseGeoPoint(point.latitude, point.longitude);
         map.add("location", geopoints);
    }





    public void countPolygonPoints() {
        if (arrayPoints.size() >= 3) {
            markerClicked = true;
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.addAll(arrayPoints);
            polygonOptions.strokeColor(Color.BLUE);
            polygonOptions.strokeWidth(7);
            polygonOptions.fillColor(0x4F00FFFF);

            Polygon polygon = mMap.addPolygon(polygonOptions);

            for (int i = 0; i < 100; i++) {
                pointInPolygon(point, polygon);
                if (pointInPolygon(point, polygon) == true) {
                    Toast.makeText(this, "point inside", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "point outside", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }



    public boolean pointInPolygon(LatLng point, Polygon polygon) {


        Double longitude= mylocation.getLongitude();
        Double latitude = mylocation.getLatitude();
        point = new LatLng(latitude,longitude);

        int crossings = 0;
        List<LatLng> arrayPoints = polygon.getPoints();
        arrayPoints.remove(arrayPoints.size()-1); //remove the last point that is added automatically by getPoints()

        // for each edge
        for (int i=0; i < arrayPoints.size(); i++) {
            LatLng a = arrayPoints.get(i);
            int j = i + 1;
            //to close the last edge, you have to take the first point of your polygon
            if (j >= arrayPoints.size()) {
                j = 0;
            }
            LatLng b = arrayPoints.get(j);
            if (rayCrossesSegment(point, a, b)) {
                crossings++;

            }
        }
        //Toast.makeText(this,"point outside", Toast.LENGTH_SHORT).show();

        // odd number of crossings?
        return (crossings % 2 == 1);
    }

    public boolean rayCrossesSegment(LatLng point, LatLng a,LatLng b) {



        // Ray Casting algorithm checks, for each segment, if the point is 1) to the left of the segment and 2) not above nor below the segment. If these two conditions are met, it returns true
        Double longitude= mylocation.getLongitude();
        Double latitude = mylocation.getLatitude();
        point = new LatLng(latitude,longitude);



        double px = point.longitude,
                py = point.latitude,
                ax = a.longitude,
                ay = a.latitude,
                bx = b.longitude,
                by = b.latitude;
        if (ay > by) {
            ax = b.longitude;
            ay = b.latitude;
            bx = a.longitude;
            by = a.latitude;
        }
        // alter longitude to cater for 180 degree crossings
        if (px < 0 || ax < 0 || bx < 0) {
            px += 360;
            ax += 360;
            bx += 360;
        }
        // if the point has the same latitude as a or b, increase slightly py
        if (py == ay || py == by) py += 0.00000001;


        // if the point is above, below or to the right of the segment, it returns false
        if ((py > by || py < ay) || (px > Math.max(ax, bx))) {

            //Toast.makeText(this,"point outside", Toast.LENGTH_SHORT).show();
            return false;
        }
        // if the point is not above, below or to the right and is to the left, return true
        else if (px < Math.min(ax, bx)) {
            return true;
        }
        // if the two above conditions are not met, you have to compare the slope of segment [a,b] (the red one here) and segment [a,p] (the blue one here) to see if your point is to the left of segment [a,b] or not
        else {
            double red = (ax != bx) ? ((by - ay) / (bx - ax)) : Double.POSITIVE_INFINITY;
            double blue = (ax != px) ? ((py - ay) / (px - ax)) : Double.POSITIVE_INFINITY;
            return (blue >= red);

        }
    }







    @Override
    public boolean onMarkerClick(Marker marker) {

        System.out.println("Marker lat long=" + marker.getPosition());
        System.out.println("First postion check" + arrayPoints.get(0));
        System.out
                .println("**********All arrayPoints***********" + arrayPoints);
        if (arrayPoints.get(0).equals(marker.getPosition())) {
            System.out.println("********First Point choose************");
            countPolygonPoints();

        }
        return false;
    }















    public void onBackPressed(){ this.finish(); }     //method to kill the class if the back button is pressed


    @Override
    public void onConnected(Bundle bundle) {

        requestLocationUpdates();
    }

    private void requestLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (googleApiClient.isConnected()) {
           requestLocationUpdates();
        }}


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(this,"location changed"+ location.getLatitude()+ "" + location.getLongitude(), Toast.LENGTH_SHORT).show();


        mylocation=location;


        if(now != null) {
            now.remove();

        }
        Double longitude= mylocation.getLongitude();
        Double latitude = mylocation.getLatitude();
        LatLng point = new LatLng(latitude,longitude);

        now=mMap.addMarker(new MarkerOptions().position(point));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }
}