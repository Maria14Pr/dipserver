from django.contrib import admin

from restpost.models import Film, Genre, Currency, Country, Location, FilmLocation

admin.site.register(Film)
admin.site.register(Genre)
admin.site.register(Currency)
admin.site.register(Country)
admin.site.register(Location)
admin.site.register(FilmLocation)
