package com.crazylabs.jogobookingapp.Fragments;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.Adapters.VenueAdapter;
import com.crazylabs.jogobookingapp.DataModels.VenueDataModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class VenuesFragment extends Fragment {
    private RecyclerView recyclerView;
    private VenueAdapter adapter;
    private List<VenueDataModel> venueList;
    View view;


    public VenuesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_venues, container, false);

        initViews();

        venueList = new ArrayList<>();
        adapter = new VenueAdapter(getContext(), venueList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmp = new LinearLayoutManager(getActivity());
        llmp.setOrientation(LinearLayoutManager.VERTICAL);
        llmp.setReverseLayout(true);
        llmp.setStackFromEnd(true);
        recyclerView.setLayoutManager(llmp);
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        return view;
    }

    public void initViews(){

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.venue_recycler_view);

    }

    /**
     * Adding Venues
     */
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.edapally,
                R.drawable.panampilly,
      };

        VenueDataModel a = new VenueDataModel("Edapally",covers[0]);
        venueList.add(a);

        a = new VenueDataModel("Kadavntra",covers[1]);
        venueList.add(a);


        adapter.notifyDataSetChanged();
    }

}
