package com.fillingapps.ordering.model;

import java.util.LinkedList;
import java.util.List;

public class Table {

    private int mTableNumber;
    private int mNumberOfFellows;
    private List<Plate> mPlates;

    public Table(int tableNumber, int numberOfFellows, List<Plate> plates) {
        mTableNumber = tableNumber;
        mNumberOfFellows = numberOfFellows;
        mPlates = plates;
    }

    public Table(int tableNumber) {
        mTableNumber = tableNumber;
        mNumberOfFellows = 0;
        mPlates = new LinkedList<>();
    }

    public int getTableNumber() {
        return mTableNumber;
    }

    public void setTableNumber(int tableNumber) {
        mTableNumber = tableNumber;
    }

    public int getNumberOfFellows() {
        return mNumberOfFellows;
    }

    public void setNumberOfFellows(int numberOfFellows) {
        mNumberOfFellows = numberOfFellows;
    }

    public List<Plate> getPlates() {
        return mPlates;
    }

    public void setPlates(List<Plate> plates) {
        mPlates = plates;
    }

    public void cleanTable(){
        mPlates = new LinkedList<>();
        mNumberOfFellows = 0;
    }

    @Override
    public String toString() {
        return String.valueOf(getTableNumber()) + ": " +String.valueOf(getNumberOfFellows());
    }
}
