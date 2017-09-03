package com.example.emili.stootietestwithmvp.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.emili.stootietestwithmvp.donnee.GpsTracker;
import com.example.emili.stootietestwithmvp.donnee.Stoot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by emili on 03/09/2017.
 */

public class RequestStoot {

    private Context context;

    // cette valeur est defini au moment d'appeller geDistance()
    private float distance = 0;
    private final String LOG_TAG = RequestStoot.class.getSimpleName();

    // cette valeur est defini au moment d'appeller getDurationDate()
    private String duree = "";

    private static List<Stoot> stootList;

    public RequestStoot(Context context){
        this.context = context;
        stootList = new ArrayList<>();
    }

    public List<Stoot> extractData(String url) {

        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        //int socketTimeout = 60000;//30 seconds - change to what you want

        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                handleReponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

        };
        requestQueue.add(stringRequest);

        return stootList;
    }

    private void handleReponse(String response) {
        try {
            JSONObject object = new JSONObject(response);
            JSONArray collection = object.getJSONArray("collection");

            for (int i = 0; i < collection.length(); i++) {

                JSONObject stootie = collection.getJSONObject(i);

                String adresse = stootie.getString("address");
                long id = stootie.getLong("id");

                //double prix = stootie.getDouble("unit_price");
                int prix = (int) stootie.getDouble("unit_price");

                String date = stootie.getString("created_at");

                double latitude = stootie.getDouble("lat");
                double longitude = stootie.getDouble("lng");


                getDurationDate(date);

                getDistance(context, latitude, longitude);

                JSONObject user = stootie.getJSONObject("user");

                String prenom = user.getString("firstname");
                String nom = user.getString("lastname");

                if(user.has("profile_picture_url")) {

                    String urlImagestoot = user.getString("profile_picture_url");
                    Stoot stoot1 = new Stoot(id, prenom, nom, adresse, longitude, latitude, prix, distance, duree, urlImagestoot);
                    stootList.add(stoot1);

                } else {
                    Stoot stoot1 = new Stoot(id, prenom, nom, adresse, longitude, latitude, prix, distance, duree);
                    stootList.add(stoot1);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getDistance(Context context, final double Platitude, final double Plongitude) {
        GpsTracker gt = new GpsTracker(context);
        Location current = gt.getLocation();
        if(current == null){
            Toast.makeText(context,"GPS unable to get Value",Toast.LENGTH_SHORT).show();
        }else {

            Location stoot = new Location("Location stoot");
            stoot.setLatitude(Platitude);
            stoot.setLongitude(Plongitude);

            distance = current.distanceTo(stoot) / 1000;
            //distance = (int) (distance /1000);

            Log.d(LOG_TAG, "distance = "+String.valueOf(distance));
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void getDurationDate(String Pdate){

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        try {
            Date mDate = date.parse(Pdate);

            Date now = new Date(System.currentTimeMillis());
            long heure = getDateDifference(mDate, now, TimeUnit.HOURS);

            if (heure < 24){
                //modifie la variable de classe durée
                duree = "il y a "+String.valueOf(heure) + " h";
            }
            else if (heure >= 24 && heure < 168){

                //modifie la variable de classe durée
                duree = "il y a "+String.valueOf(heure / 24) + " j";
            }
            else{
                //modifie la variable de classe durée
                duree = "il y a "+String.valueOf(heure / 168) + " s";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private long getDateDifference(Date date1, Date date2, TimeUnit timeUnit){

        long difference = date2.getTime() - date1.getTime();

        return timeUnit.convert(difference, TimeUnit.MILLISECONDS);

    }

    public List<Stoot> getAllStootList(){
        return stootList;
    }

}