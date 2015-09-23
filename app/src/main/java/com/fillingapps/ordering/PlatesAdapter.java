package com.fillingapps.ordering;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fillingapps.ordering.activity.MainActivity;
import com.fillingapps.ordering.model.Plate;
import com.fillingapps.ordering.model.Plates;
import com.fillingapps.ordering.view.PlateView;


public class PlatesAdapter extends RecyclerView.Adapter<PlatesAdapter.PlatesViewHolder> {

    // Adaptador de RecyclerView que maneja varios ViewHolder eficientemente
    private Plates mPlates;
    private Context mContext;
    private int mContextMenuLayoutId;
    private Plate mLongPressPlate;

    public PlatesAdapter(Plates plates, Context context, int contextMenuLayoutId) {
        super();
        mPlates = plates;
        mContext = context;
        mContextMenuLayoutId = contextMenuLayoutId;
    }

    @Override
    public PlatesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlatesViewHolder(new PlateView(mContext), mContext);
    }

    @Override
    public void onBindViewHolder(final PlatesViewHolder holder, int position) {
        Plate currentPlate = mPlates.getPlates().get(position);
        holder.bindForecast(currentPlate);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mLongPressPlate = mPlates.getPlates().get(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlates.getPlates().size();
    }


    // ViewHolder que maneja una vista
    public class PlatesViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private PlateView mPlateView;
        private Context mContext;

        public PlatesViewHolder(View itemView, Context context) {
            super(itemView);

            mContext = context;
            mPlateView = (PlateView) itemView;
            itemView.setOnCreateContextMenuListener(this);
        }

        // Nos asocian el modelo con este ViewHolder
        public void bindForecast(final Plate plate) {

            mPlateView.setPlateName(plate.getName());
            mPlateView.setPlateIngredients(plate.getIngredientsString());
            mPlateView.setPlatePrice(plate.getPrice());
            int iconID = mContext.getResources().getIdentifier(plate.getImage(), "drawable", mContext.getPackageName());
            mPlateView.setPlateImage(iconID);
            mPlateView.setPlateNotes(plate.getNotes());
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(mLongPressPlate.getName());

            if (mContext instanceof Activity) {
                MenuInflater inflater = ((Activity) mContext).getMenuInflater();
                inflater.inflate(mContextMenuLayoutId, menu);
            }
        }
    }
}
