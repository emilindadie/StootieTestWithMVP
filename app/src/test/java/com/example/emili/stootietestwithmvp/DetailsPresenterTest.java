package com.example.emili.stootietestwithmvp;

import android.content.Context;

import com.example.emili.stootietestwithmvp.View.DetailsView;
import com.example.emili.stootietestwithmvp.View.MainView;
import com.example.emili.stootietestwithmvp.presenter.DetailsPresenter;
import com.example.emili.stootietestwithmvp.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

/**
 * Created by emili on 03/09/2017.
 */
public class DetailsPresenterTest {


    DetailsPresenter detailsPresenter;
    DetailsView detailsView;
    @Mock Context context;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); //créé tous les @Mock
        detailsPresenter = new DetailsPresenter(context, detailsView);
    }

    @Test
    public void loadStootDetails() throws Exception {



    }

    @Test
    public void updateDetailsStoot() throws Exception {
        String stootUserFirstname = "emilin";
        String stootUserLastname = "dadie";
        String stootTitle = "Peinture";
        int stootPrice = 50;
        String stootAddress = "14 rue de Mulhouse";
        String stootDuration = " il ya 4 jours";
        int stootBudget = 50;
        String stootKindOfService  = "mission";

        assertFalse(stootUserFirstname.isEmpty());
        assertFalse(stootUserLastname.isEmpty());
        assertFalse(stootTitle.isEmpty());
        assertNotNull(stootPrice);
        assertFalse(stootAddress.isEmpty());
        assertFalse(stootDuration.isEmpty());
        assertNotNull(stootBudget);
        assertFalse(stootKindOfService.isEmpty());
    }

}