package com.fillingapps.ordering.model;

import java.io.Serializable;

public class Allergen implements Serializable {

    private String mName;
    private String mIcon;

    public Allergen(String name, String icon) {
        this.mName = name;
        this.mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    @Override
    public String toString() {
        return getName();
    }
}
