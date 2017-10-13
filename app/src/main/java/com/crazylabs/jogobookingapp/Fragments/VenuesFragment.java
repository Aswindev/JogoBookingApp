package com.crazylabs.jogobookingapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazylabs.jogobookingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenuesFragment extends Fragment {


    public VenuesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_venues, container, false);
    }

}
