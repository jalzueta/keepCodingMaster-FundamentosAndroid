package com.fillingapps.ordering.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.adapter.PlatesAdapter;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Table;
import com.fillingapps.ordering.model.Tables;

import java.util.Collections;
import java.util.List;

public class TableDetailFragment extends Fragment{

    private TableDetailBroadcastReceiver mBroadcastReceiver;

    private RecyclerView mPlateList;
    private Table mCurrentTable;

    private TextView mFellowsTextView;
    private TextView mPlatesTextView;
    private TextView mTableNumberTextView;

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

        // TODO: paint values on screen
        mPlateList = (RecyclerView) root.findViewById(R.id.table_recycler);
        mPlateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlateList.setItemAnimator(new DefaultItemAnimator());

        mFellowsTextView = (TextView) root.findViewById(R.id.fellows);
        mPlatesTextView = (TextView) root.findViewById(R.id.plates);
        mTableNumberTextView = (TextView) root.findViewById(R.id.table_number);

        // Suscripcion a la notificacion de cambio en el Singleton "Tables"
        mBroadcastReceiver = new TableDetailBroadcastReceiver();
        // Me suscribo a notificaciones broadcast
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(Tables.TABLE_LIST_CHANGED_ACTION));

        setScreenValues();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Dejo de enterarme de cambios en el modelo Cities
        getActivity().unregisterReceiver(mBroadcastReceiver);
        mBroadcastReceiver = null;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        if (item.getItemId() == R.id.action_delete){
            // TODO: delete plate from table
        }else{
            return false;
        }
        return true;
    }

    public void loadTable(Table table){
        mCurrentTable = table;
        setScreenValues();
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
            mPlateList.swapAdapter(new PlatesAdapter(plates, getActivity(), R.menu.menu_context_table, null), false);
        }
    }

    private class TableDetailBroadcastReceiver extends BroadcastReceiver {

        // Necesito el adapter al que voy a avisar de que hay nuevos datos
        public TableDetailBroadcastReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Hay nuevos cambios, aviso al adaptador para que vuelva a recargarse
            mCurrentTable = Tables.getInstance(getActivity()).getTable(mCurrentTable.getTableNumber());
            setScreenValues();
        }
    }
}