package com.fillingapps.ordering.model;

import java.util.LinkedList;
import java.util.List;

public class Tables {

    private static Tables ourInstance;

    private List<Table> mTables;

    public static Tables getInstance(){
        if (ourInstance == null){
            ourInstance = new Tables();
        }
        return ourInstance;
    }

    public Tables() {
        mTables = new LinkedList<>();
        for (int i=1; i<10; i++){
            Table table = new Table(i);
            mTables.add(table);
        }
    }

    public List<Table> getTables() {
        return mTables;
    }

    public void cleanTable(int tableNumber){
        for (int i=1; i<mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber){
                table.cleanTable();
                mTables.set(i, table);
            }
        }
    }

    public void deleteTable(int tableNumber){
        for (int i=1; i<mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber){
                mTables.remove(i);
            }
        }
    }

    public void setNumberOfFellows (int numberOfFellows, int tableNumber){
        for (int i=1; i<mTables.size(); i++) {
            Table table = mTables.get(i);
            if (table.getTableNumber() == tableNumber){
                table.setNumberOfFellows(numberOfFellows);
            }
        }
    }
}
