package com.example.emili.stootietestwithmvp.presenter;

import android.content.Context;

import com.example.emili.stootietestwithmvp.View.DetailsView;
import com.example.emili.stootietestwithmvp.model.RequestDetailsStoot;

/**
 * Created by emili on 03/09/2017.
 */


public class DetailsPresenter {

    private DetailsView detailsView;
    private RequestDetailsStoot requestDetailsStoot;
    private Context context;
    private String stootUserFirstname = "";
    private String stootUserLastname ="";
    private String stootTitle ="";
    private int stootPrice = 0;
    private String stootAddress = "";
    private String stootDuration ="";
    private int stootBudget = 0;
    private String stootKindOfService  = "";


    public DetailsPresenter(Context context, DetailsView detailsView){
        this.context = context;
        this.detailsView = detailsView;
        requestDetailsStoot = new RequestDetailsStoot(context);

    }

    public void loadStootDetails(String url){
        requestDetailsStoot.extractDetails(url);
        stootUserFirstname = requestDetailsStoot.getFistname();
        stootUserLastname = requestDetailsStoot.getLastname();
        stootTitle = requestDetailsStoot.getTitle();
        stootPrice = requestDetailsStoot.getPriceStoot();
        stootAddress = requestDetailsStoot.getAddress();
        stootDuration = requestDetailsStoot.getDuration();
        stootBudget = requestDetailsStoot.getBudget();
        stootKindOfService = requestDetailsStoot.getKindOfService();
    }


    public void updateDetailsStoot(){
        detailsView.updateFistName(stootUserFirstname);
        detailsView.updateLastName(stootUserLastname);
        detailsView.updateDescription(stootTitle);
        detailsView.updatePrice(stootPrice);
        detailsView.updateAddress(stootAddress);
        detailsView.updateDate(stootDuration);
        detailsView.updateBudget(stootBudget);
        detailsView.updateKindOfservice(stootKindOfService);
    }
}
