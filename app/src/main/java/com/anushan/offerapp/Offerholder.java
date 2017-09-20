package com.anushan.offerapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Offerholder extends RecyclerView.ViewHolder {
    private static final String TAG = Offerholder.class.getSimpleName();
    public TextView offerName;
    public ImageView offerImage;
    public Offerholder(View itemView) {
        super(itemView);
        offerName = itemView.findViewById(R.id.recipe_name);
        offerImage = itemView.findViewById(R.id.recipe_image);
    }
}