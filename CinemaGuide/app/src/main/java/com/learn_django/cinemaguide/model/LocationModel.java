package com.learn_django.cinemaguide.model;

//модель поста с полями
public class LocationModel {

    private Integer location_id;
    private String location_name_rus;
    private String location_name_orig;
    private Float location_latitude;
    private Float location_longitude;
    private String location_opening_time;
    private String location_close_time;
    private Float location_visit_cost;
    private Integer location_visit_duration;
    private String location_picture;
    private String location_country;
    private CountryModel fk_country_location;

    public LocationModel(int location_id,
                         String location_name_rus,
                         String location_name_orig,
                         float location_latitude,
                         float location_longitude,
                         String location_opening_time,
                         String location_close_time,
                         float location_visit_cost,
                         int location_visit_duration,
                         String location_picture,
                         CountryModel fk_country_location
                         ) {

        this.location_id = location_id;
        this.location_name_rus = location_name_rus;
        this.location_name_orig = location_name_orig;
        this.location_latitude = location_latitude;
        this.location_longitude = location_longitude;
        this.location_opening_time = location_opening_time;
        this.location_close_time = location_close_time;
        this.location_visit_cost = location_visit_cost;
        this.location_visit_duration = location_visit_duration;
        this.location_picture = location_picture;
        //this.fk_country_location = fk_country_location;
    }

    public int getId(){
        return location_id;
    }
    public String getLocRusTitle(){
        return location_name_rus;
    }

    public String getLocOrigTitle(){
        return location_name_orig;
    }

    public String getLocPhoto(){
        return location_picture;
    }

    public String getLocCountry(){
        return fk_country_location.getCountry_name_rus();
    }

    public String getLocCurrency(){
        return fk_country_location.getCurrency_of_country();
    }

    public String getLocation_opening_time(){
        return location_opening_time;
    }

    public String getLocation_close_time(){
        return location_close_time;
    }

    public float getLocation_cost() {
        return location_visit_cost;
    }


}
