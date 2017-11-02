package com.crazylabs.jogobookingapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.crazylabs.jogobookingapp.Utils.ArenaLocationClass;
import com.crazylabs.jogobookingapp.Utils.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MerchantActivity extends AppCompatActivity implements PaymentResultListener {

    private String TAG="MerchantActivityTag";
    private int amount;
    private ImageView homeImageView,arrowImageView;
    private Animation animTranslation;
    private LinearLayout successLinearLayout, failureLinearLayout, cancelledLinearLayout;
    private String uid;
    private String bookingId,groundId,bookingDate,slot;
    private String permanentBookingUrl;
    private Animation animeScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        InitViews();
        try {
            amount = getIntent().getExtras().getInt("amount");
            bookingId=getIntent().getExtras().getString("bookingId");
            groundId=getIntent().getExtras().getString("groundId");
            bookingDate=getIntent().getExtras().getString("bookingDate");
            slot=getIntent().getExtras().getString("slot");

            Log.d(TAG, "onCreate: amount "+amount+" "+bookingId);
        }catch (Exception e){}

        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        animTranslation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.vertical_translation_animation);

        animeScale = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale_animation);
        startPayment();

        homeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(k);
            }
        });
    }

    @Override

    public void onBackPressed() {
        Intent k = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(k);
    }

    private void InitViews() {
        homeImageView= (ImageView) findViewById(R.id.activity_merchant_return_home_imageview);
        arrowImageView= (ImageView) findViewById(R.id.activity_merchant_arrow_imageview);
        successLinearLayout= (LinearLayout) findViewById(R.id.activity_merchant_success_text_linearlayout);
        failureLinearLayout= (LinearLayout) findViewById(R.id.activity_merchant_failure_text_linearlayout);
        cancelledLinearLayout= (LinearLayout) findViewById(R.id.activity_merchant_cancelled_text_linearlayout);
    }

    @Override
    public void onPaymentSuccess(String s) {

        cancelledLinearLayout.setVisibility(View.GONE);
        failureLinearLayout.setVisibility(View.GONE);
        successLinearLayout.setVisibility(View.VISIBLE);
        arrowImageView.startAnimation(animTranslation);
        homeImageView.startAnimation(animeScale);

        int amountInPaise=100*amount;
        String url=ArenaLocationClass.domain+"/Jogo/CapturePayment?bookingId="+bookingId+"&userId="+uid+"&paymentId="+s+"&amount="+amountInPaise;
        Log.d(TAG, "onPaymentSuccess: "+s);
        Log.d(TAG, "onPaymentSuccess: capture url "+url);

        volleyJsonObjectRequestCapture(url);
    }

    @Override
    public void onPaymentError(int i, String s) {

        if (s.equals("Payment Cancelled")) {
            successLinearLayout.setVisibility(View.GONE);
            failureLinearLayout.setVisibility(View.GONE);
            cancelledLinearLayout.setVisibility(View.VISIBLE);
        }else{
            cancelledLinearLayout.setVisibility(View.GONE);
            successLinearLayout.setVisibility(View.GONE);
            failureLinearLayout.setVisibility(View.VISIBLE);
        }
        arrowImageView.startAnimation(animTranslation);
        homeImageView.startAnimation(animeScale);

        Log.d(TAG, "onPaymentError: "+s);
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.jogo_square_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", "Jogo Football LLP");

            /**
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Order #123456");

            options.put("currency", "INR");

            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", amount*100);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    public void volleyJsonObjectRequestCapture(String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        permanentBookingUrl= ArenaLocationClass.domain+"/Jogo/SetBooking?userId="+uid+"&groundId="+groundId+"&slot="+slot+"&bookingDate="+bookingDate;

                        Log.d(TAG, "onPaymentSuccess: PermaBooking url "+permanentBookingUrl);
                        volleyJsonObjectRequestPermaBooking(permanentBookingUrl);

                        Log.d(TAG,"volleyJsonObjectRequestForCapture "+ response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "volleyJsonObjectRequestForCapture Error: " + error.getMessage());
                Toast.makeText(MerchantActivity.this, "Payment failed, please make sure you are connected to the internet.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

    public void volleyJsonObjectRequestPermaBooking(String url){

        String  REQUEST_TAG = "com.crazylabs.jogobookingapp.volleyJsonObjectRequest";

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG,"volleyJsonObjectRequestForPermaBooking "+ response.toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "volleyJsonObjectRequestForCapture Error: " + error.getMessage());
                Toast.makeText(MerchantActivity.this, "Payment failed, please make sure you are connected to the internet.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Adding JsonObject request to request queue
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,REQUEST_TAG);
    }

}
