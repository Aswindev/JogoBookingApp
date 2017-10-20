package com.crazylabs.jogobookingapp;

import android.animation.Animator;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crazylabs.jogobookingapp.Adapters.CartAdapter;
import com.crazylabs.jogobookingapp.Adapters.VenueAdapter;
import com.crazylabs.jogobookingapp.DataModels.CartDataModel;
import com.crazylabs.jogobookingapp.DataModels.SelectedSlotDataModel;
import com.crazylabs.jogobookingapp.DataModels.VenueDataModel;
import com.crazylabs.jogobookingapp.Utils.CartListener;
import com.crazylabs.jogobookingapp.Utils.ItemClickSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.crazylabs.jogobookingapp.MainActivity.selectedSlotList;

public class CartActivity extends AppCompatActivity implements CartListener {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartDataModel> cartList;

    private ImageView arrowImageView;
    private int totalCartPrice=0;
    private TextView payNowTextView;
    private Animation animTranslation;
    private RelativeLayout payNowRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        initViews();

        cartList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmp = new LinearLayoutManager(CartActivity.this);
        llmp.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmp);
        adapter = new CartAdapter(getApplicationContext(), fillcartList(), CartActivity.this);
        recyclerView.setAdapter(adapter);

        animTranslation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.horizontal_translation_animation);
        arrowImageView.startAnimation(animTranslation);

        final SimpleDateFormat BookingIdFormat = new SimpleDateFormat("yyyyMMdd");

        payNowRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(CartActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to proceed to payment?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

//                                Bookingid yyyyMMddGroundIdSlotID.   bookingDate yyyyMMddHH
                                String bookingId= String.valueOf(BookingIdFormat.format(selectedSlotList.get(0).fullDate));
                                String timeText = (selectedSlotList.get(0).time < 10 ? "0" : "") + selectedSlotList.get(0).time;
                                Log.d("payment", "GroundId: "+selectedSlotList.get(0).locationCode);
                                Log.d("paymentB", "BookingId: "+bookingId);
                                Log.d("paymentB", "BookingDate: "+bookingId+timeText);
                            }
                        }).create().show();
            }
        });

    }

    private List<CartDataModel> fillcartList() {
        Iterator<SelectedSlotDataModel> iterator = selectedSlotList.iterator();

        while(iterator.hasNext()) {
            SelectedSlotDataModel currentObject = iterator.next();

            Log.d("cartlistvalues", "fillcartList: "+currentObject.location+" "+currentObject.date+"-"+currentObject.month+"-"+currentObject.year+" "+currentObject.day+" "+currentObject.time+" "+currentObject.price+" "+currentObject.groundType);
            CartDataModel cartDataModel=new CartDataModel(currentObject.location,currentObject.date,currentObject.month,currentObject.year,currentObject.day,currentObject.time,currentObject.price,currentObject.groundType);
            cartDataModel.setFullDate(currentObject.fullDate);
            cartList.add(cartDataModel);
            totalCartPrice+=cartDataModel.getBooking_amount();
        }
//        adapter.notifyDataSetChanged();
        String temp=String.valueOf(totalCartPrice);
        if (totalCartPrice!=0) {
            payNowTextView.setText("Pay ₹"+temp);
            arrowImageView.setVisibility(View.VISIBLE);
        } else {
            payNowTextView.setText("Your cart is empty");
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.GONE);
        }
        return cartList;
    }


    public void RefreshTotalCartPrice() {
        Iterator<SelectedSlotDataModel> iterator = selectedSlotList.iterator();
        totalCartPrice=0;
        if (selectedSlotList.isEmpty()){
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.GONE);
            payNowTextView.setText("Your cart is empty");
        }
        while(iterator.hasNext()) {
            SelectedSlotDataModel currentObject = iterator.next();
            totalCartPrice+=currentObject.price;

            String temp=String.valueOf(totalCartPrice);
            if (totalCartPrice!=0) {
                payNowTextView.setText("Pay ₹"+temp);
            } else {
                payNowTextView.setText("Your cart is empty");
            }
        }
    }

    public void initViews(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView)findViewById(R.id.cart_recycler_view);
        arrowImageView= (ImageView) findViewById(R.id.activity_card_arrow_image_view);
        payNowTextView= (TextView) findViewById(R.id.activity_cart_pay_now_textview);
        payNowRelativeLayout= (RelativeLayout) findViewById(R.id.activity_cart_pay_now_relative_layout);
    }

    @Override
    public void itemDeleted() {
//        Toast.makeText(getApplicationContext(), "Listener activated", Toast.LENGTH_SHORT).show();
        RefreshTotalCartPrice();
    }
}
