package ir.hamiss.persianaddresspicker.Maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import ir.hamiss.persianaddresspicker.Modul.SearchItem;
import ir.hamiss.persianaddresspicker.R;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;


public class MapPresenter
{

    void address_add(String title, String detail, SearchItem location, MapsActivity activity) {

        if (title!=null){
            location.setTitle(title);
        }
        if (detail!=null){
            location.setAddress(detail);

        }
        Intent data = new Intent();

        data.putExtra(activity.getString(R.string.location_searched), location);


        location.getLocation().setX(activity.mMap.getCameraPosition().target.latitude);
        location.getLocation().setY(activity.mMap.getCameraPosition().target.longitude);

        activity.setResult(RESULT_OK, data);
        activity.finish();

    }

    public void check_inputs(Bundle savedInstanceState,MapsActivity activity) {

        if (savedInstanceState==null){
            Bundle extras = activity.getIntent().getExtras();

            if (extras!=null){

                //region  title
                if (extras.containsKey(activity.getString(R.string.hint_title))){

                    activity.hint_title = extras.getString(activity.getString(R.string.hint_title));

                }
                else {

                    activity.hint_title = activity.getString(R.string.hint_title);
                }

                //endregion

                //region info


                if (extras.containsKey(activity.getString(R.string.enable_info))){

                    activity.enable_info = extras.getBoolean(activity.getString(R.string.enable_info));

                }
                else {

                    activity.enable_info = true;
                }




                if (extras.containsKey(activity.getString(R.string.hint_info))){

                    activity.hint_info = extras.getString(activity.getString(R.string.hint_info));

                }
                else {

                    activity.hint_title = activity.getString(R.string.hint_title);
                }


                if (extras.containsKey(activity.getString(R.string.error_field))){

                    activity.empty_field = extras.getString(activity.getString(R.string.error_field));

                }
                else {

                    activity.empty_field = activity.getString(R.string.error_field);
                }


                //endregion

                //region location


                if (extras.containsKey(activity.getString(R.string.start_lat))){

                    activity.start_latitude = extras.getDouble(activity.getString(R.string.start_lat));


                }
                else {

                    activity.start_latitude = 35.68;
                }
                if (extras.containsKey(activity.getString(R.string.start_lng))){

                    activity.start_longitude = extras.getDouble(activity.getString(R.string.start_lng));

                }
                else {

                    activity.start_longitude =51.38;
                }




                //endregion


                //region zoom


                if (extras.containsKey(activity.getString(R.string.map_zoom))){

                    activity.zoom_map = extras.getInt(activity.getString(R.string.map_zoom));

                }
                else {

                    activity.zoom_map = 17;
                }



                //endregion


                //region api_key


                if (extras.containsKey(activity.getString(R.string.api_key))){

                    activity.neshan_api_key = extras.getString(activity.getString(R.string.api_key));

                }
                else {

                    activity.neshan_api_key = null;
                }




                //endregion



                //region drawable


                if (extras.containsKey(activity.getString(R.string.drawable_int))){

                    activity.drawable_src = extras.getInt(activity.getString(R.string.drawable_int));

                }
                else {

                    activity.drawable_src = R.drawable.location_placeholder;
                }




                //endregion


                //region search alley


                if (extras.containsKey(activity.getString(R.string.search_alley))){

                    activity.search_alley = extras.getString(activity.getString(R.string.search_alley));

                }
                else {

                    activity.search_alley = activity.getString(R.string.search_alley);
                }




                //endregion

                //region dialog top title


                if (extras.containsKey(activity.getString(R.string.address_name))){

                    activity.dialog_string = extras.getString(activity.getString(R.string.address_name));

                }
                else {

                    activity.search_alley = activity.getString(R.string.address_name);
                }




                //endregion


                Log.e("lat,lon",activity.start_latitude+"\t"+activity.start_longitude);

                //region set

                activity.request_btn.setText(activity.search_alley);
                activity.center_img.setImageResource(activity.drawable_src);





                //endregion



            }
        }
    }


    public boolean isGPSEnabled (Context mContext){
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    void request_last(MapsActivity activity){
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria locationCritera = new Criteria();
        locationCritera.setAccuracy(Criteria.ACCURACY_FINE);
        locationCritera.setAltitudeRequired(false);
        locationCritera.setBearingRequired(false);
        locationCritera.setCostAllowed(true);
        locationCritera.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String providerName = locationManager.getBestProvider(locationCritera, true);
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 102);

        } else {


           activity.last_location  = locationManager.getLastKnownLocation(providerName);
           if (activity.last_location!=null)
           activity.moveCamera(activity.last_location.getLongitude(),activity.last_location.getLatitude(),activity.zoom_map);

        }

    }

}
