package com.fillingapps.ordering.model;

import com.fillingapps.ordering.model.Allergen;

import java.util.List;

public class Plate {

    private int mId;
    private String mName;
    private String type;
    private String image;
    private List<String> mIngredients;
    private List<Allergen> mAllergens;

    public Plate(int id, String name, String type, String image, List<String> ingredients, List<Allergen> allergens) {
        mId = id;
        mName = name;
        this.type = type;
        this.image = image;
        mIngredients = ingredients;
        mAllergens = allergens;
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

    public List<String> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<String> ingredients) {
        mIngredients = ingredients;
    }

    public List<Allergen> getAllergens() {
        return mAllergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        mAllergens = allergens;
    }
}
