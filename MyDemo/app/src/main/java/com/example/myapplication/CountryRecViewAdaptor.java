package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * An adaptor for a RecyclerView containing Country objects
 */
public class CountryRecViewAdaptor extends RecyclerView.Adapter<CountryRecViewAdaptor.ViewHolder> {

    private ArrayList<Country> mCountries = new ArrayList<>();
    private Context mContext;

    public CountryRecViewAdaptor(Context aContext) {
        mContext = aContext;
    }

    /**
     * {@inheritDoc}
     * Initialises and returns a ViewHolder object
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_list_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    /**
     * {@inheritDoc}
     * Gets the Country information of an item in the RecyclerView from mCountries and sets this information to variables in its ViewHolder Object
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTxtCountry.setText(mCountries.get(position).getCountry());
        holder.mTxtCity.setText(mCountries.get(position).getCity());
        holder.mTxtTemp.setText(mCountries.get(position).getTemperature());
        holder.mTxtDescription.setText(mCountries.get(position).getDescription());
    }
    /**
     * {@inheritDoc}
     * Provides the amount of Country objects stored in the mCountries ArrayList by returning its size
     */
    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    /**
     * Sets countriesList parameter to local mCountries ArrayList and calls notifyDataSetChanged to refresh RecyclerView
     *
     * @param countriesList an ArrayList of Country objects to be used to set mCountries ArrayList and display
     */
    public void setCountries(ArrayList<Country> countriesList) {
        this.mCountries = countriesList;
        notifyDataSetChanged();
    }

    /**
     * Describes a Country object stored and displayed in a RecyclerView and contains metadata about its position in the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTxtCountry;
        private TextView mTxtCity;
        private TextView mTxtTemp;
        private TextView mTxtDescription;

        /**
         * Constructor for ViewHolder which initialises its TextView variables from the information stored in itemView parameter
         *
         * @param itemView a View object for a itemView in a RecyclerView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mTxtCountry = itemView.findViewById(R.id.txtCountry);
            mTxtCity = itemView.findViewById(R.id.txtCity);
            mTxtTemp = itemView.findViewById(R.id.txtTemp);
            mTxtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
