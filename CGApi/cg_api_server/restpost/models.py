from django.db import models

class Genre(models.Model):
    genre_id = models.AutoField(primary_key=True)
    genre_name = models.CharField(max_length=50, unique=True)

    def __str__(self):
        return self.genre_name

class Film(models.Model):
    film_id = models.AutoField(primary_key=True)
    film_name_rus = models.CharField(max_length=200, unique=True)
    film_name_orig = models.CharField(max_length=200, default = '_')
    film_year = models.IntegerField()
    film_desc = models.TextField(default = '_')
    film_poster = models.ImageField(upload_to = 'filmpic/', default = 'none/no-img.jpg', max_length=100)
    film_kp_url = models.CharField(max_length=200)
    film_genres = models.ManyToManyField(Genre)

    def __str__(self):
        return self.film_name_rus

class Currency(models.Model):
    currency_name_short = models.CharField(max_length=3, primary_key=True)
    currency_name_long = models.CharField (max_length=32)
    currency_today_course = models.FloatField()
    currency_course_day = models.DateField()

    def __str__(self):
        return self.currency_name_long

class Country(models.Model):
    country_id = models.AutoField(primary_key=True)
    country_name_rus = models.CharField(max_length=32)
    country_name_int = models.CharField(max_length=32)
    currency_of_country = models.ForeignKey('Currency', related_name='countries', on_delete=models.CASCADE)

    def __str__(self):
        return self.country_name_rus


class Location(models.Model):
    DEFAULT_COUNTRY_ID = 1
    location_id = models.AutoField(primary_key=True)
    location_name_rus = models.CharField(max_length=100)
    location_name_orig = models.CharField(max_length=100)
    location_latitude = models.FloatField()
    location_longitude = models.FloatField()
    location_opening_time = models.TimeField()
    location_close_time = models.TimeField()
    location_visit_cost = models.FloatField()
    location_visit_duration = models.IntegerField()
    location_picture = models.ImageField(upload_to = 'locationpic/', default = 'none/no-img.jpg', max_length=100)
    fk_country_location = models.ForeignKey('Country', related_name='country_locations', on_delete=models.CASCADE,
    default=DEFAULT_COUNTRY_ID)
    films_were_here = models.ManyToManyField(Film, through='FilmLocation', through_fields=('fk_location', 'fk_film'))

    class Meta:
        unique_together = ('location_latitude', 'location_longitude')

    def __str__(self):
        return self.location_name_rus


class FilmLocation(models.Model):
    fk_location = models.ForeignKey(Location, on_delete=models.CASCADE)
    fk_film = models.ForeignKey(Film, on_delete=models.CASCADE)
    film_scene_desc = models.CharField(max_length=200, default = 'эта сцена')
    film_frame = models.ImageField(upload_to = 'filmframeloc/', default = 'none/no-img.jpg', max_length=100)

    class Meta:
        unique_together = ('fk_location', 'fk_film')

    def __str__(self):
        return self.film_scene_desc