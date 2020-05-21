package com.learn_django.cinemaguide.model;

//модель поста с полями
public class FilmModel {

    private Integer film_id;
    private String film_name_rus;
    private String film_name_orig;
    private Integer film_year;
    private String film_desc;
    private String film_poster;
    private String film_kp_url;
    private String [] film_genres;

    public FilmModel(int film_id,
                     String film_name_rus,
                     String film_name_orig,
                     int film_year,
                     String film_desc,
                     String film_poster,
                     String film_kp_url,
                     String [] film_genres

    ) {
        this.film_id = film_id;
        this.film_name_rus = film_name_rus;
        this.film_name_orig = film_name_orig;
        this.film_year = film_year;
        this.film_desc = film_desc;
        this.film_poster = film_poster;
        this.film_kp_url = film_kp_url;
        this.film_genres = film_genres;
    }

    public int getId(){
        return film_id;
    }
    public String getRusTitle(){
        return film_name_rus;
    }

    public String getOrigTitle(){
        return film_name_orig;
    }

    public String getFilmPoster(){
        return film_poster;
    }

    public int getFilmYear(){
        return film_year;
    }

    public String getFilmDesc(){
        return film_desc;
    }

    public String getFilmGenres () {
        StringBuilder genres_list= new StringBuilder();
        for (int i=0; i<film_genres.length-1; i++){
            genres_list.append(film_genres[i]).append(", ");
        }
        genres_list.append(film_genres[film_genres.length-1]);
        return genres_list.toString();
    }



}