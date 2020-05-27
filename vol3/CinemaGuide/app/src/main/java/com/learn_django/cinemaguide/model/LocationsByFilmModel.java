package com.learn_django.cinemaguide.model;

public class LocationsByFilmModel {

    private Integer fk_film;
    private LocationModel fk_location;

    public LocationsByFilmModel(int fk_film,
                         LocationModel fk_location
    ) {
        this.fk_film = fk_film;
        this.fk_location = fk_location;
    }

    public int getLocFilmId() {
        return fk_location.getId();
    }

    public int getLocFilmFilmId() {
        return fk_film;
    }

    public String getLocFilmNameRus(){
        return fk_location.getLocRusTitle();
    }

    public String getLocFilmNameOrig(){
        return fk_location.getLocOrigTitle();
    }

    public String getLocFilmCountry(){
        return fk_location.getLocCountry();
    }

    public String getLocFilmCurrency(){
        return fk_location.getLocCurrency();
    }

    public String getLocFilmPhoto(){
        return fk_location.getLocPhoto();
    }

    public float getLocFilmCost(){
        return fk_location.getLocation_cost();
    }

    public float getLocFilmLat(){
        return fk_location.getLocLat();
    }

    public float getLocFilmLong(){
        return fk_location.getLocLong();
    }

    public int getLocFilmDuration(){
        return fk_location.getDuration();
    }



}
