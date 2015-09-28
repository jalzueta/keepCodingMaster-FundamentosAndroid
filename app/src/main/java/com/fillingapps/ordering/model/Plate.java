package com.fillingapps.ordering.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Plate implements Serializable, Comparable<Plate>{

    private int mId;
    private int mMenuId;
    private String mName;
    private String mType;
    private String mImage;
    private String mImageUrl;
    private String mDescription;
    private List<Ingredient> mIngredients;
    private List<Allergen> mAllergens;
    private float mPrice;
    private String mNotes;

    public Plate(int id, int menuId, String name, String type, String image, String imageUrl, String description, List<Ingredient> ingredients, List<Allergen> allergens, float price, String notes) {
        mId = id;
        mMenuId = menuId;
        mName = name;
        this.mType = type;
        this.mImage = image;
        this.mImageUrl = imageUrl;
        this.mDescription = description;
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

    public int getMenuId() {
        return mMenuId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getImage() {
        return mImage;
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
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
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

    public String getAllergensString(){
        String result = "";
        if (mAllergens.size() > 0){
            for (Allergen allergen : mAllergens) {
                result = result + allergen.getName() + ", ";
            }
            return result.substring(0, result.length() - 2);
        }
        return result;
    }

    @Override
    public int compareTo(Plate another) {
        return ((Integer)getMenuId()).compareTo(another.getMenuId());
    }


    public boolean isTheSame (Plate plate){
        return (this.getMenuId() == plate.getMenuId());
    }

}
