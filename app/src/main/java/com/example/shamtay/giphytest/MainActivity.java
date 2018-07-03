package com.example.shamtay.giphytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.shamtay.giphytest.grid.ImagesGridController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.container)
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Router router = Conductor.attachRouter(this, container, savedInstanceState);

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new ImagesGridController()));
        }

    }



}
