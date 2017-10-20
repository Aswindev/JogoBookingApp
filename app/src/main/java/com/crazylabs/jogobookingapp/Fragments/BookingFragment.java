package com.crazylabs.jogobookingapp.Fragments;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crazylabs.jogobookingapp.Adapters.DaysRecyclerViewAdapter;
import com.crazylabs.jogobookingapp.Animations.FadeInAndShowImage;
import com.crazylabs.jogobookingapp.Animations.FadeOutAndHideImage;
import com.crazylabs.jogobookingapp.CartActivity;
import com.crazylabs.jogobookingapp.DataModels.DaysDataModel;
import com.crazylabs.jogobookingapp.DataModels.SelectedSlotDataModel;
import com.crazylabs.jogobookingapp.R;
import com.crazylabs.jogobookingapp.Utils.ArenaLocationClass;
import com.crazylabs.jogobookingapp.Utils.ItemClickSupport;
import com.crazylabs.jogobookingapp.Utils.ZoomOutPageTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.crazylabs.jogobookingapp.MainActivity.cachedCartPrice;
import static com.crazylabs.jogobookingapp.MainActivity.cachedPosition;
import static com.crazylabs.jogobookingapp.MainActivity.currentSelectedGroundType;
import static com.crazylabs.jogobookingapp.MainActivity.currentSelectedLocation;
import static com.crazylabs.jogobookingapp.MainActivity.currentSelectedLocationCode;
import static com.crazylabs.jogobookingapp.MainActivity.selectedDayPosition;
import static com.crazylabs.jogobookingapp.MainActivity.selectedSlotList;


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

    private DaysDataModel daysDataModel;
    final List<DaysDataModel> result = new ArrayList<DaysDataModel>();
    private RecyclerView daysRecList;
    private DaysRecyclerViewAdapter ca;

    private TextView locationNameTextView;

    private static DaysDataModel currentSelectedSlot;
    private static SelectedSlotDataModel selectedSlot;


    private int morningPrice=1200, evePrice=1800;

    private LinearLayout[] timeSlotLinearLayout=new LinearLayout[20];
    private TextView[] timeSlotPriceTextView=new TextView[20];
    private LinearLayout cartLinearLayout;
    private TextView cartPriceTextView;
    private int cartPrice=cachedCartPrice;

    private RadioGroup radioGroup;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private RadioButton radioButton7;


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_booking, container, false);


        InitViews(view);
        RefreshViews();
//        Hide JOGO logo initially
        new FadeOutAndHideImage(logoImage, 50);
        cartLinearLayout.setVisibility(View.GONE);
//        Set negative margin for the arena cards to make multiple cards visible simultaneously
        OptimizeCardPadding();
//        Decide when to show JOGO logo

        ToolbarStateCheck();
        InitHorizontalDateSelectorList(view);

        SetListenersForTimeSlots();
        SetListenerForRadioGroup();
        Log.d("initValues", "onCreateView: "+selectedDayPosition);

        RefreshCartPrice();
        cartLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void RefreshCartPrice() {
        Iterator<SelectedSlotDataModel> iterator = selectedSlotList.iterator();
        cartPrice=0;

        while(iterator.hasNext()) {
            SelectedSlotDataModel currentObject = iterator.next();

            Log.d("TimeslotlistenersLL", "inList: "+ currentObject.price);
            cartPrice+=currentObject.price;
        }
        cachedCartPrice=cartPrice;
    }

    private void RefreshViews() {
//        Refresh time slot prices
        for (int i = 0; i < 20; i++) {
            if (i<12) {
                timeSlotPriceTextView[i].setText(String.valueOf(morningPrice)+"₹");
            } else {
                timeSlotPriceTextView[i].setText(String.valueOf(evePrice)+"₹");
            }
        }
//        Refresh selected slots
        refreshSlotSelection();
//        Refresh cart price
        cartPriceTextView.setText(String.valueOf(cartPrice)+"₹");
    }

    private void SetListenerForRadioGroup() {

        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                morningPrice=1200;
                evePrice=1800;

//                collapse toolbar
                appBarLayout.setExpanded(false);

//                Clear all selections and cart on changing radiobutton
                clearSelectedtimeSlots();

                for (int i = 0; i < 20; i++) {
                    if (i<12) {
                        timeSlotPriceTextView[i].setText(String.valueOf(morningPrice)+"₹");
                    } else {
                        timeSlotPriceTextView[i].setText(String.valueOf(evePrice)+"₹");
                    }
                }

            }
        });

        radioButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    morningPrice=1500;
                    evePrice=2000;

