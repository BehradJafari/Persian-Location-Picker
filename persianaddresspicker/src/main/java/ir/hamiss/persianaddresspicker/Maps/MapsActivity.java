package ir.hamiss.persianaddresspicker.Maps;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.patloew.rxlocation.RxLocation;


import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import ir.hamiss.persianaddresspicker.Modul.AlertDialogAddress;
import ir.hamiss.persianaddresspicker.Modul.SearchItem;
import ir.hamiss.persianaddresspicker.R;
import ir.hamiss.persianaddresspicker.SearchAddress.SearchAddress;


import static android.content.RestrictionsManager.RESULT_ERROR;

public class MapsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {



    public CompositeDisposable bag = new CompositeDisposable();

    private static final int REQUEST_LOCATION = 456;
    Button done_btn;

    FrameLayout mapView;

    ImageView center_img;


    GoogleMap mMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    SearchItem place = new SearchItem();
    Location last_location;


    MarkerOptions markerOptions;
    //    Marker marker;
    LatLng last_lat;

    MapPresenter presenter = new MapPresenter();


    //set_visibility info edit_text
    boolean enable_info;

    //hint for dialog alert
    String hint_info;


    //hint for dialog alert
    String hint_title;

    //neshan api_key
    String neshan_api_key;


    //zoom in map int
    int zoom_map;

    //request_code
    int request_code;


    //drawable src
    int drawable_src;

    //start latitude
    double start_latitude;

    //start longitude
    double start_longitude;
    //error empty field

    String empty_field;


    Button request_btn;
    //search alley text

    String search_alley;



    //address title dialog
     String dialog_string;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        request_btn = findViewById(R.id.request_btn);

        center_img = findViewById(R.id.center_img);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);



        mapFragment.getMapAsync(this);
        turnGPSOn();
        buildGoogleApiClient();

        done_btn = findViewById(R.id.done_btn);
        mapView = findViewById(R.id.map);

        presenter.check_inputs(savedInstanceState, this);


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras() != null)
                    if (data.getExtras().containsKey("item")) {
                        place = (SearchItem) data.getExtras().get("item");

                        moveCamera(place);
                    }
            }
        } else if (resultCode == RESULT_ERROR) {
            // TODO: Handle the error.
        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
        }
    }


    private void moveCamera(SearchItem place) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place.getLocation().getY(), place.getLocation().getX()), zoom_map));



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        moveCamera(start_longitude, start_latitude, zoom_map);

        add_dragable(start_latitude, start_longitude);
        marker_drag();


        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        rx_location();

        mMap.setOnMyLocationButtonClickListener(() -> {
            if (mGoogleApiClient != null) {

                if (presenter.isGPSEnabled(this)){
                    if (last_location!=null)
                    moveCamera(last_location.getLongitude(),last_location.getLatitude(),zoom_map);
                    else
                        presenter.request_last(this);
                }
                else
               turnGPSOn();
                return true;
                  }
            return false;
        });


        mMap.setOnMapClickListener(arg0 -> {


            moveCamera(arg0.longitude, arg0.latitude, 15);
        });

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {


            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);


            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
// position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(180, 180, 180, 180);
        }


//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            locationRequestNew();
//
//        } else {

//            mMap.setMyLocationEnabled(true);


        mGoogleApiClient.connect();

        locationRequestNew();

    }

    private void add_dragable(double teh_lat, double teh_lon) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(drawable_src);


        LatLng loc = new LatLng(teh_lat, teh_lon);

        if (markerOptions == null) {
            mMap.clear();
            markerOptions = new MarkerOptions()
//                    .icon(icon)
                    .position(loc)

                    .icon(icon)

                    .draggable(false);

//            marker = mMap.addMarker(markerOptions);


        }

        mMap.setOnCameraIdleListener(() -> {
            //get latlng at the center by calling
            last_lat = mMap.getCameraPosition().target;

//            marker.setPosition(midLatLng);
        });
    }


//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

    private void locationRequestNew() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            final LocationSettingsStates state = result1.getLocationSettingsStates();
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    // Location settings are not satisfied. But could be fixed by showing the user
                    // a dialog.
