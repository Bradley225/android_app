package com.example.myapplication;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * An Activity providing a screen containing a RecyclerView displaying Applications that are installed on the device
 */
public class AppLauncher extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArrayList<ResolveInfo> mApps = new ArrayList<>();
    private AppRecViewAdaptor mAdaptor;
    private GridLayoutManager mLayoutManager;

    /**
     * {@inheritDoc}
     * Initialises the UI including RecyclerView and its adaptor and loads and displays Applications installed on the device into the RecyclerView
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_launcher);
        mRecyclerView = findViewById(R.id.apps_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdaptor = new AppRecViewAdaptor(this);
        mRecyclerView.setAdapter(mAdaptor);
        loadInstalledApplications();
        mAdaptor.setApps(mApps);
    }

    /**
     * Calls queryIntentActivities to get a list of ResolveInfo for each Application installed on device and sets returned List to mApps ArrayList
     */
    private void loadInstalledApplications() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mApps = (ArrayList)getPackageManager().queryIntentActivities(mainIntent, 0);

    }


}