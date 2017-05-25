package com.example.haolu.recipemaker;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchableRecipeActivity extends AppCompatActivity {
    private static final String TAG = "SearchableRecipeAct";
    public static final String BASE_URL = "http://192.168.29.128:3000/";

    private Retrofit retrofit;
    private ProgressBar mProgressBar;

    // RecycleView
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_searchable_recipe);

        // Open search view with keyboard for user
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Query: " + query);
                new SearchRecipesByQuery().execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ImageView backArrowImage = (ImageView) findViewById(R.id.back_button);
        backArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recipe_list_recycler_view);
        // Improves performance if you know that the contents doesn't change the layout size
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(this, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Recipe recipe = mAdapter.getRecipeAt(position);
                Log.d(TAG, recipe.getName());
                Intent i = new Intent(SearchableRecipeActivity.this, RecipeDetailsActivity.class);
                i.putExtra("RecipeDetails", recipe);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    // AsyncTask allows task to be performed in the background and no in the UIThread, which can
    // cause the whole app to crash if the task takes too much time. The asynchronous task is good
    // for short task and the results are publish on the UIThread.
    private class SearchRecipesByQuery extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(SearchableRecipeActivity.this, "onPreExecute", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            // Create retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RecipeInterface recipeInterface = retrofit.create(RecipeInterface.class);
            Log.d(TAG, "STRING " + strings[0]);
            Call<RecipeResponse> call = recipeInterface.getSearchResult(strings[0]);

            call.enqueue(new Callback<RecipeResponse>() {
                @Override
                public void onResponse(Call<RecipeResponse> call, retrofit2.Response<RecipeResponse> response) {
                    if (response.isSuccessful()) {
                        RecipeResponse recipeResponse = response.body();
                        Toast.makeText(SearchableRecipeActivity.this, "onResponse", Toast.LENGTH_SHORT).show();
                        displayResults(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    Log.d(TAG, "FAILED");
                    Toast.makeText(SearchableRecipeActivity.this, "onFailure", Toast.LENGTH_SHORT).show();

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void displayResults(RecipeResponse recipeResponse) {
        if (mAdapter == null) {
            // Our recipeResponse class
            mAdapter = new RecipeAdapter(recipeResponse.getRecipes());
            mRecyclerView.setAdapter(mAdapter);
        }
        else {
            // If searched already, then clear the list
            mRecyclerView.removeAllViews();
            mAdapter = new RecipeAdapter(recipeResponse.getRecipes());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
