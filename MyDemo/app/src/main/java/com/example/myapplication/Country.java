package com.example.myapplication;

/**
 * Stores an object containing Country information about a JSON Object loaded from a Rest API
 */
public class Country {

    private String mCountry;
    private String mCity;
    private String mTemperature;
    private String mDescription;

    /**
     * Constructor for class Country
     *
     * @param country sets to mCountry String
     * @param city sets to mCity String
     * @param description sets to mDescription String
     * @param temp sets to mTemperature String
     */
    public Country(String country, String city, String temp, String description) {
        this.mCountry = country;
        this.mCity = city;
        this.mTemperature = temp;
        this.mDescription = description;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature(String mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    /**
     * {@inheritDoc}
     * Returns the information stored in this Country object in a String
     */
    @Override
    public String toString() {
        return "Country{" +
                "country='" + mCountry + '\'' +
                ", city='" + mCity + '\'' +
                "temp='" + mTemperature + '\'' +
                ", description='" + mDescription + '\'' +
                 '}';
    }
}
