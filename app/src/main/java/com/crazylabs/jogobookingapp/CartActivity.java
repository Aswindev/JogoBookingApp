package com.crazylabs.jogobookingapp;

import android.animation.Animator;
import android.app.ProgressDialog;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.crazylabs.jogobookingapp.Adapters.CartAdapter;
import com.crazylabs.jogobookingapp.Adapters.VenueAdapter;
import com.crazylabs.jogobookingapp.DataModels.CartDataModel;
import com.crazylabs.jogobookingapp.DataModels.SelectedSlotDataModel;
import com.crazylabs.jogobookingapp.DataModels.VenueDataModel;
import com.crazylabs.jogobookingapp.Utils.CartListener;
import com.crazylabs.jogobookingapp.Utils.ItemClickSupport;
import com.crazylabs.jogobookingapp.Utils.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private String TAG="CartActivityTag";
    private String url;
    private String userId,groundId,slot,bookingDate;

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

        payNowRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(CartActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to proceed to payment?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {

//                                userId=1
//                                groundId=2
//                                slot=12,13
//                                bookingDate=20171103,20171103

                                userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                groundId= String.valueOf(selectedSlotList.get(0).locationCode);
//                                slot = String.valueOf(selectedSlotList.get(0).time);
//                                bookingDate= String.valueOf(BookingIdFormat.format(selectedSlotList.get(0).fullDate));

                                Log.d(TAG, "userId: "+ userId);
                                Log.d(TAG, "groundId: "+groundId);
                                Log.d(TAG, "slot: "+slot);
                                Log.d(TAG, "bookingDate: "+bookingDate);

                                url="http://jogoapi-env.mbwc7vryaa.ap-south-1.elasticbeanstalk.com//Jogo/SetTempBooking?userId="+userId+"&groundId="+groundId+"&slot="+slot+"&bookingDate="+bookingDate;
                                volleyStringRequest(url);
                            }
                        }).create().show();
            }
        });

    }


    private List<CartDataModel> fillcartList() {

        final SimpleDateFormat BookingDateFormat = new SimpleDateFormat("yyyyMMdd");

        Iterator<SelectedSlotDataModel> iterator = selectedSlotList.iterator();
        StringBuilder slotBuilder = new StringBuilder();
        StringBuilder bookingDateBuilder = new StringBuilder();

        while(iterator.hasNext()) {
            SelectedSlotDataModel currentObject = iterator.next();

            Log.d("cartlistvalues", "fillcartList: "+currentObject.location+" "+currentObject.date+"-"+currentObject.month+"-"+currentObject.year+" "+currentObject.day+" "+currentObject.time+" "+currentObject.price+" "+currentObject.groundType);
            CartDataModel cartDataModel=new CartDataModel(currentObject.location,currentObject.date,currentObject.month,currentObject.year,currentObject.day,currentObject.time,currentObject.price,currentObject.groundType);
            cartDataModel.setFullDate(currentObject.fullDate);
            cartList.add(cartDataModel);
            totalCartPrice+=cartDataModel.getBooking_amount();



            bookingDateBuilder.append(BookingDateFormat.format(currentObject.fullDate)).append(',');
            slotBuilder.append(currentObject.time).append(',');
        }
        if (bookingDateBuilder.length()!=0){
            bookingDateBuilder.deleteCharAt(bookingDateBuilder.length()-1);
        }
        if (slotBuilder.length()!=0){
            slotBuilder.deleteCharAt(slotBuilder.length()-1);
        }
        bookingDate = bookingDateBuilder.toString();
        slot = slotBuilder.toString();

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


//    Volley methods

    public void volleyStringRequest(String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "volleyStringRequest onResponse: "+response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "volleyStringRequest Error: " + error.getMessage());
                Toast.makeText(CartActivity.this, "Payment failed, please make sure you are connected to the internet.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // Adding String request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void volleyJsonObjectRequest(String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,"volleyJsonObjectRequest "+ response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "volleyJsonObjectRequest Error: " + error.getMessage());
                Toast.makeText(CartActivity.this, "Payment failed, please make sure you are connected to the internet.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

    public void volleyJsonArrayRequest(String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyJsonArrayRequest";

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,"volleyJsonArrayRequest "+ response.toString());

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "volleyJsonArrayRequest Error: " + error.getMessage());
                Toast.makeText(CartActivity.this, "Payment failed, please make sure you are connected to the internet.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }
}