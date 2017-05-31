package com.fyp.masukami.weacon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.fyp.masukami.weacon.estimote.BeaconID;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetails;
import com.fyp.masukami.weacon.estimote.EstimoteCloudBeaconDetailsFactory;
import com.fyp.masukami.weacon.estimote.ProximityContentManager;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Main extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView nearbyStores, relatedStores;
    private AdapterShop shopAdapter, relatedShopAdapter;
    private TextView emptyList, bleInRange, nearStores, interestStores;
    private LinearLayout relatedStoresLayout;
    MyApplication app;

    //Beacon
    private BeaconManager beaconManager;
    private Region region;
    private static final String TAG = "MainActivity";
    private ProximityContentManager proximityContentManager;
    private List<String> nearbies = Collections.emptyList();
    private static final Map<String, List<String>> PLACES_BY_BEACONS;

    static {
        Map<String, List<String>> placesByBeacons = new HashMap<>();
        placesByBeacons.put("63797:8827", new ArrayList<String>() {{
            add("Auntie Anne's Pretzels");
            add("Caring Pharmacy");
            add("Nando's");
        }});
        placesByBeacons.put("41073:32690", new ArrayList<String>() {{
            add("Auntie Anne's Pretzels");
            add("Baskin Robbins");
            add("The Body Shop");
        }});
        //add more with more iBeacons
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private List<String> placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        if (PLACES_BY_BEACONS.containsKey(beaconKey))
            return PLACES_BY_BEACONS.get(beaconKey);
        return Collections.emptyList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = new BeaconManager(this);
        app = (MyApplication) getApplication();
        nearbyStores = (RecyclerView) findViewById(R.id.advertisersList);
        relatedStores = (RecyclerView) findViewById(R.id.relatedList);
        relatedStoresLayout = (LinearLayout)findViewById(R.id.lowerLayout);
        emptyList = (TextView) findViewById(R.id.tvEmptyList);
        setFontFace();

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 63797, 8827),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 3878, 23708),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 41073, 32690)),
                new EstimoteCloudBeaconDetailsFactory());

        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                String text = "";
                Drawable background;
                if (content != null) {
                    nearbyStores.setVisibility(View.VISIBLE);
                    relatedStoresLayout.setVisibility(View.VISIBLE);
                    emptyList.setVisibility(View.GONE);
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if (beaconDetails.getBeaconName().equals("suhail-blueberry")) {
                        text = "You are in" + "\nBlueberry Area";
                        //background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.blueberrygradient);
                    } else if (beaconDetails.getBeaconName().equals("suhail-ice")) {
                        text = "You are in" + "\nMarshmallow Area";
                        //background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icegradient);
                    } else {
                        text = "You are in" + "\nMint Area";
                        //background = ContextCompat.getDrawable(getApplication(), R.drawable.mintgradient);
                    }
                } else {
                    text = "You are\nOut of range";
                    nearbyStores.setVisibility(View.GONE);
                    relatedStoresLayout.setVisibility(View.GONE);
                    emptyList.setVisibility(View.VISIBLE);
                    //background = ContextCompat.getDrawable(getApplication(), R.drawable.maingradient);
                }
                ((TextView) findViewById(R.id.tvBLEinRange)).setText(text);
