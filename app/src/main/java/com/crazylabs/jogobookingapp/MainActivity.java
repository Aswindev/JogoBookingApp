package com.crazylabs.jogobookingapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.crazylabs.jogobookingapp.Adapters.ViewPagerAdapter;
import com.crazylabs.jogobookingapp.Fragments.BookingFragment;
import com.crazylabs.jogobookingapp.Fragments.ProfileFragment;
import com.crazylabs.jogobookingapp.Fragments.VenuesFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem prevMenuItem;

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

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        BookingFragment bookingFragment = new BookingFragment();
        VenuesFragment venuesFragment = new VenuesFragment();
        ProfileFragment profileFragment = new ProfileFragment();
        adapter.addFragment(bookingFragment);
        adapter.addFragment(venuesFragment);
        adapter.addFragment(profileFragment);
        viewPager.setAdapter(adapter);
    }
}
