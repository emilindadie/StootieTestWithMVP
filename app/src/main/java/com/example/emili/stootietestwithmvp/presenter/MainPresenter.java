package com.example.emili.stootietestwithmvp.presenter;

import android.content.Context;

import com.example.emili.stootietestwithmvp.View.MainView;
import com.example.emili.stootietestwithmvp.donnee.Stoot;
import com.example.emili.stootietestwithmvp.model.RequestStoot;

import java.util.List;

/**
 * Created by emili on 03/09/2017.
 */

public class MainPresenter{

    MainView mainView;
    static List<Stoot> stootList;
    Context context;
    RequestStoot requestStoot;


    public MainPresenter(Context context, MainView mainView){
        this.context = context;
        this.mainView = mainView;
        //stootList = new ArrayList<Stoot>();
        requestStoot = new RequestStoot(context);
    }


    public void loadStootList(String url){
        stootList = requestStoot.extractData(url);}
    public void updateStootList(){
        mainView.updateStootList(stootList);
    }
}