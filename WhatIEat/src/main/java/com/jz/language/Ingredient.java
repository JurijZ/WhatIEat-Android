package com.jz.language;

/**
 * Created by User on 27/06/2017.
 */

public class Ingredient {

    public String IngredientName;
    public String IngredientDescription;
    public Integer IngredientDangerLevel;
    public Integer FuzzyDistance;

    public Ingredient() {

    }

    // Retrieve ingredients Name
    public String getName() {return IngredientName;}
}
