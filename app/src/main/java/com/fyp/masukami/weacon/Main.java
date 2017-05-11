package com.fyp.masukami.weacon;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.estimote.sdk.BeaconManager;
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
import java.util.List;

public class Main extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView nearbyStores;
    private AdapterShop shopAdapter;

    //Beacon
    private BeaconManager beaconManager;
    private static final String TAG = "MainActivity";
    private ProximityContentManager proximityContentManager;
    private String nearbies;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beaconManager = new BeaconManager(this);

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
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    if(beaconDetails.getBeaconName().equals("suhail-blueberry")){
                        text = "You are in" + "\nBlueberry Area";
                        background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.blueberrygradient);
                    }else if(beaconDetails.getBeaconName().equals("suhail-ice")){
                        text = "You are in" + "\nMarshmallow Area";
                        background = ContextCompat.getDrawable(getApplicationContext(), R.drawable.icegradient);
                    }else{
                        text = "You are in" + "\nMint Area";
                        background = ContextCompat.getDrawable(getApplication(), R.drawable.mintgradient);
                    }
                } else {
                    text = "You are\nOut of range";
                    background = ContextCompat.getDrawable(getApplication(), R.drawable.maingradient);
                }
                ((TextView) findViewById(R.id.tvBLEinRange)).setText(text);
                findViewById(R.id.upperLayout).setBackground(
                        background != null ? background : ContextCompat.getDrawable(getApplicationContext(), R.drawable.maingradient));

                new AsyncFetch().execute();
            }
        });
    }

    protected void onPause() {
        Log.d(TAG, "Stopping ProximityContentManager content updates");
        proximityContentManager.stopContentUpdates();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        beaconManager.disconnect();
        proximityContentManager.destroy();
        super.onDestroy();
    }

    protected void onResume() {
        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else {
            Log.d(TAG, "Starting ProximityContentManager content updates");
            proximityContentManager.startContentUpdates();
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

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
            try{
                url = new URL("http://192.168.1.176/plazawalk/getAdvertisers.php");
            } catch (MalformedURLException e){
                Log.d("URL Error", e.toString());
                return e.toString();
            }
            try{
                //Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
            } catch (IOException e){
                Log.d("IO Exception", e.toString());
                return e.toString();
            }
            try{
                int response_code = conn.getResponseCode();

                //Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK){

                    //Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }

                    //Pass data to onPostExecute
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e){
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
            List<Advertisers> advertisers = new ArrayList<>();

            try{
                JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                //Extract data from JSON and store into ArrayList as class object
                if(jsonArray.length() > 0){
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        Advertisers advertisersData = new Advertisers();
                        advertisersData.setLogo(jsonData.getString("logo_image"));
                        advertisersData.setName(jsonData.getString("name"));
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
                        advertisers.add(advertisersData);
                    }
                }else {
                    Log.d("JSON Size ", "JSON Array is 0");
                }

                //Setup and handover data to recyclerview
                nearbyStores = (RecyclerView)findViewById(R.id.advertisersList);
                shopAdapter = new AdapterShop(Main.this, advertisers);
                nearbyStores.setAdapter(shopAdapter);
                nearbyStores.setLayoutManager(new LinearLayoutManager(Main.this));
            } catch (JSONException e){
                Log.d("JSON Error", e.toString());
            }
        }

//                    Get image url and change to bitmap
//                    InputStream input = connection.getInputStream();
//                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
//                    return myBitmap;
    }
}
