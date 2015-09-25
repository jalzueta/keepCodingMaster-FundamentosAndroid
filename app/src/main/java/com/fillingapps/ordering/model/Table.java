package com.fillingapps.ordering.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Table implements Serializable, Comparable<Table>{

    private int mTableNumber;
    private int mNumberOfFellows;
    private List<Plate> mPlates;
    private String mNotes;

    public Table(int tableNumber, int numberOfFellows, List<Plate> plates, String notes) {
        mTableNumber = tableNumber;
        mNumberOfFellows = numberOfFellows;
        mPlates = plates;
        mNotes = notes;
    }

    public Table(int tableNumber) {
        mTableNumber = tableNumber;
        mNumberOfFellows = 0;
        mPlates = new LinkedList<>();
        mNotes = "";
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
        Collections.sort(mPlates);
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

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public void addPlate (Plate plate){
        plate.setId(mPlates.size()+1);
        mPlates.add(plate);
    }

    public void addPlate (Plate plate, String notes){
        plate.setId(mPlates.size()+1);
        plate.setNotes(notes);
        mPlates.add(plate);
    }

    public void updatePlate (Plate plate){
        for (int i=0; i<mPlates.size(); i++) {
            Plate auxPlate = mPlates.get(i);
            if (auxPlate.isTheSame(plate)){
                mPlates.set(i, plate);
                break;
            }
        }
    }

    public void removePlate (Plate plate){
        for (int i=0; i<mPlates.size(); i++) {
            Plate auxPlate = mPlates.get(i);
            if (auxPlate.isTheSame(plate)){
                mPlates.remove(i);
                break;
            }
        }
    }

    public float getBill(){
        float total = 0;
        for (Plate plate:mPlates) {
            total += plate.getPrice();
        }
        return total;
    }

    public boolean isTheSame (Table table){
        return this.getTableNumber() == table.getTableNumber();
    }

    @Override
    public int compareTo(Table another) {
        return ((Integer)getTableNumber()).compareTo(another.getTableNumber());
    }
}
