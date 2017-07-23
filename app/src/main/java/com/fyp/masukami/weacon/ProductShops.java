package com.fyp.masukami.weacon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suhail on 6/10/2017.
 */

public class ProductShops extends AppCompatActivity {

    MyApplication app;
    private String product;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private TextView productName;
    private RecyclerView shopList;
    private AdapterShop shopAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_shops);
        product = getIntent().getStringExtra("product");
        Log.d("ProductShops", product);
        app = (MyApplication) getApplication();
        productName = (TextView) findViewById(R.id.tvProductName);
        productName.setText(product);
        productName.setTypeface(app.BebasBold);
        shopList = (RecyclerView) findViewById(R.id.productShops);
        new AsyncFetch().execute();
    }

    public class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(ProductShops.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be on UI thread
            pdLoading.setMessage("Loading Advertisers");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(app.ipAddress + "plazawalk/getAdvertisers.php");
            } catch (MalformedURLException e) {
                Log.d("URL Error", e.toString());
                return e.toString();
            }
            try {
                //Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
            } catch (IOException e) {
                Log.d("IO Exception", e.toString());
                return e.toString();
            }
            try {
                int response_code = conn.getResponseCode();

                //Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    //Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    //Pass data to onPostExecute
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                Log.d("IO Exception", e.toString());
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(result);
            //This method will be running on UI thread
            pdLoading.dismiss();
            final List<Advertisers> advertisers = new ArrayList<>();

            String jsonName;

            try {
                JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                //Extract data from JSON and store into ArrayList as class object
                if (jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        Advertisers advertisersData = new Advertisers();
                        advertisersData.setLogo(jsonData.getString("logo_image"));
                        advertisersData.setName(jsonData.getString("name"));
                        jsonName = jsonData.getString("name");
                        advertisersData.setProductName(jsonData.getString("product_name"));
                        advertisersData.setAddress(jsonData.getString("address"));
                        advertisersData.setDescription(jsonData.getString("description"));
                        advertisersData.pathwayImage[0] = jsonData.getString("pathway_image1");
                        advertisersData.pathwayImage[1] = jsonData.getString("pathway_image2");
                        advertisersData.pathwayImage[2] = jsonData.getString("pathway_image3");
                        advertisersData.pathwayImage[3] = jsonData.getString("pathway_image4");
                        advertisersData.pathwayImage[4] = jsonData.getString("pathway_image5");
                        advertisersData.pathwayImage[5] = jsonData.getString("pathway_image6");
                        advertisersData.pathwayImage[6] = jsonData.getString("pathway_image7");
                        advertisersData.pathwayImage[7] = jsonData.getString("pathway_image8");
                        advertisersData.pathwayImage[8] = jsonData.getString("pathway_image9");
                        advertisersData.pathwayImage[9] = jsonData.getString("pathway_image10");
                        advertisersData.pathwayImage[10] = jsonData.getString("pathway_image11");
                        advertisersData.pathwayImage[11] = jsonData.getString("pathway_image12");
                        advertisersData.setPromotion(jsonData.getString("promotion_image"));

                        if (advertisersData.getProductName().equals(product))
                            advertisers.add(advertisersData);
                    }
                } else {
                    Log.d("JSON Size ", "JSON Array is 0");
                }
                //Setup and handover data to recyclerview
                shopAdapter = new AdapterShop(ProductShops.this, advertisers);
                if(shopAdapter != null){
                    shopList.setAdapter(shopAdapter);
                    shopList.setLayoutManager(new LinearLayoutManager(ProductShops.this));
                    shopList.addOnItemTouchListener(new CustomRVItemTouchListener(ProductShops.this, shopList, new RecyclerViewItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent shopDetail = new Intent(ProductShops.this, AdvertiserDetails.class);
                            shopDetail.putExtra("advertiser", advertisers.get(position));
                            startActivity(shopDetail);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

            } catch (JSONException e) {
                Log.d("JSON Error", e.toString());
            }
        }
    }
}
