package com.snindustries.project.udacity.bake_o_bake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snindustries.project.udacity.bake_o_bake.ui.recpiesteps.RecipeStepsFragment;

public class RecipeStepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recpie_steps_activity);
        setTitle("Steps");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecipeStepsFragment.newInstance())
                    .commitNow();
        }
        setSupportActionBar( findViewById(R.id.toolbar));
    }
}
