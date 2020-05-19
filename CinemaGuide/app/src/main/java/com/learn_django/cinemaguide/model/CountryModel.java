package com.learn_django.cinemaguide.model;

public class CountryModel {

    private Integer country_id;
    private String country_name_rus;
    private String country_name_int;
    private String currency_of_country;

    public CountryModel( int country_id,
                         String country_name_rus,
                         String country_name_int,
                         String currency_of_country
    ) {

        this.country_id = country_id;
        this.country_name_rus = country_name_rus;
        this.country_name_int = country_name_int;
        this.currency_of_country = currency_of_country;
    }

    public int getCountryId(){
        return country_id;
    }
    public String getCountry_name_rus(){
        return country_name_rus;
    }

    public String getCurrency_of_country(){
        return currency_of_country;
    }



}
