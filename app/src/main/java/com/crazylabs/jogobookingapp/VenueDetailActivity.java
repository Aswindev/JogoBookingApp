package com.crazylabs.jogobookingapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crazylabs.jogobookingapp.DataModels.VenueDataModel;

import java.util.Locale;

import static android.view.View.GONE;


public class VenueDetailActivity extends AppCompatActivity {
    private String location;
    private int loc_thumbnail;
    private ImageView venueThumbnail;
    private TextView venueLoc;
    private Toolbar toolbar;
    private Button get_direction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_detail);

        initViews();

        try {
            location = getIntent().getExtras().getString("vloc");
        }catch (Exception e){}

        try {
            loc_thumbnail = getIntent().getExtras().getInt("vpic");
        }catch (Exception e){}

        //Log.d("Tourlistdata", String.valueOf(lo));

        venueLoc.setText(location);
        Glide.with(getApplicationContext()).load(loc_thumbnail).into(venueThumbnail);

        get_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//                startActivity(intent);

                if(location.equals("Kadavntra"))
                {
                String uri = "http://maps.google.com/maps?&daddr=" + 9.952852 + "," + 76.309624;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
            else if(location.equals("Edapally"))
                {
                    String uri = "http://maps.google.com/maps?&daddr=" + 10.0270753 + "," + 76.3080901;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            }
        });

    }

    public void initViews(){

        toolbar = (Toolbar) findViewById(R.id.venue_details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initCollapsingToolbar();

        venueThumbnail = (ImageView) findViewById(R.id.venue_details_img);
        venueLoc = (TextView) findViewById(R.id.venue_details_location);
        get_direction  = (Button) findViewById(R.id.get_direction_button);

    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    toolbar.setVisibility(GONE);
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar.setLogo(R.drawable.jogo_logo);
                    isShow = true;
                } else if (isShow) {
                    toolbar.setVisibility(GONE);
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }





}
