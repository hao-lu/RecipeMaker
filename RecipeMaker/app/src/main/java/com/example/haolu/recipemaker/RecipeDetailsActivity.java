package com.example.haolu.recipemaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class RecipeDetailsActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    AppBarLayout mAppBarLayout;
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the back button on the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the data from the MainAcitivity
        Intent intent = getIntent();
        mRecipe = (Recipe)intent.getSerializableExtra("RecipeDetails");


        // Change the toolbar title to the name of the the mRecipe
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(mRecipe.getName());

        // Listener for scrolling
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(this);

        // Change the image to the mRecipe image
        ImageView imageView = (ImageView) findViewById(R.id.appbar_image);
        Picasso.with(this).load(mRecipe.getImageUrl()).into(imageView);

        // Set the text for ingredients
        TextView ingredientsText = (TextView) findViewById(R.id.ingredients_text);
        for (String i : mRecipe.getIngredients()) {
            ingredientsText.append(i + "\n");
        }

        // Set the steps
        TextView stepsText = (TextView) findViewById(R.id.steps_text);
        for (int i = 1; i < mRecipe.getSteps().length; i++) {
            stepsText.append(i + ". " + mRecipe.getSteps()[i] + "\n");
            if (i != mRecipe.getSteps().length - 1) {
                stepsText.append("\n");
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        if (verticalOffset == 0)
            collapsingToolbarLayout.setTitle(mRecipe.getName());
        else
            collapsingToolbarLayout.setTitle(mRecipe.getName() + " by "+ mRecipe.getRecipeOwner());
    }
}
