package com.fyp.masukami.weacon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Suhail on 6/7/2017.
 */

public class ProductList extends AppCompatActivity {

    private ArrayList<String> productList = new ArrayList<>();
    private AdapterProduct productAdapter;
    private RecyclerView productView;
    private TextView tvProductAvailable;
    MyApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);
        app = (MyApplication) getApplication();
        Intent products = getIntent();
        productList = products.getStringArrayListExtra("productList");
        setFontFace();

        productView = (RecyclerView) findViewById(R.id.productList);
        productAdapter = new AdapterProduct(ProductList.this, productList);
        if(productAdapter != null){
            productView.setAdapter(productAdapter);
            productView.setLayoutManager(new LinearLayoutManager(ProductList.this));
            productView.addOnItemTouchListener(new CustomRVItemTouchListener(ProductList.this, productView, new RecyclerViewItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Intent productShops = new Intent(ProductList.this, ProductShops.class);
                    productShops.putExtra("product", productList.get(position));
                    startActivity(productShops);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }

    }

    private void setFontFace() {
        tvProductAvailable = (TextView)findViewById(R.id.tvProductAvailable);
        tvProductAvailable.setTypeface(app.BebasBold);
    }

}
