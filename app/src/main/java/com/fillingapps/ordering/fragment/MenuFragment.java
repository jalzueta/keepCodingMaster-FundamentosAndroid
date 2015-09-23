package com.fillingapps.ordering.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fillingapps.ordering.adapter.PlatesAdapter;
import com.fillingapps.ordering.R;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Plates;
import com.fillingapps.ordering.network.PlatesDownloader;

import java.lang.ref.WeakReference;

public class MenuFragment extends Fragment implements PlatesDownloader.OnPlatesReceivedListener, PlatesAdapter.OnPlateAdapterPressedListener{

    protected WeakReference<OnPlateAddedToTableListener> mOnPlateAddedToTableListener;
    private Plate mPlatePressed;

    private RecyclerView mPlateList;

    public static MenuFragment newInstance() {
        return new MenuFragment();
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

        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        mPlateList = (RecyclerView) root.findViewById(R.id.menu_recycler);
        mPlateList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlateList.setItemAnimator(new DefaultItemAnimator());

        registerForContextMenu(mPlateList);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Plates.getInstance().getPlates().size() == 0){
            downloadMenu();
        }else{
            setPlates();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnPlateAddedToTableListener = new WeakReference<OnPlateAddedToTableListener>((OnPlateAddedToTableListener) getActivity());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mOnPlateAddedToTableListener = new WeakReference<OnPlateAddedToTableListener>((OnPlateAddedToTableListener) getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnPlateAddedToTableListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_menu_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh_plates) {
            downloadMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        if (item.getItemId() == R.id.action_add){
            // Avisamos a la Activity de que se ha añadido un plato
            if (mOnPlateAddedToTableListener != null && mOnPlateAddedToTableListener.get() != null){
                mOnPlateAddedToTableListener.get().onPlateAddedToTable(mPlatePressed);
            }
        }else{
            return false;
        }
        return true;
    }

    private void downloadMenu() {
        showLoading();
        AsyncTask<String, Integer, Plates> platesTask = new PlatesDownloader(this);
        platesTask.execute();
    }

    private void setPlates(){
        Plates plates = Plates.getInstance();
        if (plates.getPlates().size() > 0){
            mPlateList.swapAdapter(new PlatesAdapter(plates.getPlates(), getActivity(), R.menu.menu_context_plates, this), false);
        }
    }

    public void showLoading() {
        if (getView() != null) {
            getView().findViewById(R.id.menu_recycler).setVisibility(View.GONE);
            getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
        }
    }

    public void showPlates() {
        if (getView() != null) {
            getView().findViewById(R.id.menu_recycler).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.loading).setVisibility(View.GONE);
        }
    }

    @Override
    public void onPlatesReceivedListener() {
        setPlates();
        showPlates();
    }

    @Override
    public void onPlateAdapterLongPressed(Plate plate) {
        mPlatePressed = plate;
    }

    public interface OnPlateAddedToTableListener {
        void onPlateAddedToTable (Plate plate);
    }
}
