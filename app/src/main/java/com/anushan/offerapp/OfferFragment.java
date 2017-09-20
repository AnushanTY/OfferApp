package com.anushan.offerapp;

//import android.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Anushan on 9/19/2017.
 */

public class OfferFragment extends Fragment {
    private static final String TAG = OfferFragment.class.getSimpleName();
    private RecyclerView recipeRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private OfferAdapter mRecipeAdapter;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference childRef;
    public OfferFragment() {
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catlog, container, false);
        getActivity().setTitle(getString(R.string.recipe_categories));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recipeRecyclerview = view.findViewById(R.id.recipe_categories);
        recipeRecyclerview.setHasFixedSize(true);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        childRef = mDatabaseRef.child("recipes");
        mRecipeAdapter = new OfferAdapter(OfferObject.class, R.layout.catlog_list, Offerholder.class, childRef, getContext());
        recipeRecyclerview.setLayoutManager(linearLayoutManager);
        recipeRecyclerview.setAdapter(mRecipeAdapter);
        return view;
    }
}
