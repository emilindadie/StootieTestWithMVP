package com.example.emili.stootietestwithmvp;

import android.content.Context;

import com.example.emili.stootietestwithmvp.view.MainView;
import com.example.emili.stootietestwithmvp.donnee.Stoot;
import com.example.emili.stootietestwithmvp.presenter.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by emili on 03/09/2017.
 */
public class MainPresenterTest {

    MainPresenter mainPresenter;
    MainView mainView;
    @Mock
    Context context;
    static List<Stoot> stootList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); //créé tous les @Mock
        mainPresenter = new MainPresenter(context, mainView);
        stootList = new ArrayList<Stoot>();
    }

    @Test
    public void loadStootList() throws Exception {
        Stoot stoot = new Stoot(15566, "emilin", "dadie", "14 rue de mulhouse", 45.555, 455.1122, 25, 45, "4 jours", "emilin.jpg");
        assertFalse(stootList.isEmpty());
    }

    @Test
    public void updateStootList() throws Exception {
        loadStootList();
        assertFalse(stootList.isEmpty());
    }
}