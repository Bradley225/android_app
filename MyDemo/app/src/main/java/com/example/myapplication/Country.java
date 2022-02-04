package com.example.myapplication;

public class Country {

    private String country;
    private String city;
    private String temp;
    private String description;
    private String imageUrl;

    public Country(String country, String city, String temp, String description) {
        this.country = country;
        this.city = city;
        this.temp = temp;
        this.description = description;

    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getTemp()
    {
        return temp;
    }

    public void setTemp(String temp)
    {
        this.temp = temp;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                "temp='" + temp + '\'' +
                ", description='" + description + '\'' +
                 '}';
    }



}
