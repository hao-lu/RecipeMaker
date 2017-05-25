package com.example.haolu.recipemaker;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecipeResponse {
    @SerializedName("results")
    private List<Recipe> recipes;

    public RecipeResponse() {
        recipes = new ArrayList<Recipe>();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipes = recipe;
    }
}
