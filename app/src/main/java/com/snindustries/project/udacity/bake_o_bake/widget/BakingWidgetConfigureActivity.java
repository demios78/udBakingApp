package com.snindustries.project.udacity.bake_o_bake.widget;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.snindustries.project.udacity.bake_o_bake.R;
import com.snindustries.project.udacity.bake_o_bake.databinding.BakingWidgetConfigureBinding;
import com.snindustries.project.udacity.bake_o_bake.ui.main.MainRecipeHandler;
import com.snindustries.project.udacity.bake_o_bake.utils.BindingHeaderAdapter;
import com.snindustries.project.udacity.bake_o_bake.webservice.Repository;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Ingredient;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * The configuration screen for the {@link BakingWidget BakingWidget} AppWidget.
 */
public class BakingWidgetConfigureActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "com.snindustries.project.udacity.bake_o_bake.widget.BakingWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = BakingWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();
            saveTitlePref(context, mAppWidgetId, widgetText);

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            BakingWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public BakingWidgetConfigureActivity() {
        super();
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        BakingWidgetConfigureBinding binding = DataBindingUtil.setContentView(this, R.layout.baking_widget_configure);
        ViewModel viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        ConfigureAdapter adapter = new ConfigureAdapter();


        binding.setLifecycleOwner(this);
        binding.recycler.setAdapter(adapter);
        binding.setModel(viewModel);


//        setContentView(R.layout.baking_widget_configure);
//        mAppWidgetText = (EditText) findViewById(R.id.appwidget_text);
//        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        mAppWidgetText.setText(loadTitlePref(BakingWidgetConfigureActivity.this, mAppWidgetId));
    }

    private static class ConfigureAdapter extends BindingHeaderAdapter<Recipe, Handler> {

        public ConfigureAdapter() {
            super(new ArrayList<>(), R.layout.recipe_card_item);
        }
    }

    public class Handler extends MainRecipeHandler {

        public Handler() { //Messy,TODO change to interface and use delegation
            super(null, null);
        }

        @Override
        public CharSequence ingredients(List<Ingredient> ingredients) {
            return super.ingredients(ingredients);
        }

        @Override
        public void onClick(View view, Recipe recipe) {
            //Select this recipe
            Toast.makeText(view.getContext(), "Selecting Recipe: " + recipe.name, Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewModel extends AndroidViewModel {
        private final LiveData<List<Recipe>> recipes;
        private final Repository repository;

        public ViewModel(@NonNull Application application) {
            super(application);
            repository = Repository.get();
            recipes = repository.getRecipes();
        }

        public LiveData<List<Recipe>> getRecipes() {
            return recipes;
        }
    }

}