//                collapse toolbar
                appBarLayout.setExpanded(false);

//                Clear all selections and cart on changing radiobutton
                clearSelectedtimeSlots();

                for (int i = 0; i < 20; i++) {
                    if (i<12) {
                        timeSlotPriceTextView[i].setText(String.valueOf(morningPrice)+"₹");
                    } else {
                        timeSlotPriceTextView[i].setText(String.valueOf(evePrice)+"₹");
                    }
                }

            }
        });

        radioButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                morningPrice=2000;
                evePrice=2500;

//                collapse toolbar
                appBarLayout.setExpanded(false);

//                Clear all selections and cart on changing radiobutton
                clearSelectedtimeSlots();

                for (int i = 0; i < 20; i++) {
                    if (i<12) {
                        timeSlotPriceTextView[i].setText(String.valueOf(morningPrice)+"₹");
                    } else {
                        timeSlotPriceTextView[i].setText(String.valueOf(evePrice)+"₹");
                    }
                }
            }
        });
    }

    private void clearSelectedtimeSlots() {
        selectedSlotList.clear();
        cartPrice=0;
        cachedCartPrice=cartPrice;
        RefreshViews();
    }

    private void SetListenersForTimeSlots() {
        for (int i = 0; i < 20; i++) {
            final int finalI = i;
            timeSlotLinearLayout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.d("TimeslotlistenersLL", "onClick: "+ finalI);
//                    Log.d("TimeslotlistenersLL", "onClick: "+ currentSelectedSlot.year);

//                collapse toolbar
                    appBarLayout.setExpanded(false);

                    selectedSlot=new SelectedSlotDataModel(currentSelectedSlot,finalI,currentSelectedLocation,currentSelectedGroundType);
                    selectedSlot.setFullDate(currentSelectedSlot.fullDate);
                    selectedSlot.setLocationCode(currentSelectedLocationCode);
                    if (finalI<12) {
                        selectedSlot.setPrice(morningPrice);
                    } else {
                        selectedSlot.setPrice(evePrice);
                    }
                    Log.d("TimeslotlistenersLL", "onClick: "+ selectedSlotList.contains(selectedSlot));

                    if (!selectedSlotList.contains(selectedSlot)) {
                        selectedSlotList.add(selectedSlot);
                        RefreshCartPrice();
                        cachedCartPrice=cartPrice;
                        cartPriceTextView.setText(String.valueOf(cartPrice)+"₹");
                    } else {
                        selectedSlotList.remove(selectedSlot);
                        RefreshCartPrice();
                        cachedCartPrice=cartPrice;
                        cartPriceTextView.setText(String.valueOf(cartPrice)+"₹");
                    }

                    refreshSlotSelection();
                }
            });
        }
    }

    private void refreshSlotSelection() {
        Iterator<SelectedSlotDataModel> iterator = selectedSlotList.iterator();

//        Unselect all
        for (int i = 0; i < 20; i++) {
            timeSlotLinearLayout[i].setBackgroundResource(0);
        }

        while(iterator.hasNext()) {
            SelectedSlotDataModel currentObject = iterator.next();

            Log.d("TimeslotlistenersLL", "inList: "+ currentObject.year+currentObject.month+currentObject.date+currentObject.time);

//            Log.d("TimeslotlistenersLL", "selecteddate: "+ currentObject.date+" selectedDayPosition");

            if (currentObject.date==result.get(selectedDayPosition).date) {
                for (int i = 0; i < 20; i++) {
                    if (currentObject.time==i) {
                        timeSlotLinearLayout[i].setBackgroundResource(R.drawable.hollow_rectangle);
//                        Log.d("TimeslotlistenersLL", "selectedTimes: "+ i);
                    }
                }
            }
        }
    }

    private void InitHorizontalDateSelectorList(View view) {
        daysRecList = (RecyclerView) view.findViewById(R.id.fragment_booking_days_recyclerview);
        daysRecList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        daysRecList.setLayoutManager(llm);
        ca = new DaysRecyclerViewAdapter(createListDays(100),getContext());
        daysRecList.setAdapter(ca);

//        set first date as selected
        currentSelectedSlot=result.get(cachedPosition);
//        Listener for date selection recyclerview
        ItemClickSupport.addTo(daysRecList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                currentSelectedSlot=result.get(position);
                selectedDayPosition=position;
                cachedPosition=position;
                refreshSlotSelection();
                ca.notifyDataSetChanged();

//                collapse toolbar
                appBarLayout.setExpanded(false);

            }
        });
    }

    private List<DaysDataModel> createListDays(final int size) {
        Calendar cal = Calendar.getInstance(); //Get the Calendar instance
        Date fromDate = cal.getTime();// Get the Date object

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        cal.add(Calendar.MONTH,3);//Three months from now
        Date toDate = cal.getTime();// Get the Date object

        int tempYear, tempMonth, tempTime;

//        Log.d("Dates", "createListDays-> From: "+fromDate);
//        Log.d("Dates", "createListDays-> To: "+toDate);

        cal.setTime(fromDate);
        while (cal.getTime().before(toDate)) {
            daysDataModel=new DaysDataModel(cal.get(Calendar.YEAR),monthFormat.format(cal.getTime()),cal.get(Calendar.DATE),dayFormat.format(cal.getTime()));
            daysDataModel.setFullDate(cal.getTime());
            result.add(daysDataModel);
            cal.add(Calendar.DATE, 1);
        }

//        daysDataModel.setTime(timeFormat.format(cal.getTime()));

        return result;
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
            cartLinearLayout.setVisibility(View.VISIBLE);
//            Log.d("Toolbarstatecheck", "fadeIn: "+alreadyVisible+logoVisibility);
            alreadyVisible=true;
//            Log.d("Toolbarstatecheck", "fadeIn: "+logoImage.getVisibility());
         }
        if (!logoVisibility && alreadyVisible) {
            new FadeOutAndHideImage(logoImage, 300);
            cartLinearLayout.setVisibility(View.GONE);
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
        locationNameTextView= (TextView) view.findViewById(R.id.fragment_booking_location_textview);
        cartLinearLayout= (LinearLayout) view.findViewById(R.id.fragment_booking_cart_linear_layout);
        cartPriceTextView= (TextView) view.findViewById(R.id.fragment_booking_cart_price_textview);

        radioGroup= (RadioGroup) view.findViewById(R.id.fragment_booking_radio_group);
        radioButton5= (RadioButton) view.findViewById(R.id.fragment_booking_radio_button_5);
        radioButton6= (RadioButton) view.findViewById(R.id.fragment_booking_radio_button_6);
        radioButton7= (RadioButton) view.findViewById(R.id.fragment_booking_radio_button_7);

        timeSlotLinearLayout[0]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_1);
        timeSlotLinearLayout[1]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_2);
        timeSlotLinearLayout[2]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_3);
        timeSlotLinearLayout[3]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_4);
        timeSlotLinearLayout[4]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_5);
        timeSlotLinearLayout[5]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_6);
        timeSlotLinearLayout[6]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_7);
        timeSlotLinearLayout[7]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_8);
        timeSlotLinearLayout[8]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_9);
        timeSlotLinearLayout[9]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_10);
        timeSlotLinearLayout[10]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_11);
        timeSlotLinearLayout[11]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_12);
        timeSlotLinearLayout[12]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_13);
        timeSlotLinearLayout[13]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_14);
        timeSlotLinearLayout[14]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_15);
        timeSlotLinearLayout[15]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_16);
        timeSlotLinearLayout[16]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_17);
        timeSlotLinearLayout[17]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_18);
        timeSlotLinearLayout[18]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_19);
        timeSlotLinearLayout[19]= (LinearLayout) view.findViewById(R.id.fragment_booking_linear_layout_20);

        timeSlotPriceTextView[0]= (TextView) view.findViewById(R.id.fragment_booking_text_view_1);
        timeSlotPriceTextView[1]= (TextView) view.findViewById(R.id.fragment_booking_text_view_2);
        timeSlotPriceTextView[2]= (TextView) view.findViewById(R.id.fragment_booking_text_view_3);
        timeSlotPriceTextView[3]= (TextView) view.findViewById(R.id.fragment_booking_text_view_4);
        timeSlotPriceTextView[4]= (TextView) view.findViewById(R.id.fragment_booking_text_view_5);
        timeSlotPriceTextView[5]= (TextView) view.findViewById(R.id.fragment_booking_text_view_6);
        timeSlotPriceTextView[6]= (TextView) view.findViewById(R.id.fragment_booking_text_view_7);
        timeSlotPriceTextView[7]= (TextView) view.findViewById(R.id.fragment_booking_text_view_8);
        timeSlotPriceTextView[8]= (TextView) view.findViewById(R.id.fragment_booking_text_view_9);
        timeSlotPriceTextView[9]= (TextView) view.findViewById(R.id.fragment_booking_text_view_10);
        timeSlotPriceTextView[10]= (TextView) view.findViewById(R.id.fragment_booking_text_view_11);
        timeSlotPriceTextView[11]= (TextView) view.findViewById(R.id.fragment_booking_text_view_12);
        timeSlotPriceTextView[12]= (TextView) view.findViewById(R.id.fragment_booking_text_view_13);
        timeSlotPriceTextView[13]= (TextView) view.findViewById(R.id.fragment_booking_text_view_14);
        timeSlotPriceTextView[14]= (TextView) view.findViewById(R.id.fragment_booking_text_view_15);
        timeSlotPriceTextView[15]= (TextView) view.findViewById(R.id.fragment_booking_text_view_16);
        timeSlotPriceTextView[16]= (TextView) view.findViewById(R.id.fragment_booking_text_view_17);
        timeSlotPriceTextView[17]= (TextView) view.findViewById(R.id.fragment_booking_text_view_18);
        timeSlotPriceTextView[18]= (TextView) view.findViewById(R.id.fragment_booking_text_view_19);
        timeSlotPriceTextView[19]= (TextView) view.findViewById(R.id.fragment_booking_text_view_20);
    }

    @Override
    public void onStart(){
        super.onStart();

        RefreshCartPrice();

        carouselAdapter = new CarouselAdapter(getChildFragmentManager());
        viewPager.setAdapter(carouselAdapter);
        viewPager.setPageTransformer(false, new ZoomOutPageTransformer(viewPager.getPaddingLeft()));
        viewPager.setOffscreenPageLimit(carouselAdapter.getCount());
//        viewPager.setPageMargin(15);
        viewPager.setClipToPadding(false);
        carouselAdapter.notifyDataSetChanged();
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int temp=-1;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("viewpagercurrentpage", "onPageScrolled: "+ArenaLocationClass.IMAGE_SUBTEXTS[position]);
                if (position!=temp) {
                    temp=position;
                    locationNameTextView.setText(ArenaLocationClass.IMAGE_SUBTEXTS[position]);
                    currentSelectedLocation=ArenaLocationClass.IMAGE_SUBTEXTS[position];
                    currentSelectedLocationCode=position;
                    radioButton5.setChecked(true);
//                    clearSelectedtimeSlots();
                    RefreshViews();
                }
            }

            @Override
            public void onPageSelected(int position) {
//                Log.d("viewpagercurrentpage", "onPageSelected: "+ArenaLocationClass.IMAGE_SUBTEXTS[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class CarouselAdapter extends FragmentStatePagerAdapter {

        private final int IMAGE_IDS[] = ArenaLocationClass.IMAGE_IDS;
        private final String IMAGE_SUBTEXTS[] = ArenaLocationClass.IMAGE_SUBTEXTS;


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
