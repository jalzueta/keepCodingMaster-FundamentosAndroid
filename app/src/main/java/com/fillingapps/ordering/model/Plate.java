package com.fillingapps.ordering.model;

import com.fillingapps.ordering.model.Allergen;

import java.util.List;

public class Plate {

    private int mId;
    private String mName;
    private String type;
    private String image;
    private String description;
    private List<Ingredient> mIngredients;
    private List<Allergen> mAllergens;
    private float mPrice;
    private String mNotes;

    public Plate(int id, String name, String type, String image, String description, List<Ingredient> ingredients, List<Allergen> allergens, float price, String notes) {
        mId = id;
        mName = name;
        this.type = type;
        this.image = image;
        this.description = description;
        mIngredients = ingredients;
        mAllergens = allergens;
        mPrice = price;
        mNotes = notes;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public List<Allergen> getAllergens() {
        return mAllergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        mAllergens = allergens;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return mPrice;
    }

    public void setPrice(float price) {
        mPrice = price;
    }

    public String getIngredientsString(){
        String result = "";
        if (mIngredients.size() > 0){
            for (Ingredient ingredient : mIngredients) {
                result = result + ingredient.getName() + ", ";
            }
            return result.substring(0, result.length() - 2);
        }
        return result;
    }
}
