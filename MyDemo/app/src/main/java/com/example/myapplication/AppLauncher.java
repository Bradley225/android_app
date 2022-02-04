package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AppLauncher extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView batteryLevelTxtView;
    ArrayList<ResolveInfo> apps = new ArrayList<>();
    AppRecViewAdaptor adaptor;
    GridLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_launcher);
        recyclerView = findViewById(R.id.apps_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(mLayoutManager);
        adaptor = new AppRecViewAdaptor(this);
        recyclerView.setAdapter(adaptor);
        loadApps();
        adaptor.setApps(apps);
    }

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
        for (ResolveInfo app : mApps) {
            apps.add(app);
        }
    }


}