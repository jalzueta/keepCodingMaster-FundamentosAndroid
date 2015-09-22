package com.fillingapps.ordering.model;

import java.util.LinkedList;
import java.util.List;

public class Plates {

    private static Plates ourInstance;

    private List<Plate> mPlates;

    public static Plates getInstance(){
        if (ourInstance == null) {
            ourInstance = new Plates();
        }
        return ourInstance;
    }

    public Plates() {
        mPlates = new LinkedList<>();
    }

    public List<Plate> getPlates() {
        return mPlates;
    }

    public void setPlates(List<Plate> plates) {
        mPlates = plates;
    }

    public void clearPlates(){
        mPlates = new LinkedList<>();
    }

    public void addPlate (Plate plate){
        mPlates.add(plate);
    }
}
