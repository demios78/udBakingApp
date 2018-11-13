package com.snindustries.project.udacity.bake_o_bake;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.snindustries.project.udacity.bake_o_bake.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {


    private NetworkIdlingResource idlingResource;

    @VisibleForTesting
    @NonNull
    public NetworkIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new NetworkIdlingResource();
        }
        return idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            MainFragment fragment;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment = MainFragment.newInstance())
                    .commitNow();
            fragment.setIdlingResource(getIdlingResource());
        }
    }


}
