package com.fillingapps.ordering;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fillingapps.ordering.model.Table;
import com.fillingapps.ordering.model.Tables;

public class TableListFragment extends Fragment {

    private static String TAG = "Ordering";

    private Tables mTables;
    private int mLongPressItemPosition;
    private ListView mList;

    public static TableListFragment newInstance() {
        return new TableListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_table_list, container, false);

        mTables = Tables.getInstance();
        mList = (ListView) root.findViewById(android.R.id.list);
        final ArrayAdapter<Table> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1, // Recuerda que esto es el layout de cada fila
                mTables.getTables());
        mList.setAdapter(adapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (mListener != null) {
//                    mListener.onCitySelected(adapter.getItem(position), position);
//                }
            }
        });

        registerForContextMenu(mList);

        // TODO: suscripcion a broadcast que avise de cambios en la lista de mesas (Clean)

        return root;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        mLongPressItemPosition = ((Table)mList.getAdapter().getItem(info.position)).getTableNumber();
        menu.setHeaderTitle(String.format(getActivity().getString(R.string.table_number_title), mLongPressItemPosition));
        inflater.inflate(R.menu.menu_context_list_fragment, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clean){
            mTables.cleanTable(mLongPressItemPosition);
        }else if (item.getItemId() == R.id.action_assign_fellows){
            launchAssignFellowDialog();
        }else{
            return false;
        }
        return true;
    }

    private void launchAssignFellowDialog() {
        //TODO: lanzar DialogFragmnet
    }
}
