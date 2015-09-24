package com.fillingapps.ordering.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.adapter.PlatesAdapter;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Table;
import com.fillingapps.ordering.model.Tables;

import java.util.Collections;
import java.util.List;

public class TableDetailFragment extends Fragment implements SetFellowsDialogFragment.OnFellowsSetListener, PlatesAdapter.OnPlateAdapterPressedListener{

    private RecyclerView mPlateList;
    private Table mCurrentTable;

    private Plate mPlatePressed;

    private TextView mFellowsTextView;
    private TextView mPlatesTextView;
    private TextView mTableNumberTextView;
    private TextView mNoPlatesTextView;

    private static final String ARG_TABLE_NUMBER = "tableNumber";

    public static TableDetailFragment newInstance(int currentTableNumber) {
        // 1) Nos creamos el fragment
        TableDetailFragment fragment = new TableDetailFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_TABLE_NUMBER, currentTableNumber);

        // 3) Asignamos los argumentos al fragment y lo devolvemos ya creado
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Vamos a recoger el argumento que nos pasan al fragment
        if (getArguments() != null) {
            mCurrentTable = Tables.getInstance(getActivity()).getTable(getArguments().getInt(ARG_TABLE_NUMBER));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_table_detail, container, false);

        if (root.findViewById(R.id.table_recycler_list) != null) {
            mPlateList = (RecyclerView) root.findViewById(R.id.table_recycler_list);
            mPlateList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mPlateList.setItemAnimator(new DefaultItemAnimator());
        }
        else if (root.findViewById(R.id.table_recycler_grid_2) != null){
            mPlateList = (RecyclerView) root.findViewById(R.id.table_recycler_grid_2);
            mPlateList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
            mPlateList.setItemAnimator(new DefaultItemAnimator());
        }

        mFellowsTextView = (TextView) root.findViewById(R.id.fellows);
        mPlatesTextView = (TextView) root.findViewById(R.id.plates);
        mTableNumberTextView = (TextView) root.findViewById(R.id.table_number);
        mNoPlatesTextView = (TextView) root.findViewById(R.id.no_plates);

        setScreenValues();

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_table_detail_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_assign_fellows) {
            launchAssignFellowDialog();
            return true;
        }
        return false;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        if (item.getItemId() == R.id.action_delete){
            // TODO: delete plate from table
            Tables.getInstance(getActivity()).removePlate(mPlatePressed, mCurrentTable.getTableNumber());
        }else{
            return false;
        }
        return true;
    }

    private void launchAssignFellowDialog() {
        showSetFellowsDialog();
    }

    // Set fellows management
    protected void showSetFellowsDialog() {
        SetFellowsDialogFragment dialog = new SetFellowsDialogFragment();
        Bundle attrs = new Bundle();
        attrs.putInt(TableListFragment.TABLE_NUMBER, mCurrentTable.getNumberOfFellows());
        dialog.setArguments(attrs);
        dialog.setOnFellowsSetListener(this);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    public void onFellowsSetListener(SetFellowsDialogFragment dialog, String numberOfFellows) {
        Tables.getInstance(getActivity()).setNumberOfFellows(Integer.parseInt(numberOfFellows), mCurrentTable.getTableNumber());
        dialog.dismiss();
    }

    @Override
    public void onFellowsCancelListener(SetFellowsDialogFragment dialog) {
        dialog.dismiss();
    }

    private void setScreenValues() {
        mTableNumberTextView.setText(String.valueOf(mCurrentTable.getTableNumber()));
        mFellowsTextView.setText(String.valueOf(mCurrentTable.getNumberOfFellows()));
        mPlatesTextView.setText(String.valueOf(mCurrentTable.getPlates().size()));
        setPlates();
    }

    private void setPlates(){
        List<Plate> plates = mCurrentTable.getPlates();
        if (mCurrentTable.getPlates().size() > 0) {
            Collections.sort(plates);
            mPlateList.swapAdapter(new PlatesAdapter(plates, getActivity(), R.menu.menu_context_table, this), false);
            showPlates();
        }
        else{
            hidePlates();
        }
    }

    private void showPlates() {
        mNoPlatesTextView.setVisibility(View.GONE);
        mPlateList.setVisibility(View.VISIBLE);
    }

    private void hidePlates() {
        mNoPlatesTextView.setVisibility(View.VISIBLE);
        mPlateList.setVisibility(View.GONE);
    }

    @Override
    public void onPlateAdapterLongPressed(Plate plate) {
        mPlatePressed = plate;
    }
}