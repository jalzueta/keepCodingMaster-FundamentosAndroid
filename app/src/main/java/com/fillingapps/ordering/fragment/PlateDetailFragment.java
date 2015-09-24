package com.fillingapps.ordering.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class PlateDetailFragment extends Fragment{

    private static final String ARG_PLATE = "plate";

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

        mPlateImage.setImageResource(getActivity().getResources().getIdentifier(mPlate.getImage(), "drawable", getActivity().getPackageName()));

        final ArrayAdapter<Ingredient> adapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_ingredient, mPlate.getIngredients());
        mIngredientsList.setAdapter(adapter);

        return root;
    }

}
