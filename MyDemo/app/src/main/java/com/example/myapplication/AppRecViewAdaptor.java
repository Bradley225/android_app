package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class AppRecViewAdaptor extends RecyclerView.Adapter<AppRecViewAdaptor.ViewHolder> {

    private ArrayList<ResolveInfo> apps = new ArrayList<>();
    private Context context;
    int selected_position = 0;


    public AppRecViewAdaptor(Context aContext)
    {
        context = aContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apps_view_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResolveInfo info = apps.get(position);
        holder.imageView.setImageDrawable(info.loadIcon(context.getPackageManager()));
        holder.txtName.setText(info.loadLabel(context.getPackageManager()));
        holder.parent.setBackgroundColor(selected_position == holder.getAdapterPosition() ? Color.GRAY : Color.WHITE);
        holder.txtName.setTextColor(selected_position == holder.getAdapterPosition() ? Color.BLACK : Color.MAGENTA);


    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void setApps(ArrayList<ResolveInfo> apps) {
        this.apps = apps;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtName;
        private ImageView imageView;
        private RelativeLayout parent;

        public void startNewActivity(String packageName) {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
        public ViewHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition() == RecyclerView.NO_POSITION) return;

                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);
                    startNewActivity(apps.get(selected_position).resolvePackageName);
                }
            });
            txtName = itemView.findViewById(R.id.app_name);
            imageView = itemView.findViewById(R.id.app_icon);
            parent = itemView.findViewById(R.id.row_parent);


        }
    }
}
