package com.fillingapps.ordering.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.model.Table;

public class TableDetailFragment extends Fragment{

    public static TableDetailFragment newInstance(Table currentTable) {
        return new TableDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_table_detail, container, false);

        return root;
    }
}
