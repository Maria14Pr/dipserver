package com.learn_django.cinemaguide.model;

//модель поста с полями
public class JourneyAddModel {

    private String journey_name;
    private String journey_desc;
    private String journey_intensity;

    public JourneyAddModel(String journey_name,
                           String journey_desc,
                           String journey_intensity
    ) {
        this.journey_name = journey_name;
        this.journey_desc = journey_desc;
        this.journey_intensity = journey_intensity;
    }

}