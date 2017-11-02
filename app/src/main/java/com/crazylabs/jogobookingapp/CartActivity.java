package com.crazylabs.jogobookingapp;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
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
import com.crazylabs.jogobookingapp.Utils.ArenaLocationClass;
import com.crazylabs.jogobookingapp.Utils.CartListener;
import com.crazylabs.jogobookingapp.Utils.ItemClickSupport;
import com.crazylabs.jogobookingapp.Utils.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;

import org.json.JSONArray;
import org.json.JSONException;
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
    private RelativeLayout payNowRelativeLayout, rootRelativeLayout;
    private String TAG="CartActivityTag";
    private String url;
    private String userId,groundId,slot,bookingDate,bookingIdString;
    private String slotTime,slotTimeString;
    private int tempSlotTime;
    private Boolean available=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        initViews();


//        Preload checkout
        Checkout.preload(getApplicationContext());

        userId=FirebaseAuth.getInstance().getCurrentUser().getUid();


        if (!selectedSlotList.isEmpty()){
            groundId= String.valueOf(selectedSlotList.get(0).locationCode);
            groundId = (groundId.length()==1 ? "0" : "") + groundId;
        }
//        slot = String.valueOf(selectedSlotList.get(0).time);
//        bookingDate= String.valueOf(BookingIdFormat.format(selectedSlotList.get(0).fullDate));


        cartList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmp = new LinearLayoutManager(CartActivity.this);
        llmp.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llmp);
        adapter = new CartAdapter(getApplicationContext(), fillcartList(), CartActivity.this);
        recyclerView.setAdapter(adapter);

        Animation animTranslation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.horizontal_translation_animation);
        arrowImageView.startAnimation(animTranslation);

        payNowRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalCartPrice==0) {

                    Snackbar snackbar = Snackbar
                            .make(rootRelativeLayout, "Please go back and select a slot", Snackbar.LENGTH_LONG);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();

                }else {

//                    /Jogo/GetBookingAvailability?groundId=0&bookingDate=20171028,20171028&slot=1,9
                    url= ArenaLocationClass.domain+"/Jogo/GetBookingAvailability?groundId="+groundId+"&bookingDate="+bookingDate+"&slot="+slot;
                    volleyJsonObjectRequest(url);

                }
            }
        });

    }

    private void OpenDialogStartPayment() {
        new AlertDialog.Builder(CartActivity.this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to proceed to payment?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

//                                    loadingScreenImageView.setVisibility(View.VISIBLE);
                        ProgressDialog progress = new ProgressDialog(CartActivity.this);
                        progress.setTitle("Loading");
                        progress.setMessage("Please wait ...");
                        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                        progress.show();
// To dismiss the dialog
//                                    progress.dismiss();
//                                userId=1
//                                groundId=2
//                                slot=12,13
//                                bookingDate=20171103,20171103


//                                Log.d(TAG, "userId: "+ userId);
//                                Log.d(TAG, "groundId: "+groundId);
//                                Log.d(TAG, "slot: "+slot);
//                                Log.d(TAG, "bookingDate: "+bookingDate);

                        url= ArenaLocationClass.domain+"/Jogo/SetTempBooking?userId="+userId+"&groundId="+groundId+"&slot="+slot+"&bookingDate="+bookingDate;
                        volleyStringRequest(url);

                        Intent intent = new Intent(getApplicationContext(), MerchantActivity.class);
//                                    For Capture
                        intent.putExtra("amount",totalCartPrice);
                        intent.putExtra("bookingId",bookingIdString);

//                                    for Permenant Booking
                        intent.putExtra("groundId",groundId);
                        intent.putExtra("slot",slot);
                        intent.putExtra("bookingDate",bookingDate);

                        cartList.clear();
                        selectedSlotList.clear();
                        RefreshTotalCartPrice();

                        startActivity(intent);

                    }
                }).create().show();

    }


    private List<CartDataModel> fillcartList() {

        final SimpleDateFormat BookingDateFormat = new SimpleDateFormat("yyyyMMdd");

        Iterator<SelectedSlotDataModel> iterator = selectedSlotList.iterator();
        StringBuilder slotBuilder = new StringBuilder();
        StringBuilder bookingDateBuilder = new StringBuilder();
        StringBuilder bookingIdBuilder = new StringBuilder();

        while(iterator.hasNext()) {
            SelectedSlotDataModel currentObject = iterator.next();

            Log.d("cartlistvalues", "fillcartList: "+currentObject.location+" "+currentObject.date+"-"+currentObject.month+"-"+currentObject.year+" "+currentObject.day+" "+currentObject.time+" "+currentObject.price+" "+currentObject.groundType);
            CartDataModel cartDataModel=new CartDataModel(currentObject.location,currentObject.date,currentObject.month,currentObject.year,currentObject.day,currentObject.time,currentObject.price,currentObject.groundType);
            cartDataModel.setFullDate(currentObject.fullDate);
            cartList.add(cartDataModel);
            totalCartPrice+=cartDataModel.getBooking_amount();


            tempSlotTime=currentObject.time;
            slotTime = (tempSlotTime<10 ? "0" : "") + tempSlotTime;
            bookingIdBuilder.append(BookingDateFormat.format(currentObject.fullDate)).append(groundId).append(slotTime).append(',');
            bookingDateBuilder.append(BookingDateFormat.format(currentObject.fullDate)).append(',');
            slotBuilder.append(currentObject.time).append(',');
        }

        if (bookingDateBuilder.length()!=0){
            bookingDateBuilder.deleteCharAt(bookingDateBuilder.length()-1);
        }
        if (slotBuilder.length()!=0){
            slotBuilder.deleteCharAt(slotBuilder.length()-1);
        }
        if (bookingIdBuilder.length()!=0){
            bookingIdBuilder.deleteCharAt(bookingIdBuilder.length()-1);
        }
        bookingDate = bookingDateBuilder.toString();
        slot = slotBuilder.toString();
        bookingIdString = bookingIdBuilder.toString();

        Log.d(TAG, "fillcartListStrings: "+bookingIdString+" "+slot);
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
        Log.d("cartlistvalues", "fillcartList: "+bookingIdString);
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
        rootRelativeLayout= (RelativeLayout) findViewById(R.id.cart_activity_root_relative_layout);
    }

    @Override
    public void itemDeleted() {
//        Toast.makeText(getApplicationContext(), "Listener activated", Toast.LENGTH_SHORT).show();
        RefreshTotalCartPrice();
        RefreshStringValues();
    }

    private void RefreshStringValues() {
        Iterator<CartDataModel> iterator = cartList.iterator();
        StringBuilder slotBuilder = new StringBuilder();
        StringBuilder bookingDateBuilder = new StringBuilder();
        StringBuilder bookingIdBuilder = new StringBuilder();

        final SimpleDateFormat BookingDateFormat = new SimpleDateFormat("yyyyMMdd");

        while(iterator.hasNext()) {
            CartDataModel currentObject = iterator.next();

            tempSlotTime=currentObject.getBooking_time();
            slotTime = (tempSlotTime<10 ? "0" : "") + tempSlotTime;
            bookingIdBuilder.append(BookingDateFormat.format(currentObject.getFullDate())).append(groundId).append(slotTime).append(',');
            bookingDateBuilder.append(BookingDateFormat.format(currentObject.getFullDate())).append(',');
            slotBuilder.append(currentObject.getBooking_time()).append(',');
            Log.d(TAG, "fillcartList: "+currentObject.getBooking_time());
        }



        if (bookingDateBuilder.length()!=0){
            bookingDateBuilder.deleteCharAt(bookingDateBuilder.length()-1);
        }
        if (slotBuilder.length()!=0){
            slotBuilder.deleteCharAt(slotBuilder.length()-1);
        }
        if (bookingIdBuilder.length()!=0){
            bookingIdBuilder.deleteCharAt(bookingIdBuilder.length()-1);
        }
        bookingDate = bookingDateBuilder.toString();
        slot = slotBuilder.toString();
        bookingIdString = bookingIdBuilder.toString();
        Log.d(TAG, "fillcartListRefreshedStrings: "+bookingIdString+" "+slot);
    }


