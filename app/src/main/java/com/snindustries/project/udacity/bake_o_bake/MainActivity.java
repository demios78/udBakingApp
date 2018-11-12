package com.snindustries.project.udacity.bake_o_bake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snindustries.project.udacity.bake_o_bake.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }
}
