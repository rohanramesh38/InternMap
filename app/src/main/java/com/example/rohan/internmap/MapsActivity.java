package com.example.rohan.internmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LatLng l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Fetching Your Location...", Toast.LENGTH_SHORT).show();

        // Add a marker in Sydney and move the camera




        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double LATTITUDE = location.getLatitude();
                    double LONGITUDE = location.getLongitude();
                     l =new LatLng(LATTITUDE,LONGITUDE);

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(LATTITUDE, LONGITUDE, 1);
                        String addr = addressList.get(0).getLocality() + "," + addressList.get(0).getAddressLine(0);
                        mMap.addMarker(new MarkerOptions().position(l).title(addr));
                        Log.v("lmgdn", addr);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));

                        // arr.add(0,d);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    double LATTITUDE = location.getLatitude();
                    double LONGITUDE = location.getLongitude();
                    l =new LatLng(LATTITUDE,LONGITUDE);

                    // LatLng latLng = new LatLng(LATTITUDE, LONGITUDE);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {

                        List<Address> addressList = geocoder.getFromLocation(LATTITUDE, LONGITUDE, 1);
                        String addr= addressList.get(0).getLocality() + ","+addressList.get(0).getAddressLine(0);

                        Log.v("lmgdn",addr);
                        mMap.addMarker(new MarkerOptions().position(l).title(addr));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f));
                        // arr.add(0,d);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
            });

        }





    }
}
