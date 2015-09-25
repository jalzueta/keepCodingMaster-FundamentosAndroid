package com.fillingapps.ordering.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.fragment.TableDetailFragment;
import com.fillingapps.ordering.fragment.TablePagerFragment;
import com.fillingapps.ordering.model.Table;
import com.fillingapps.ordering.model.Tables;

public class TableDetailActivity extends AppCompatActivity implements TablePagerFragment.OnTablePageChangedListener{

    private FloatingActionButton mAddPlateButton;
    private Table mSelectedTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Inicializamos la mesa seleccionada con la mesa recibida en el intent
        mSelectedTable = Tables.getInstance(this).getTables().get(getIntent().getIntExtra(TableDetailFragment.ARG_TABLE_NUMBER, 0));

        // Obtenemos la referencia al FAB para decirle qué pasa si lo pulsan
        mAddPlateButton = (FloatingActionButton) findViewById(R.id.add_plate_button);
        if (mAddPlateButton != null) {
            mAddPlateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent menuIntent = new Intent(TableDetailActivity.this, MenuActivity.class);
                    menuIntent.putExtra(MenuActivity.EXTRA_TABLE_NUMBER, mSelectedTable.getTableNumber());
                    startActivity(menuIntent);
                }
            });
            mAddPlateButton.setVisibility(View.GONE);
        }

        //Insertamos el fragment con el detalle de la mesa seleccionada (si existe el FrameLayout "table_detail" en el xml de layout)
        FragmentManager fm = getFragmentManager();
        if (findViewById(R.id.table_detail) != null) {
            if (fm.findFragmentById(R.id.table_detail) == null) {
                fm.beginTransaction()
                        .add(R.id.table_detail, TablePagerFragment.newInstance(mSelectedTable.getTableNumber()))
                        .commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Voy a hacer aparecer el boton despues de 1 segundos
        Handler showAddButton = new Handler();
        showAddButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAddPlateButton != null){
                    // Muestra el boton con una animacion incluida
                    mAddPlateButton.show();
                }
            }
        }, 1000);
    }

    @Override
    public void onTablePageChanged(Table table, int index) {
        mSelectedTable = table;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
