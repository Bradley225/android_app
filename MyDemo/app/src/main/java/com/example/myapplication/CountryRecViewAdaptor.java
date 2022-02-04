package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
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

public class CountryRecViewAdaptor extends RecyclerView.Adapter<CountryRecViewAdaptor.ViewHolder> {

    private ArrayList<Country> countries = new ArrayList<>();
    private Context context;
    int selected_position = 0;
    public CountryRecViewAdaptor(Context aContext)
    {
        context = aContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCountry.setText(countries.get(position).getCountry());
        holder.txtCity.setText(countries.get(position).getCity());
        holder.txtTemp.setText(countries.get(position).getTemp());
        holder.txtDescription.setText(countries.get(position).getDescription());
        holder.parent.setBackgroundColor(selected_position == holder.getAdapterPosition() ? Color.GRAY : Color.WHITE);
        holder.txtCountry.setTextColor(selected_position == holder.getAdapterPosition() ? Color.BLACK : Color.MAGENTA);
        holder.txtCity.setTextColor(selected_position == holder.getAdapterPosition() ? Color.BLACK : Color.MAGENTA);


    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public void setCountries(ArrayList<Country> contacts) {
        this.countries = contacts;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView txtCountry;
        private TextView txtCity;
        private TextView txtTemp;
        private TextView txtDescription;
        private ImageView imageView;
        private RelativeLayout parent;

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

                }
            });
            txtCountry = itemView.findViewById(R.id.txtCountry);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtTemp = itemView.findViewById(R.id.txtTemp);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imageView = itemView.findViewById(R.id.imagePerson);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