//                findViewById(R.id.upperLayout).setBackground(
//                        background != null ? background : ContextCompat.getDrawable(getApplicationContext(), R.drawable.maingradient));

            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            ;

            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    List<String> places = placesNearBeacon(nearestBeacon);
                    if (nearbies.isEmpty() || !nearbies.equals(places)) {
                        nearbies = places;
                        new AsyncFetch().execute();
                    }
                }
            }
        });

        region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
    }

    private void setFontFace() {
        bleInRange = (TextView) findViewById(R.id.tvBLEinRange);
        bleInRange.setTypeface(app.BebasBold);
        nearStores = (TextView) findViewById(R.id.tvnearStores);
        nearStores.setTypeface(app.BebasRegular);
        emptyList.setTypeface(app.BebasBook);
        interestStores = (TextView) findViewById(R.id.tvstoreInterest);
        interestStores.setTypeface(app.BebasRegular);
    }

    protected void onPause() {
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
        beaconManager.stopRanging(region);
        app.startMonitoring();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();
        proximityContentManager.destroy();
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        app = (MyApplication) getApplication();
        app.stopMonitoring();
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    public class AsyncFetch extends AsyncTask<String, String, String> {

        ProgressDialog pdLoading = new ProgressDialog(Main.this);
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
            final List<Advertisers> relatedAdvertisers = new ArrayList<>();
            final ArrayList<String> relatedProduct = new ArrayList<>();
            final List<Advertisers> relatedReff = new ArrayList<>();

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

                        for (int j = 0; j < nearbies.size(); j++) {
                            if (jsonName.equals(nearbies.get(j))) {
                                advertisers.add(advertisersData);
                                relatedProduct.add(advertisersData.getProductName());
                            }
                        }
                        relatedReff.add(advertisersData);
                    }

                    for(int k = 0; k < relatedReff.size(); k++) {
                        String currentProductName = relatedReff.get(k).getProductName();
                        String currentProduct = relatedReff.get(k).getName();
                        if(!nearbies.contains(currentProduct)){
                            if (currentProductName.equals("Dessert") || currentProductName.equals("Bakery") || currentProductName.equals("Restaurant")) {
                                if (relatedProduct.contains("Dessert") || relatedProduct.contains("Bakery") || relatedProduct.contains("Restaurant"))
                                    relatedAdvertisers.add(relatedReff.get(k));
                            } else if (currentProductName.equals("Clothing") || currentProductName.equals("Leather Goods")) {
                                if (relatedProduct.contains("Clothing") || relatedProduct.contains("Leather Goods"))
                                    relatedAdvertisers.add(relatedReff.get(k));
                            } else if (currentProductName.equals("Health Suppliments") || currentProductName.equals("Convenience Shop") || currentProductName.equals("Cosmetics")) {
                                if (relatedProduct.contains("Health Suppliments") || relatedProduct.contains("Convenience Shop") || relatedProduct.contains("Cosmetics"))
                                    relatedAdvertisers.add(relatedReff.get(k));
                            }
                        }
                    }
                } else {
                    Log.d("JSON Size ", "JSON Array is 0");
                }

                //Setup and handover data to recyclerview
                shopAdapter = new AdapterShop(Main.this, advertisers);
                if(shopAdapter != null){
                    nearbyStores.setAdapter(shopAdapter);
                    nearbyStores.setLayoutManager(new LinearLayoutManager(Main.this));
                    nearbyStores.addOnItemTouchListener(new CustomRVItemTouchListener(Main.this, nearbyStores, new RecyclerViewItemClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Intent shopDetail = new Intent(Main.this, AdvertiserDetails.class);
                            shopDetail.putExtra("advertiser", advertisers.get(position));
                            startActivity(shopDetail);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));
                }

                relatedShopAdapter = new AdapterShop(Main.this, relatedAdvertisers);
                if(relatedShopAdapter != null){
                    relatedStores.setAdapter(relatedShopAdapter);
                    relatedStoresLayout.setVisibility(View.VISIBLE);
                    relatedStores.setLayoutManager(new LinearLayoutManager(Main.this));
                    relatedStores.addOnItemTouchListener(new CustomRVItemTouchListener(Main.this, relatedStores, new RecyclerViewItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent shopDetail = new Intent(Main.this, AdvertiserDetails.class);
                        shopDetail.putExtra("advertiser", relatedAdvertisers.get(position));
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
//                    Get image url and change to bitmap
//                    InputStream input = connection.getInputStream();
//                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                    return myBitmap;
    }
}
