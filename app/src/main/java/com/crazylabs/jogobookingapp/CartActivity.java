package com.crazylabs.jogobookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crazylabs.jogobookingapp.Adapters.CartAdapter;
import com.crazylabs.jogobookingapp.Adapters.VenueAdapter;
import com.crazylabs.jogobookingapp.DataModels.CartDataModel;
import com.crazylabs.jogobookingapp.DataModels.VenueDataModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<CartDataModel> cartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        initViews();

        cartList = new ArrayList<>();
        adapter = new CartAdapter(getApplicationContext(), cartList);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llmp = new LinearLayoutManager(CartActivity.this);
        llmp.setOrientation(LinearLayoutManager.VERTICAL);
        llmp.setReverseLayout(true);
        llmp.setStackFromEnd(true);
        recyclerView.setLayoutManager(llmp);
        recyclerView.setAdapter(adapter);

    }

    public void initViews(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = (RecyclerView)findViewById(R.id.cart_recycler_view);

    }
}
