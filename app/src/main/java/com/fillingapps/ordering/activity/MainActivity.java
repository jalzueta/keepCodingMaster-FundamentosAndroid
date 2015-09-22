package com.fillingapps.ordering.activity;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.fragment.MenuFragment;
import com.fillingapps.ordering.fragment.TableListFragment;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Plates;
import com.fillingapps.ordering.network.PlatesDownloader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PlatesDownloader.OnPlatesReceivedListener{

    public static final String PREF_PLATES = "com.fillingapps.ordering.activity.PREF_PLATES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Asignamos la Toolbar como "ActionBar"
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();
        if (findViewById(R.id.table_list) != null) {
            if (fm.findFragmentById(R.id.table_list) == null) {
                fm.beginTransaction()
                        .add(R.id.table_list, MenuFragment.newInstance())
                        .commit();
            }
        }
        //downloadMenu();
    }

    private void downloadMenu() {
        AsyncTask<String, Integer, Plates> platesTask = new PlatesDownloader(this);
        platesTask.execute();
    }

    @Override
    public void onPlatesReceivedListener() {

    }
}
