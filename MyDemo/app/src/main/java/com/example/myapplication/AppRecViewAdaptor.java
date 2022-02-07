package com.example.myapplication;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Adaptor for a RecyclerView and will show icons and names of Applications installed on the device in its RecyclerView
 */
public class AppRecViewAdaptor extends RecyclerView.Adapter<AppRecViewAdaptor.ViewHolder> {

    private ArrayList<ResolveInfo> mApps = new ArrayList<>();
    private Context mContext;

    /**
     * Constructor for class AppRecViewAdaptor
     *
     * @param aContext The Context Object to be set to mContext
     */
    public AppRecViewAdaptor(Context aContext) {
        mContext = aContext;
    }

    /**
     * {@inheritDoc}
     * Initialises the UI and returns a ViewHolder object
     */
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_view_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * {@inheritDoc}
     * Gets the ResolveInfo object of an Application installed on the device from mApps ArrayList stored at the value of the position parameter
     * and calls Glide.with to load the icon of the ResolveInfo object into its ViewHolders ImageView and also sets its name to ViewHolders TextView
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResolveInfo info = mApps.get(position);
        Glide.with(mContext)
                .load(info.loadIcon(mContext.getPackageManager()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mAppIcon);
        holder.mTxtName.setText(info.loadLabel(mContext.getPackageManager()));
    }

    /**
     * {@inheritDoc}
     * Provides the amount of ResolveInfo objects stored in the mApps ArrayList by returning its size
     */
    @Override
    public int getItemCount() {
        return mApps.size();
    }

    /**
     * Sets installedApplications argument to local ArrayList mApps and calls notifyDataSetChanged to refresh RecyclerView
     *
     * @param installedApplications to be set to mApps and contains list of ResolveInfo Objects for each Application installed on device
     */
    public void setApps(ArrayList<ResolveInfo> installedApplications) {
        this.mApps = installedApplications;
        notifyDataSetChanged();
    }

    /**
     * This class describes an installed Application stored and displayed in a RecyclerView
     * and contains metadata about its position in the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTxtName;
        private ImageView mAppIcon;

        /**
         * Constructor for ViewHolder which initialises its TextView and ImageView from the information stored in itemView parameter
         *
         * @param itemView a View object for a itemView in a RecyclerView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {

                /**
                 * Triggered when the View has been selected by the user and opens an Application on the device by calling openApp
                 * passing the information stored in its TextVew and also the packageName of the Application
                 *
                 * @param view a View object for a itemView in a RecyclerView
                 */
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    int selectedPosition = getAdapterPosition();
                    openApp(mTxtName.getText().toString(), mApps.get(selectedPosition).activityInfo.packageName);
                }
            });

            mTxtName = itemView.findViewById(R.id.app_name);
            mAppIcon = itemView.findViewById(R.id.app_icon);
        }

        /**
         * Calls isAppInstalled and isAppEnabled to see if Application pertaining to packageName argument is installed and enabled on device
         * if it is installed and enabled it opens it by calling startActivity using local mContext object
         * if it isn't a Toast message informing the user of this will be shown
         *
         * @param appName name of the application to open which will be used if is not installed or enabled and need to inform user in a Toast message
         * @param packageName package name of the application to open
         */
        public void openApp(String appName, String packageName) {
            if (isAppInstalled(packageName)) {
                if (isAppEnabled(packageName)) {
                    mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage(packageName));
                } else {
                    Toast.makeText(mContext, appName + " app is not enabled.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(mContext, appName + " app is not installed.", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * Returns true if Application corresponding to packageName String argument is installed on device otherwise returns false
         *
         * @param packageName name of package to be used when calling getPackageInfo to check if application is installed on device
         */
        private boolean isAppInstalled(String packageName) {
            PackageManager pm = mContext.getPackageManager();
            try {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
            return false;
        }

        /**
         * Returns true if Application corresponding to packageName is enabled on device otherwise returns false
         *
         * @param packageName name of package to be used when calling getApplicationInfo to check if application is enabled on device
         */
        private boolean isAppEnabled(String packageName) {
            boolean appStatus = false;
            try {
                ApplicationInfo ai = mContext.getPackageManager().getApplicationInfo(packageName, 0);
                if (ai != null) {
                    appStatus = ai.enabled;
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return appStatus;
        }

    }
}
