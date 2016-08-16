package com.austin.myapplication;

/**
 * Created by austin on 8/16/16.
 */
public class Ingredient {
    private String ingredientName;
    private String date;

    public Ingredient(String name, String date){
        this.ingredientName = name;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
