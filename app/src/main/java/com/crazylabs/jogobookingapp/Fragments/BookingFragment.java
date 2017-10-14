package com.crazylabs.jogobookingapp.Fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crazylabs.jogobookingapp.Animations.FadeInAndShowImage;
import com.crazylabs.jogobookingapp.Animations.FadeOutAndHideImage;
import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.Utils.ZoomOutPageTransformer;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment {


    private CarouselAdapter carouselAdapter;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private ImageView logoImage;
    private static Boolean logoVisibility=false;
    private static Boolean alreadyVisible=false;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking, container, false);


        InitViews(view);
        new FadeOutAndHideImage(logoImage, 50);

        OptimizeCardPadding();
        ToolbarStateCheck();
        return view;
    }

    private void ToolbarStateCheck() {
        appBarLayout.addOnOffsetChangedListener(new   AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < -collapsingToolbarLayout.getHeight() + toolbar.getHeight() + 20) {
                    //toolbar is collapsed here
                        logoVisibility=true;
                }else if (verticalOffset < 20){
                        logoVisibility=false;
                }
//                Log.d("Toolbarstatecheck", "fadeOut: "+toolbar.getAlpha());
                ModifyLogoState();

            }
        });
    }

    private void ModifyLogoState() {
//        Log.d("Toolbarstatecheck", "Outside: "+alreadyVisible+logoVisibility);
        if (logoVisibility && !alreadyVisible) {
            new FadeInAndShowImage(logoImage);
//            Log.d("Toolbarstatecheck", "fadeIn: "+alreadyVisible+logoVisibility);
            alreadyVisible=true;
//            Log.d("Toolbarstatecheck", "fadeIn: "+logoImage.getVisibility());
         }
        if (!logoVisibility && alreadyVisible) {
            new FadeOutAndHideImage(logoImage, 500);
//            Log.d("Toolbarstatecheck", "fadeOut: "+alreadyVisible+logoVisibility);
            alreadyVisible=false;
//            Log.d("Toolbarstatecheck", "fadeOut: "+logoImage.getVisibility());
        }
    }

    private void OptimizeCardPadding() {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, r.getDisplayMetrics());
        viewPager.setPageMargin((int) (-0.8 * px));
        viewPager.setOffscreenPageLimit(5);
    }

    private void InitViews(View view) {
        viewPager= (ViewPager) view.findViewById(R.id.booking_fragment_view_pager);
        appBarLayout= (AppBarLayout) view.findViewById(R.id.booking_fragment_app_bar_layout);
        collapsingToolbarLayout= (CollapsingToolbarLayout) view.findViewById(R.id.booking_fragment_collapsing_toolbar_layout);
        toolbar= (Toolbar) view.findViewById(R.id.booking_fragment_toolbar);
        logoImage= (ImageView) view.findViewById(R.id.booking_fragment_logo_image);
    }

    @Override
    public void onStart(){
        super.onStart();

        carouselAdapter = new CarouselAdapter(getChildFragmentManager());
        viewPager.setAdapter(carouselAdapter);
        viewPager.setPageTransformer(false, new ZoomOutPageTransformer(viewPager.getPaddingLeft()));
        viewPager.setOffscreenPageLimit(carouselAdapter.getCount());
//        viewPager.setPageMargin(15);
        viewPager.setClipToPadding(false);
        carouselAdapter.notifyDataSetChanged();
        viewPager.setPadding(0, 0, 0, 0);


    }

    private class CarouselAdapter extends FragmentStatePagerAdapter {

        private final int IMAGE_IDS[] = {R.drawable.panampilly, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
        private final String IMAGE_SUBTEXTS[] = {"kadavanthra","2","3","4","5"};


        public CarouselAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Needs change
            return ArenaCardFragment.getInstance(IMAGE_SUBTEXTS[position], IMAGE_IDS[position]);
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