//    Volley methods

    public void volleyStringRequest(String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyStringRequest";

        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("merchantactivitytag", "volleyStringRequest onResponse: "+response);
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

    public void volleyJsonObjectRequest(final String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: "+url);
                        Log.d(TAG,"volleyJsonObjectRequest "+ response.toString());

                        try {
                            JSONArray resultArray = response.getJSONArray("data");
                            for(int i = 0; i < resultArray.length(); i++) {
                                JSONObject obj = resultArray.getJSONObject(i);
//                                Log.d(TAG,"volleyJsonObjectRequest "+ obj.toString());

                                //store your variable
//                                bookingId = (String) obj.get("bookingId");
//                                groundId = (String) obj.get("groundId");
                                String status = (String) obj.get("status");
                                if (status.equals("Available")) {

                                }else {
                                    available=false;
                                }
                                Log.d(TAG,"volleyJsonObjectRequest "+ status);
//                                Log.d(TAG,"volleyJsonObjectRequest "+ bookingId.substring(bookingId.length()-2));
//                                Log.d(TAG,"volleyJsonObjectRequest "+ bookingId.substring(0,8));
//                                String substr = word.substring(word.length() - 3);
//                                tempSlotNumber=Integer.parseInt(bookingId.substring(bookingId.length()-2));
//                                Log.d(TAG,"volleyJsonObjectRequest TempSlotNumber "+ tempSlotNumber);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (available){
                            OpenDialogStartPayment();
                        } else {
                            Toast.makeText(CartActivity.this, "One or more slots in your cart has just been booked by someone else.",
                                    Toast.LENGTH_SHORT).show();
                        }

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