package com.crazylabs.jogobookingapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazylabs.jogobookingapp.MainActivity;
import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.SignInActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private LinearLayout signOutLinearLayout;
    private TextView userName, userEmailId;
    private ImageView userDp;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        InitViews(view);

        userName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        userEmailId.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Glide
                .with(getContext())
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()) // the uri from Firebase
                .centerCrop()
                .into(userDp); //imageView variable
        signOutLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent k = new Intent(getContext(), SignInActivity.class);
                startActivity(k);
            }
        });
        return view;
    }

    private void InitViews(View view) {
        signOutLinearLayout= (LinearLayout) view.findViewById(R.id.fragment_profile_sign_out_linear_layout);
        userName= (TextView) view.findViewById(R.id.fragment_profile_username_textview);
        userEmailId= (TextView) view.findViewById(R.id.fragment_profile_email_id_textview);
        userDp= (ImageView) view.findViewById(R.id.fragment_profile_user_dp_imageview);
    }

}
