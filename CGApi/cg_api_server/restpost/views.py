from django.shortcuts import render
from rest_framework.views import APIView
from django.http import HttpResponse
from django.http import JsonResponse

from .serializers import (
	addFilmSerializer,
	filmListSerializer,
	# listCategorySerializer,
	showFilmSerializer,
	locationListSerializer,
	showLocationSerializer,
	addLocationSerializer,
	listFilmLocationsSerializer
	)

from rest_framework.generics import (CreateAPIView, RetrieveUpdateAPIView, DestroyAPIView, ListAPIView, RetrieveAPIView)
from rest_framework.response import Response

from .models import Film, Genre, Location, FilmLocation

from rest_framework.permissions import (
	IsAuthenticated,
	)

from rest_framework.authentication import TokenAuthentication
from rest_framework.authentication import SessionAuthentication

class FilmsListAPIView(ListAPIView):
	serializer_class = filmListSerializer
	queryset = Film.objects.all()

class ShowFilm(RetrieveAPIView):
	queryset = Film.objects.all()
	serializer_class = showFilmSerializer
	lookup_field = 'film_id'

class AddFilm(CreateAPIView):
	authentication_classes = (TokenAuthentication, SessionAuthentication)
	permission_classes = (IsAuthenticated,)
	serializer_class = addFilmSerializer
	queryset = Film.objects.all()

class LocationsListAPIView(ListAPIView):
	serializer_class = locationListSerializer
	queryset = Location.objects.all()

class ShowLocation(RetrieveAPIView):
	queryset = Location.objects.all()
	serializer_class = showLocationSerializer
	lookup_field = 'location_id'

class AddLocation(CreateAPIView):
	authentication_classes = (TokenAuthentication, SessionAuthentication)
	permission_classes = (IsAuthenticated,)
	serializer_class = addLocationSerializer
	queryset = Location.objects.all()

#попытка вывести локации по фильму
class ListFilmLocations(ListAPIView):
    serializer_class = listFilmLocationsSerializer

    def get_queryset(self):
        return FilmLocation.objects.get(fk_film=self.kwargs[self.lookup_field])