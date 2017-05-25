package com.example.haolu.recipemaker;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Home network
    public static final String BASE_URL = "http://192.168.43.145:3000/";
    public static final String TAG = "MainActivity";

    // RecyclerView
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private Retrofit retrofit;
    private ProgressBar mProgessBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Need to implement refrigerator", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mProgessBar = (ProgressBar)findViewById(R.id.main_progress_bar);

        // RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_main);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(15);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(this, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                Recipe recipe = mAdapter.getRecipeAt(position);
                Log.d(TAG, recipe.getName());

                Intent i = new Intent(MainActivity.this, RecipeDetailsActivity.class);
                i.putExtra("RecipeDetails", recipe);
                startActivity(i);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Show the top recipes from the database that are higher rated than 4 stars
        new DisplayTopRecipes().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_search:
                Intent i = new Intent(this, SearchableRecipeActivity.class);
                startActivity(i);
                break;
            case R.id.action_account:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class DisplayTopRecipes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgessBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final RecipeInterface recipeInterface = retrofit.create(RecipeInterface.class);
            Call<RecipeResponse> call = recipeInterface.getSearchResult();

            call.enqueue(new Callback<RecipeResponse>() {
                @Override
                public void onResponse(Call<RecipeResponse> call,
                                       retrofit2.Response<RecipeResponse> response) {
                    if (response.isSuccessful()) {
                        RecipeResponse recipeResponse = response.body();
                        Log.d(TAG, recipeResponse.getRecipes().get(0).getName());
                        Log.d(TAG, "onResponse");
                        Toast.makeText(MainActivity.this, "onResponse", Toast.LENGTH_SHORT).show();
                        displayResults(response.body());
                    }
                }
                @Override
                public void onFailure(Call<RecipeResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure");
                    Toast.makeText(MainActivity.this, "onFailure", Toast.LENGTH_SHORT).show();

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgessBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void displayResults(RecipeResponse recipeResponse) {
        if (mAdapter == null) {
            // Our recipeResponse class with DataBinding
            mAdapter = new RecipeAdapter(recipeResponse.getRecipes());
            mRecyclerView.setAdapter(mAdapter);
        }
        else {

        }
    }
}

