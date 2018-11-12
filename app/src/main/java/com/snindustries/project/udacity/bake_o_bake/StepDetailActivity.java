package com.snindustries.project.udacity.bake_o_bake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snindustries.project.udacity.bake_o_bake.ui.stepdetail.StepDetailFragment;

public class StepDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, StepDetailFragment.newInstance())
                    .commitNow();
        }
    }
}
