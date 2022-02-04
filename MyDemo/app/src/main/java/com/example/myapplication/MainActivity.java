package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.BatteryManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress_bar, battery_bar;
    private RecyclerView recyclerView;
    private TextView batteryLevelTxtView;
    ArrayList<Country> countries;
    CountryRecViewAdaptor adaptor;
    int amountOfRequests;
    String[] urlArray = {"http://weather.bfsah.com/beijing", "http://weather.bfsah.com/cardiff", "http://weather.bfsah.com/edinburgh", "http://weather.bfsah.com/london"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        battery_bar = (ProgressBar) findViewById(R.id.batteryBar);
        batteryLevelTxtView = findViewById(R.id.batteryTxtView);
        progress_bar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.my_recycler_view);
        countries = new ArrayList<>();
        adaptor = new CountryRecViewAdaptor(this);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        amountOfRequests = urlArray.length;

        for (String url : urlArray) {

            getCountry(url);

        }
    }

    void getCountry(String url) {
        Log.i("TAG", "getCountry");
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
                            Log.i("TAG", "parsed country " + country);
                            countries.add(new Country(country, city, temp, description));
                            amountOfRequests--;
                            if (amountOfRequests == 0) {
                                adaptor.setCountries(countries);
                                progress_bar.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            //Do something with error
                            Log.i("TAG", "jsonexception thrown " + e);
                            amountOfRequests--;
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.i("TAG", error.toString());
                        amountOfRequests--;
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


    public void showAppLauncher(View view) {
        Intent intent = new Intent(this, AppLauncher.class);
        startActivity(intent);
    }


    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context c, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryLevelTxtView.setText(level + "%");
            battery_bar.setProgress(level);
        }

    };
}

