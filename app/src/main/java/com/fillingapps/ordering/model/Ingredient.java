package com.fillingapps.ordering.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String mName;

    public Ingredient(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
