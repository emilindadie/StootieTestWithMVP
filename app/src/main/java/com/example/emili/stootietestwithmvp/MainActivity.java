package com.example.emili.stootietestwithmvp;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.emili.stootietestwithmvp.view.MainView;
import com.example.emili.stootietestwithmvp.donnee.Stoot;
import com.example.emili.stootietestwithmvp.donnee.StootAdapter;
import com.example.emili.stootietestwithmvp.presenter.MainPresenter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    StootAdapter stootAdapter;
    private static final String URL_STOOT = "https://bff-mobile-dev.stootie.com/stoots.json?lat=48.8694023&lng=2.3522692&radius=50&stoot_type[]=miss%20ion&page=1&per_page=50";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check permission
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //load stoot
        loadstootList();
    }

    @Override
    public void updateStootList(List<Stoot> stoots) {

        stootAdapter = new StootAdapter(this, stoots, new StootAdapter.RecyclerItemClickListener() {
            @Override
            public void OnClickListener(Stoot stoot, int position) {

                Intent intent = new Intent(MainActivity.this, StootActivityDetails.class);
                intent.putExtra("id", stoot.getId());
                intent.putExtra("longitude", stoot.getLongitude());
                intent.putExtra("latitude", stoot.getLatitude());
                intent.putExtra("address", stoot.getAdresse());
                startActivity(intent);
            }
        });

        if (stoots.size() > 0) {
            Toast.makeText(this, String.valueOf(stoots.size()), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "empty arrayList", Toast.LENGTH_LONG).show();
        }

        recyclerView.setAdapter(stootAdapter);

    }


    //load stootList
    private void loadstootList() {
        MainPresenter mainPresenter = new MainPresenter(MainActivity.this, this);
        mainPresenter.loadStootList(URL_STOOT);
        mainPresenter.updateStootList();
    }

    //Action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                loadstootList();
                Toast.makeText(this, "chargement terminé", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

