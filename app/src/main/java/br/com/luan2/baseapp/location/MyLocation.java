package br.com.luan2.baseapp.location;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Luan on 24/05/17.
 */

public class MyLocation implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    Location lastLocation;
    private Handler handler;
    private Context context;
    private Activity activity;
    public static final int  REQUEST_PERMISSIONS_CODE = 128;
    LocationReady mListener;

    public interface LocationReady{
        void onReady(Location location);
    }


    public MyLocation(Activity activity, LocationReady listener) {
        this.activity = activity;
        this.context = activity.getBaseContext();
        mListener = listener;
        callConnection();
    }

    public Location getLastLocation(){
        return lastLocation;
    }

    private synchronized void callConnection(){
        Log.i("LOG", "LastLocationActivity.callConnection()");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE );

            }else{
                lastLocation = LocationServices
                        .FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            }
        }else{
            lastLocation = LocationServices
                    .FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }

        if(lastLocation != null) {
            LatLng location = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mListener.onReady(lastLocation);
            Log.d("location",location.toString());
            //envia location pra request;


        }else{
            Toast.makeText(context, "Sua localização não foi encontrada. Verifique se seu celular está funcionando corretamente.", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public String getEndereco(Location lastLocation){
        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String resultAddress = "";

        try {
            list = (ArrayList<Address>) geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
            //example retorno Address[addressLines=[0:"Rua Voluntários da Pátria, 250 - Centro",1:"Curitiba - PR",2:"Brasil"],feature=250,admin=Paraná,sub-admin=null,locality=Curitiba,thoroughfare=Rua Voluntários da Pátria,postalCode=80020,countryCode=BR,countryName=Brasil,hasLatitude=true,latitude=-25.4327455,hasLongitude=true,longitude=-49.2742322,phone=null,url=null,extras=null]
            if(list != null && list.size() > 0){
                Address a = list.get(0);
                for(int i = 0, tam = a.getMaxAddressLineIndex(); i < tam; i++){
                    resultAddress += a.getAddressLine(i);
                    resultAddress += i < tam - 1 ? ", " : "";
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            //error = "Network problem";
//            try {
//                RequestFuture<JSONObject> future = RequestFuture.newFuture();
//                String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" +
//                        String.valueOf(lastLocation.getLatitude()) +"," + String.valueOf(lastLocation.getLongitude());
//                JsonObjectRequest request = new  JsonObjectRequest(Request.Method.GET,url,future,future);
//                request.setRetryPolicy(new DefaultRetryPolicy(1000 * 20, 1, 1f));
//                Volley.newRequestQueue(getBaseContext()).add(request);
//                JSONObject response = future.get();
//                if("OK".equalsIgnoreCase(response.getString("status"))){
//                    JSONArray array = response.getJSONArray("results");
//                    resultAddress = array.getJSONObject(0).getString("formatted_address");
//                }else{
//                    error = "parse error";
//                }
//            }catch (Exception ex){
//                e.printStackTrace();
//                error = "Network problem";
//            }
        }
//        catch (IllegalArgumentException e){
//            e.printStackTrace();
//            error = "Illegal arguments";
//        }

        return resultAddress;
    }




}
