package com.example.haolu.recipemaker;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Recipe extends BaseObservable implements Serializable {

    @Bindable
    public ObservableArrayList<Recipe> recipes;
    private String name;
    private String recipeOwner;
    private String imageUrl;
    private double rating;
    private String[] ingredients = null;
    private String[] steps = null;

    public Recipe() {
        this.recipes = new ObservableArrayList<>();
    }

    public Recipe(String name, String recipeOwner, String[] steps, String[] ingredients,
                  double rating, String image) {
        this.name = name;
        this.recipeOwner = recipeOwner;
        this.steps = steps;
        this.ingredients = ingredients;
        this.rating = rating;
        this.imageUrl = image;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getRecipeOwner() {
        return recipeOwner;
    }

    public String[] getSteps() {
        return steps;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    @Bindable
    public double getRating() {
        return rating;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipeOwner(String recipeOwner) {
        this.recipeOwner = recipeOwner;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }


}
