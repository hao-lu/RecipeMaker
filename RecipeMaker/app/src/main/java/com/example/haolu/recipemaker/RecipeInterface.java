package com.example.haolu.recipemaker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeInterface { 
    @GET("recipes")
    Call<RecipeResponse> getSearchResult();

    @GET("recipes/Search?")
    Call<RecipeResponse> getSearchResult(@Query("term") String term);

}
