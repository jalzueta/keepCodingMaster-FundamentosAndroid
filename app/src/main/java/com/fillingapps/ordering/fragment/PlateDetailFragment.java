package com.fillingapps.ordering.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fillingapps.ordering.R;
import com.fillingapps.ordering.model.Ingredient;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Tables;

import java.lang.ref.WeakReference;

public class PlateDetailFragment extends Fragment{

    private static final String ARG_PLATE = "plate";

    protected WeakReference<OnPlateNotesChangedListener> mOnPlateNotesChangedListener;

    private Plate mPlate;
    private ListView mIngredientsList;

    private TextView mPlateTitle;
    private ImageView mPlateImage;
    private TextView mPlateAlergens;
    private TextView mPlateDescription;
    private TextView mPlateNotes;

    public static PlateDetailFragment newInstance(Plate plate) {
        PlateDetailFragment fragment = new PlateDetailFragment();

        // 2) Nos creamos los argumentos y los empaquetamos
        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_PLATE, plate);

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
            mPlate = (Plate) getArguments().getSerializable(ARG_PLATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_plate_detail, container, false);

        mPlateTitle = (TextView) root.findViewById(R.id.plate_detail_title);
        mPlateImage = (ImageView) root.findViewById(R.id.plate_detail_image);
        mPlateAlergens = (TextView) root.findViewById(R.id.plate_detail_allergens);
        mPlateDescription = (TextView) root.findViewById(R.id.plate_detail_description);
        mPlateNotes = (TextView) root.findViewById(R.id.plate_detail_notes);
        mIngredientsList = (ListView) root.findViewById(android.R.id.list);

        mPlateTitle.setText(mPlate.getName());
        mPlateAlergens.setText(mPlate.getAllergensString());
        mPlateDescription.setText(mPlate.getDescription());
        mPlateNotes.setText(mPlate.getNotes());

        mPlateNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO: avisar a la activity para que actualice el mPlate
                if (mOnPlateNotesChangedListener != null && mOnPlateNotesChangedListener.get() != null) {
                    mPlate.setNotes(mPlateNotes.getText().toString());
                    mOnPlateNotesChangedListener.get().onPlateNotesChanged(mPlate);
                }
            }
        });

        //mPlateImage.setImageResource(getActivity().getResources().getIdentifier(mPlate.getImage(), "drawable", getActivity().getPackageName()));
        mPlateImage.setImageBitmap(mPlate.getImageBitmap());

        final ArrayAdapter<Ingredient> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_ingredient, mPlate.getIngredients());
        mIngredientsList.setAdapter(adapter);

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mOnPlateNotesChangedListener = new WeakReference<OnPlateNotesChangedListener>((OnPlateNotesChangedListener) getActivity());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mOnPlateNotesChangedListener = new WeakReference<OnPlateNotesChangedListener>((OnPlateNotesChangedListener) getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnPlateNotesChangedListener = null;
    }

    public interface OnPlateNotesChangedListener {
        void onPlateNotesChanged (Plate plate);
    }

}
