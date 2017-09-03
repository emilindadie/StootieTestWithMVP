package com.example.emili.stootietestwithmvp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emili.stootietestwithmvp.View.DetailsView;
import com.example.emili.stootietestwithmvp.presenter.DetailsPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StootActivityDetails extends AppCompatActivity implements DetailsView, OnMapReadyCallback {

    private static final String LOG_TAG = StootActivityDetails.class.getSimpleName();
    ImageView profilImage;
    TextView stootUserFirstname, stootUserLastname, stootTitle, stootAddress, stootPrice, stootDurationDate, stootBugget, stootKingofService;
    String url_stoot_detail = "https://bff-mobile-dev.stootie.com/stoot/mission/";
    private double longitude;
    private double latitude;
    String addressByIntent;
    String addressByRequest = "";
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoot_details);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);

        //get Longitude and latitude from the previous activity
        latitude = intent.getDoubleExtra("latitude", 0);
        longitude = intent.getDoubleExtra("longitude", 0);

        //get adresse from previous activity
        addressByIntent = intent.getStringExtra("address");

        profilImage = (ImageView) findViewById(R.id.detailPhotoUser);
        stootUserFirstname = (TextView) findViewById(R.id.detailPrenom);
        stootUserLastname = (TextView) findViewById(R.id.detailNom);
        stootTitle = (TextView) findViewById(R.id.detailTitre);
        stootAddress = (TextView) findViewById(R.id.adresseDetail);
        stootPrice = (TextView) findViewById(R.id.detailPrix);
        stootDurationDate = (TextView) findViewById(R.id.detailDate);
        stootBugget = (TextView) findViewById(R.id.detailBudgetEnvigase);
        stootKingofService = (TextView) findViewById(R.id.detailTypeService);


        if (id == 0) {
            Toast.makeText(this, "extra is null", Toast.LENGTH_LONG).show();
        }

        loadStootDetails();
    }


    private void loadStootDetails() {
        DetailsPresenter detailsPresenter = new DetailsPresenter(StootActivityDetails.this, this);
        detailsPresenter.loadStootDetails(url_stoot_detail + String.valueOf(id));
        detailsPresenter.updateDetailsStoot();
    }

    @Override
    public void updateFistName(String firstName) {

        stootUserFirstname.setText(firstName);
    }

    @Override
    public void updateLastName(String lastName) {

        stootUserLastname.setText(lastName);
    }

    @Override
    public void updateDescription(String description) {
        stootTitle.setText(description);
    }

    @Override
    public void updatePrice(int price) {
        stootPrice.setText(String.valueOf(price) +" euro");
    }

    @Override
    public void updateAddress(String address) {
        stootAddress.setText(address);
        addressByRequest = address;
    }

    @Override
    public void updateDate(String date) {
        stootDurationDate.setText(date);
    }

    @Override
    public void updateKindOfservice(String kindOfService) {
        stootKingofService.setText(kindOfService);
    }

    @Override
    public void updateBudget(int bugdet) {
        stootBugget.setText(String.valueOf(bugdet) + " euro");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // and move the map's camera to the same location.
        LatLng stoot = new LatLng(latitude, longitude);

        if (addressByRequest.equals("")) {
            googleMap.addMarker(new MarkerOptions().position(stoot)
                    .title(addressByIntent));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stoot, 15));

        } else {
            googleMap.addMarker(new MarkerOptions().position(stoot)
                    .title(addressByRequest));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stoot, 15));
        }

    }
}

