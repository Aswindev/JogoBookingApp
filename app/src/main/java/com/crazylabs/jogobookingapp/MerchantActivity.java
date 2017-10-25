package com.crazylabs.jogobookingapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MerchantActivity extends AppCompatActivity implements PaymentResultListener {

    private String TAG="MerchantActivityTag";
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        try {
            amount = getIntent().getExtras().getInt("amount");
            Log.d(TAG, "onCreate: amount "+amount);
        }catch (Exception e){}

//        Preload checkout
        Checkout.preload(getApplicationContext());
        startPayment();
    }

    @Override
    public void onPaymentSuccess(String s) {

        Log.d(TAG, "onPaymentSuccess: "+s);
    }

    @Override
    public void onPaymentError(int i, String s) {

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
            options.put("name", "JOGO");

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
}
