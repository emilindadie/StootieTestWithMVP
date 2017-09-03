package com.example.emili.stootietestwithmvp.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by emili on 03/09/2017.
 */


public class RequestDetailsStoot {

    private static final String LOG_TAG = RequestDetailsStoot.class.getSimpleName();
    Context context;

    private static String firstName ="";
    private static String lastName = "";
    private static String title ="";
    private static int priceStoot = 0;
    private static String kindOfService = "";
    private static int budget = 0;
    private static String address = "";
    private static String duration = "";
    private static String urlImageStoot= "";


    public RequestDetailsStoot(Context context){
        this.context = context;
    }
    public void extractDetails(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //int socketTimeout = 60000;//30 seconds - change to what you want

        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d("Response: ", response);
                handleReponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(LOG_TAG, "reponse don't work");

            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept-Version", "2.0.0");
                params.put("X-Request-Id", "5f3b4f3c-44ca-4447-89e4-759e00d9b2b2");
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }

    private void handleReponse(String response) {

        try {
            JSONObject stootDetails = new JSONObject(response);

            //get Stoot title
            title = stootDetails.getString("title");

            //get Stoot type of service
            kindOfService = stootDetails.getString("stoot_type");

            //get Stoot prix
            priceStoot = (int) stootDetails.getDouble("unit_price");

            //get Stoot user object
            JSONObject user = stootDetails.getJSONObject("user");

            //get publish date
            String dateDeCreation = stootDetails.getString("created_at");
            getDurationDate(dateDeCreation);

            firstName = user.getString("firstname");
            lastName = user.getString("lastname");


            if(user.has("profile_picture_url") && user.getString("profile_picture_url") != null) {
                urlImageStoot = user.getString("profile_picture_url");
            }

            JSONObject answer_wizard = stootDetails.getJSONObject("answer_wizard");
            JSONObject wizard = answer_wizard.getJSONObject("wizard");
            JSONArray questions = wizard.getJSONArray("questions");

            //get Stoot budget
            JSONObject stootGetBudget = questions.getJSONObject(3);
            JSONObject answerBudget = stootGetBudget.getJSONObject("answer");
            budget = answerBudget.getInt("value");

            //get Stoot adresse
            JSONObject stootGetAdresse = questions.getJSONObject(4);

            JSONObject answerAdresse = stootGetAdresse.getJSONObject("answer");
            JSONObject to = answerAdresse.getJSONObject("to");

            if(!to.isNull("city")){
                address = to.getString("city");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.N)
    public void getDurationDate(String Pdate){

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        try {
            Date mDate = date.parse(Pdate);

            Date now = new Date(System.currentTimeMillis());
            long heure = getDateDifference(mDate, now, TimeUnit.HOURS);

            if (heure < 24){

                duration = "il y a "+String.valueOf(heure) + " h";
                // return jour + "j";
            }
            else if (heure >= 24 && heure < 168){
                duration = "il y a "+String.valueOf(heure / 24) + " j";

                // return  jour / 7 + "s";
            }
            else{
                duration = "il y a "+String.valueOf(heure / 168) + " s";
                // return  jour / 7 + "s";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //return duree;
    }

    private long getDateDifference(Date date1, Date date2, TimeUnit timeUnit){

        long difference = date2.getTime() - date1.getTime();

        return timeUnit.convert(difference, TimeUnit.MILLISECONDS);

    }

    public String getFistname(){
        return firstName;
    }

    public String getLastname(){
        return lastName;
    }


    public String getTitle(){

        return title;
    }

    public int getPriceStoot(){

        return priceStoot;
    }

    public String getKindOfService(){

        return kindOfService;
    }

    public int getBudget(){
        return budget;
    }

    public String getAddress(){

        return address;
    }

    public String getDuration(){

        return duration;
    }


    public String getUrlImageStoot(){
        return urlImageStoot;
    }
}

