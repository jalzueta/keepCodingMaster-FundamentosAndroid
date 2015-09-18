package com.fillingapps.ordering.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.TableListFragment;

public class MainActivity extends AppCompatActivity {

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
                        .add(R.id.table_list, TableListFragment.newInstance())
                        .commit();
            }
        }
    }
}
