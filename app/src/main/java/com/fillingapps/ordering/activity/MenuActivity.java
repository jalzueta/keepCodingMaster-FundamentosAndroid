package com.fillingapps.ordering.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.fragment.MenuFragment;

public class MenuActivity extends AppCompatActivity{

    public static final String EXTRA_TABLE_NUMBER = "com.fillingapps.ordering.activity.MenuActivity.EXTRA_TABLE_NUMBER";
    private int mSelectedTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // REcuperamos el numero de mesa seleccionada
        mSelectedTable = getIntent().getIntExtra(EXTRA_TABLE_NUMBER, 0);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Ponemos la flecha de volver porque esta actividad siempre
        // viene de otra
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Insertamos el fragment con la lista de platos
        FragmentManager fm = getFragmentManager();
        if (findViewById(R.id.menu_list) != null) {
            if (fm.findFragmentById(R.id.menu_list) == null) {
                fm.beginTransaction()
                        .add(R.id.menu_list, MenuFragment.newInstance())
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Comprobamos si la opción es la de la flecha "back"
        if (item.getItemId() == android.R.id.home) {
            // Esto me asegura que finaliza la actividad actual y regresa a la que
            // hayamos definido en el AndroidManifest.xml
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Esto me asegura que finaliza la actividad actual y regresa a la que
        // hayamos definido en el AndroidManifest.xml
        NavUtils.navigateUpFromSameTask(this);
    }
}
