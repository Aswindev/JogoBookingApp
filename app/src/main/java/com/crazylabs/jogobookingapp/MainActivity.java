package com.crazylabs.jogobookingapp;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.crazylabs.jogobookingapp.Adapters.ViewPagerAdapter;
import com.crazylabs.jogobookingapp.DataModels.SelectedSlotDataModel;
import com.crazylabs.jogobookingapp.Fragments.BookingFragment;
import com.crazylabs.jogobookingapp.Fragments.ProfileFragment;
import com.crazylabs.jogobookingapp.Fragments.VenuesFragment;
import com.crazylabs.jogobookingapp.Utils.FragmentRefreshListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    public static int selectedDayPosition;
    public static int cachedPosition=0;
    public static int cachedCartPrice=0;
    public static String currentSelectedLocation;
    public static int currentSelectedGroundType=5;
    public final static List<SelectedSlotDataModel> selectedSlotList = new ArrayList<SelectedSlotDataModel>();
    public static int currentSelectedLocationCode=0;
    public static int cachedLocationCode;

    private FragmentRefreshListener fragmentRefreshListener;
    private Boolean refreshFragments=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView= (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);

        viewPager= (ViewPager) findViewById(R.id.activity_main_viewpager);
        setupViewPager(viewPager);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_booking:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_venues:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_profile:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }

                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setListener(FragmentRefreshListener listener)
    {
        this.fragmentRefreshListener = listener ;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BookingFragment bookingFragment = new BookingFragment();
        setListener(bookingFragment);
        VenuesFragment venuesFragment = new VenuesFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        adapter.addFragment(bookingFragment);
        adapter.addFragment(venuesFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
    }

    @Override

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                    }
                }).create().show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (refreshFragments){

            fragmentRefreshListener.refreshFragment();
            refreshFragments=false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        refreshFragments=true;
    }
}
