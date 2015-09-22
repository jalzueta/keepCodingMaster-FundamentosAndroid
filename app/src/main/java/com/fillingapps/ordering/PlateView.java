package com.fillingapps.ordering;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PlateView extends CardView{

    private int mPlateImage;
    private String mPlateName;
    private String mPlateIngredients;
    private float mPlatePrice;

    private ImageView mPlateImageView;
    private TextView mNameTextView;
    private TextView mIngredientsTextView;
    private TextView mPriceTextView;

    public PlateView(Context context) {
        this(context, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
    }

    public PlateView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_plate, this, true);

        mNameTextView = (TextView) findViewById(R.id.plate_name);
        mIngredientsTextView = (TextView) findViewById(R.id.plate_ingredients);
        mPriceTextView = (TextView) findViewById(R.id.plate_price);
        mPlateImageView = (ImageView) findViewById(R.id.plate_image);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PlateView, 0, 0);
        if (a != null) {
            mPlateImage = a.getInt(R.styleable.PlateView_image, R.drawable.no_image);
            mPlateIngredients = a.getString(R.styleable.PlateView_ingredients);
            mPlatePrice = a.getFloat(R.styleable.PlateView_price, 0);
            mPlateName = a.getString(R.styleable.PlateView_name);
            a.recycle();

            setPlateImage(mPlateImage);
            setPlateName(mPlateName);
            setPlateIngredients(mPlateIngredients);
            setPlatePrice(mPlatePrice);
        }
    }

    public int getPlateImage() {
        return mPlateImage;
    }

    public void setPlateImage(int plateImage) {
        mPlateImage = plateImage;
        mPlateImageView.setImageResource(mPlateImage);
    }

    public String getPlateName() {
        return mPlateName;
    }

    public void setPlateName(String plateName) {
        mPlateName = plateName;
        mNameTextView.setText(mPlateName);
    }

    public String getPlateIngredients() {
        return mPlateIngredients;
    }

    public void setPlateIngredients(String plateIngredients) {
        mPlateIngredients = plateIngredients;
        mIngredientsTextView.setText(mPlateIngredients);
    }

    public float getPlatePrice() {
        return mPlatePrice;
    }

    public void setPlatePrice(float platePrice) {
        mPlatePrice = platePrice;
        mPriceTextView.setText(String.valueOf(String.format("%.2f €", mPlatePrice)));
    }
}