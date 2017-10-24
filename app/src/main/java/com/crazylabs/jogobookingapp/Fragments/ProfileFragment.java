package com.crazylabs.jogobookingapp.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;




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
//        Glide
//                .with(getContext())
//                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()) // the uri from Firebase
//                .centerCrop()
//                .into(userDp); //imageView variable

        Uri photourl =FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

        Picasso.with(getContext()).load(photourl)
                .resize(230, 230)
                .into(userDp, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) userDp.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        userDp.setImageDrawable(imageDrawable);
                    }
                    @Override
                    public void onError() {

                    }
                });

        signOutLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent k = new Intent(getContext(), SignInActivity.class);
                k.putExtra("signOut",true);
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