//                        try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
//                            status.startResolutionForResult(
//                                    getActivity(), 1000);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    // Location settings are not satisfied. However, we have no way to fix the
                    // settings so we won't show the dialog.
                    break;
            }
        });

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30 * 1000);
        mLocationRequest.setFastestInterval(5 * 1000);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

//        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//
//        } else {
//            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MapsActivity.this);
//
//
//        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {


        last_location = location;


    }




    private void moveCamera(Location location) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom_map));

    }

     void moveCamera(double lat, double lon, float zoom) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lon, lat), zoom));

    }


    private void turnGPSOn() {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);

        LocationSettingsRequest.Builder settingsBuilder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        settingsBuilder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(this)
                .checkLocationSettings(settingsBuilder.build());

        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response =
                        task.getResult(ApiException.class);
//
//                    if (gps.canGetLocation()) {
//                        lat = String.valueOf(gps.getLatitude());
//                        lon = String.valueOf(gps.getLongitude());
//                    }

            } catch (ApiException ex) {
                switch (ex.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException =
                                    (ResolvableApiException) ex;
                            resolvableApiException
                                    .startResolutionForResult(MapsActivity.this,
                                            REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }

    public void search(View view) {
        Intent i = new Intent(this, SearchAddress.class);
        i.putExtra(getString(R.string.api_key),neshan_api_key);
        i.putExtra(getString(R.string.start_lat),start_latitude);
        i.putExtra(getString(R.string.start_lng),start_longitude);
        startActivityForResult(i, 1001);
    }


    private void marker_drag() {
//        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDragStart(Marker arg0) {
//                done_btn.setVisibility(View.INVISIBLE);
//                marker = arg0;
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onMarkerDragEnd(Marker arg0) {
//                moveCamera(arg0.getPosition().longitude, arg0.getPosition().latitude, 15);
//                done_btn.setVisibility(View.VISIBLE);
//
//
//            }
//
//            @Override
//            public void onMarkerDrag(Marker arg0) {
//
//
//            }
//        });
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void set_address(View view) {
        if (last_lat != null) {

            address_detail_dialog(last_lat.latitude, last_lat.longitude);
        }
    }

    private void address_detail_dialog(double lat, double lon) {


        AlertDialogAddress dialogAddress = new AlertDialogAddress(this);
        dialogAddress.show();


        dialogAddress.name.setHint(hint_title);
        dialogAddress.title.setText(dialog_string);



        if (enable_info) {
            dialogAddress.detail.setVisibility(View.VISIBLE);
            dialogAddress.detail.setHint(hint_info);
        } else {
            dialogAddress.detail.setVisibility(View.GONE);

        }



        dialogAddress.yes_btn.setOnClickListener(v -> {

            if (dialogAddress.name_et.getText().toString().trim().isEmpty()


            ) {

                toast(empty_field);
            } else {

                if (enable_info && dialogAddress.detail_et.getText().toString().trim().isEmpty()) {
                    toast(empty_field);

                    return;
                }

                dialogAddress.dismiss();
                presenter.address_add(dialogAddress.name_et.getText().toString(), dialogAddress.detail_et.getText().toString(), place, this);




            }


        });


    }

    private void toast(String empty_field) {
        Toast.makeText(this,empty_field,Toast.LENGTH_SHORT).show();
    }


    private void rx_location() {

        RxLocation rxLocation = new RxLocation(getApplicationContext());
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(6000);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            rxLocation.location().updates(locationRequest)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<Location>() {
                        @Override
                        public void onSubscribe(Disposable d) {


                            bag.add(d);


                        }

                        @Override
                        public void onNext(Location location) {

                            if (location!=null) {

                                last_location = location;
                                moveCamera(location);
                                bag.clear();
                            }




                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("maps with rxLocation",e.toString());
                            bag.clear();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }
    }



    @Override
    protected void onStart() {
        super.onStart();

        startLocationUpdates();

    }
    protected void startLocationUpdates() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleApiClient.isConnected()) {

                PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);

            }
            else {
                mGoogleApiClient.connect();

            }
        }


    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected())
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length > 0)
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {




                    rx_location();
                } else {


                    Log.e("PerResult location ","not granted");
                }
        }

    }


}
