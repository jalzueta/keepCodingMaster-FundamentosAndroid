package com.fillingapps.ordering.fragment;

import android.animation.Animator;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.model.Table;
import com.fillingapps.ordering.model.Tables;

public class TableListFragment extends Fragment  implements SetFellowsDialogFragment.OnFellowsSetListener{

    private static String TAG = "Ordering";
    public static String TABLE_NUMBER = "com.fillingapp.ordering.fragment.TableListFragment.TABLE_NUMBER";

    private TableBroadcastReceiver mBroadcastReceiver;

    private Tables mTables;
    private int mLongPressTableNumber;
    private ListView mList;

    public static TableListFragment newInstance() {
        return new TableListFragment();
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
        View root = inflater.inflate(R.layout.fragment_table_list, container, false);

        mTables = Tables.getInstance(getActivity());
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

        mBroadcastReceiver = new TableBroadcastReceiver(adapter);
        // Me suscribo a notificaciones broadcast
        getActivity().registerReceiver(mBroadcastReceiver, new IntentFilter(Tables.TABLE_LIST_CHANGED_ACTION));

        // Activo los menus contextuales de las celdas (long press)
        registerForContextMenu(mList);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_clean_all) {
            mTables.cleanAllTables();
            return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getActivity().getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        mLongPressTableNumber = ((Table)mList.getAdapter().getItem(info.position)).getTableNumber();
        menu.setHeaderTitle(String.format(getActivity().getString(R.string.table_number_title), mLongPressTableNumber));
        inflater.inflate(R.menu.menu_context_list_fragment, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        if (item.getItemId() == R.id.action_clean){
            mTables.cleanTable(mLongPressTableNumber);
        }else if (item.getItemId() == R.id.action_assign_fellows){
            launchAssignFellowDialog();
        }else{
            return false;
        }
        return true;
    }

    private void launchAssignFellowDialog() {
        //TODO: lanzar DialogFragmnet
        showSetFellowsDialog();
    }

    private class TableBroadcastReceiver extends BroadcastReceiver {
        private ArrayAdapter mAdapter;

        // Necesito el adapter al que voy a avisar de que hay nuevos datos
        public TableBroadcastReceiver(ArrayAdapter adapter) {
            super();
            mAdapter = adapter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Hay nuevos cambios, aviso al adaptador para que vuelva a recargarse
            mAdapter.notifyDataSetChanged();
        }
    }

    // Set fellows management
    protected void showSetFellowsDialog() {
//        hideInterfaceBeforeDialog();
        SetFellowsDialogFragment dialog = new SetFellowsDialogFragment();
        Bundle attrs = new Bundle();
        attrs.putInt(TABLE_NUMBER, mTables.getNumberOfFellowsForTable(mLongPressTableNumber));
        dialog.setArguments(attrs);
        dialog.setOnFellowsSetListener(this);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    public void onFellowsSetListener(SetFellowsDialogFragment dialog, String numberOfFellows) {
        mTables.setNumberOfFellows(Integer.parseInt(numberOfFellows), mLongPressTableNumber);
        dialog.dismiss();
//        revealInterfaceAfterDialog();
    }

    @Override
    public void onFellowsCancelListener(SetFellowsDialogFragment dialog) {
        dialog.dismiss();
//        revealInterfaceAfterDialog();
    }

    protected  void hideInterfaceBeforeDialog() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final ListView list = (ListView) getView().findViewById(android.R.id.list);
            int centerX = list.getWidth() / 2;
            int centerY = list.getHeight() / 2;

            Animator hideAnimation = ViewAnimationUtils.createCircularReveal(list, centerX, centerY, list.getWidth(), 0);
            hideAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    list.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            hideAnimation.setDuration(500);
            hideAnimation.start();
        }
    }

    protected void revealInterfaceAfterDialog() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ListView list = (ListView) getView().findViewById(android.R.id.list);
            list.setVisibility(View.VISIBLE);
            Animator showAnimation = ViewAnimationUtils.createCircularReveal(list, 0, 0, 0, Math.max(list.getWidth(), list.getHeight()));
            showAnimation.setDuration(500);
            showAnimation.start();
        }
    }
}
