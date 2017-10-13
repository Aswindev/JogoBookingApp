package com.crazylabs.jogobookingapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazylabs.jogobookingapp.R;

/**
 * Created by aswin on 13/10/2017.
 */

public class ArenaCardFragment extends Fragment {


    private String text = null;
    private int imageId = 0;

    private static final String ARGS_TEXT = "text";
    private static final String ARGS_IMAGE_ID = "image_id";


    public static ArenaCardFragment getInstance(String t, int id){
        ArenaCardFragment fragment = new ArenaCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_IMAGE_ID, id);
        args.putString(ARGS_TEXT, t);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            imageId = getArguments().getInt(ARGS_IMAGE_ID);
            text = getArguments().getString(ARGS_TEXT);
        }
    }




    public ArenaCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_arena_card, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.fragment_arena_card_image);
        TextView textView = (TextView) view.findViewById(R.id.fragment_arena_card_text);

        if((imageId != 0) && (text != null)){
            imageView.setImageResource(imageId);
            textView.setText(text);
        }

        return view;
    }

}
