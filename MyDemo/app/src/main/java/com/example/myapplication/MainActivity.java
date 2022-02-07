package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.os.BatteryManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main Activity of the Application and will display
 * a clock in a textView, a ProgressBar related to the devices battery level, a
 * RecyclerView containing Country objects and also a button to display AppLauncher class
 */
public class MainActivity extends AppCompatActivity {

    private ProgressBar mLoadingProgressBar;
    private ProgressBar mBatteryBar;
    private RecyclerView mRecyclerView;
    private TextView mBatteryLevelTxtView;
    private ArrayList<Country> mCountries;
    private CountryRecViewAdaptor mAdaptor;
    private int mAmountRequests;
    private String[] mCountriesURL = {"http://weather.bfsah.com/beijing", "http://weather.bfsah.com/cardiff", "http://weather.bfsah.com/edinburgh", "http://weather.bfsah.com/london"};


    /**
     * {@inheritDoc}
     * Initialises the UI and also makes a request to a REST API for String contained in mCountriesURL by calling getCountry
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        mBatteryBar = findViewById(R.id.batteryBar);
        mBatteryLevelTxtView = findViewById(R.id.batteryTxtView);
        mLoadingProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mCountries = new ArrayList<>();
        mAdaptor = new CountryRecViewAdaptor(this);
        mRecyclerView.setAdapter(mAdaptor);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAmountRequests = mCountriesURL.length;

        for (int i = 0; i < mCountriesURL.length; i++) {
            getCountry(mCountriesURL[i]);
        }
    }

    /**
     * Gets a Country Object by creating a JsonObjectRequest with the url String parameter and calling addToRequestQueue
     * on the AppController static instance passing the created JsonObjectRequest as an parameter to be loaded from the RequestQueue
     *
     * @param url contains address to a Rest API which responds with a JSON object to be parsed as a Country Object
     */
    public void getCountry(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject countryObject) {
                        String country, city, temp, description;

                        try {
                            country = countryObject.getString("country");
                            city = countryObject.getString("city");
                            temp = countryObject.getString("temperature") + "\u00B0";
                            description = countryObject.getString("description");
                            mCountries.add(new Country(country, city, temp, description));
                            mAmountRequests--;
                            if (mAmountRequests == 0) {
                                mAdaptor.setCountries(mCountries);
                                mLoadingProgressBar.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            mAmountRequests--;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mAmountRequests--;
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Creates a new Intent for AppLauncher class and calls StartActivity to start its Activity
     *
     * @param view is a button which was pressed to call the function
     */
    public void showAppLauncher(View view) {
        Intent intent = new Intent(this, AppLauncher.class);
        startActivity(intent);
    }

    /**
     * Receives Events related to Device Battery and updates batteryLevelTxtView and battery_bar accordingly
     */
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            mBatteryLevelTxtView.setText(level + "%");
            mBatteryBar.setProgress(level);
        }

    };
}

