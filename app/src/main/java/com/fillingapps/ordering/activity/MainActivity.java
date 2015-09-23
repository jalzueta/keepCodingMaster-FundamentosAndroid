package com.fillingapps.ordering.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.fragment.TableDetailFragment;
import com.fillingapps.ordering.fragment.TableListFragment;
import com.fillingapps.ordering.model.Plates;
import com.fillingapps.ordering.model.Table;
import com.fillingapps.ordering.model.Tables;
import com.fillingapps.ordering.network.PlatesDownloader;

public class MainActivity extends AppCompatActivity implements PlatesDownloader.OnPlatesReceivedListener, TableListFragment.OnTableSelectedListener {

    static final int RESULT_UPDATED_TABLE = 1;

    private FloatingActionButton mAddPlateButton;
    private Table mSelectedTable;

    private boolean shouldShowFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Inicializamos la mesa seleccionada con la primera mesa disponible
        mSelectedTable = Tables.getInstance(this).getTables().get(0);

        // Obtenemos la referencia al FAB para decirle qué pasa si lo pulsan
        mAddPlateButton = (FloatingActionButton) findViewById(R.id.add_plate_button);
        if (mAddPlateButton != null) {
            mAddPlateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent menuIntent = new Intent(MainActivity.this, MenuActivity.class);
                    menuIntent.putExtra(MenuActivity.EXTRA_TABLE_NUMBER, mSelectedTable.getTableNumber());
                    startActivityForResult(menuIntent, RESULT_UPDATED_TABLE);
                }
            });

            mAddPlateButton.setVisibility(View.GONE);
        }

        //Insertamos el fragment con la lista de mesas
        FragmentManager fm = getFragmentManager();
        if (findViewById(R.id.table_list) != null) {
            if (fm.findFragmentById(R.id.table_list) == null) {
                fm.beginTransaction()
                        .add(R.id.table_list, TableListFragment.newInstance())
                        .commit();
            }
        }

        //Insertamos el fragment con el detalle de la mesa seleccionada (si existe el FrameLayout "table_detail" en el xml de layout)
        if (findViewById(R.id.table_detail) != null) {
            if (fm.findFragmentById(R.id.table_detail) == null) {
                fm.beginTransaction()
                        .add(R.id.table_detail, TableDetailFragment.newInstance(mSelectedTable.getTableNumber()))
                        .commit();
                shouldShowFab = true;
            }
        }

        // Descargamos el menu (asynctask)
        downloadMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_UPDATED_TABLE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // TODO: refresh Tables
                mSelectedTable = Tables.getInstance(this).getTable(data.getIntExtra(MenuActivity.RESULT_TABLE_NUMBER, 0));
                refreshFragments();
            }
        }
    }

    private void refreshFragments() {
        FragmentManager fm = getFragmentManager();
        // La pantalla esta dividida
        if (findViewById(R.id.table_detail) != null) {
            // Hay un fragment en el hueco del tipo TableDetailFragment
            if (fm.findFragmentById(R.id.table_detail) instanceof TableDetailFragment) {
                ((TableDetailFragment)fm.findFragmentById(R.id.table_detail)).updateTable(mSelectedTable);
            }
        }
        else{
            if (fm.getBackStackEntryCount() > 0) {
                if (fm.findFragmentById(R.id.table_list) instanceof TableDetailFragment) {
                    ((TableDetailFragment)fm.findFragmentById(R.id.table_list)).updateTable(mSelectedTable);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleFAB();
    }

    @Override
    public void onPlatesReceivedListener() {
        // Vuelta del AsyncTask al terminar de descargar los platos
        Snackbar.make(
                findViewById(R.id.coordinator),
                R.string.plates_downloaded,
                Snackbar.LENGTH_LONG).show();

        handleFAB();
    }

    @Override
    public void onTableSelected(Table table, int index) {
        mSelectedTable = table;
        if (findViewById(R.id.table_detail) != null) {
            //TODO: update table in tableDetailFragment
            // Se actualiza el fragment de detalle de mesa con los datos de la mesa pulsada
        } else {
            // No existe un hueco "table_detail", por lo que reemplazamos el fragment con la lista
            // mesas por el del detalle de la mesa
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.table_list, TableDetailFragment.newInstance(mSelectedTable.getTableNumber()))
                    .addToBackStack(null)
                    .commit();
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            shouldShowFab = true;
        }
        handleFAB();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            FragmentManager fm = getFragmentManager();

            if (fm.getBackStackEntryCount() == 1 && getSupportActionBar() != null) {
                // Quitamos la flecha de volver
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }

            // Desapilamos el fragment
            fm.popBackStack();

            shouldShowFab = false;
            handleFAB();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();

        if (fm.getBackStackEntryCount() == 1 && getSupportActionBar() != null) {
            // Quitamos la flecha de volver
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        // Comprobamos si hay elementos en la pila pendientes de desapilar
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();

            shouldShowFab = false;
            handleFAB();
        } else {
            super.onBackPressed();
        }
    }

    private void downloadMenu() {
        // Le estamos pasando al AsyncTask el listener en el constructor (this)
        AsyncTask<String, Integer, Plates> platesTask = new PlatesDownloader(this);
        platesTask.execute();
    }

    private void handleFAB() {
        if (shouldShowFab && Plates.getInstance().getPlates().size() > 0) {
            showFAB();
        } else {
            hideFAB();
        }
    }

    private void showFAB() {
        Handler showAddButton = new Handler();
        showAddButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAddPlateButton != null) {
                    mAddPlateButton.show();
                }
            }
        }, 1000);
    }

    private void hideFAB() {
        if (mAddPlateButton != null) {
            mAddPlateButton.hide();
        }
    }
}
