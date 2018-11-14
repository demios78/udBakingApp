package com.snindustries.project.udacity.bake_o_bake.ui.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.snindustries.project.udacity.bake_o_bake.RecipeStepsActivity;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Ingredient;
import com.snindustries.project.udacity.bake_o_bake.webservice.model.Recipe;

import java.util.Iterator;
import java.util.List;

/**
 * @author Shaaz Noormohammad
 * (c) 11/13/18
 */
public class MainRecipeHandler {

    private Fragment context;
    private MainFragment.ViewModel viewModel;

    public MainRecipeHandler(Fragment context, MainFragment.ViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public CharSequence ingredients(List<Ingredient> ingredients) {

        if (ingredients.size() == 0) {
            return "";
        }

        String delimiter = ", ";
        Iterator<Ingredient> it = ingredients.iterator();
        final StringBuilder sb = new StringBuilder();
        sb.append(it.next().ingredient);
        while (it.hasNext()) {
            sb.append(delimiter);
            sb.append(it.next().ingredient);
        }
        return sb.toString();
    }

    public void onClick(View view, Recipe recipe) {
        viewModel.setCurrentRecipe(recipe.id);

        Intent intent = new Intent(context.getActivity(), RecipeStepsActivity.class);
        context.getActivity().startActivity(intent);
    }

}
