package com.example.emili.stootietestwithmvp.view;

/**
 * Created by emili on 03/09/2017.
 */


public interface DetailsView{
    void updateFistName(String firstName);
    void updateLastName(String lastName);
    void updateDescription(String description);
    void updatePrice(int price);
    void updateAddress(String address);
    void updateDate(String date);
    void updateKindOfservice(String kindOfService);
    void updateBudget(int bugdet);
}