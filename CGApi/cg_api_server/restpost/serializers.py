from rest_framework import serializers
from rest_framework.serializers import (
	ModelSerializer,
)
from .models import Film, Genre, Location, Country, Currency, FilmLocation

class filmListSerializer(ModelSerializer):
	class Meta:
		model = Film
		fields = [
			'film_id',
			'film_name_rus',
			'film_poster',
		]

class GenresSerializer(serializers.ModelSerializer):
    class Meta:
        model = Genre
        fields = ('genre_name', )



class showFilmSerializer(ModelSerializer):
	film_genres = serializers.StringRelatedField(many=True, read_only=True) #GenresSerializer(many=True, read_only=True)
	class Meta:
		model = Film
		fields = [
			'film_id',
			'film_name_rus',
			'film_name_orig',
			'film_year',
			'film_desc',
			'film_poster',
			'film_kp_url',
			'film_genres',
		]

class addFilmSerializer(ModelSerializer):
	class Meta:
		model = Film
		fields = [
			'film_id',
			'film_name_rus',
			'film_name_orig',
			'film_year',
			'film_desc',
			'film_poster',
			'film_kp_url',
			'film_genres',
		]

class locationListSerializer(ModelSerializer):
	class Meta:
		model = Location
		fields = [
			'location_id',
			'location_name_rus',
			'location_picture',
		]

class CountrySerializer(serializers.ModelSerializer):
    class Meta:
        model = Country
        fields = [
			'country_name_int',
			'currency_of_country'
		]

class showLocationSerializer(ModelSerializer):
	fk_country_location =  CountrySerializer(read_only=True)
	class Meta:
		model = Location
		fields = [
			'location_id',
			'location_name_rus',
			'location_name_orig',
			'location_latitude',
			'location_longitude',
			'location_opening_time',
			'location_close_time',
			'location_visit_cost',
			'location_visit_duration',
			'location_picture',
			'fk_country_location',
		]

class addLocationSerializer(ModelSerializer):
	class Meta:
		model = Location
		fields = [
			'location_id',
			'location_name_rus',
			'location_name_orig',
			'location_latitude',
			'location_longitude',
			'location_opening_time',
			'location_close_time',
			'location_visit_cost',
			'location_visit_duration',
			'location_picture',
			'fk_country_location',
		]

class listFilmLocationsSerializer(ModelSerializer):
	fk_location = locationListSerializer(read_only=True)
	class Meta:
		model = FilmLocation
		fields = [
			'fk_film',
			'fk_location',
		]

