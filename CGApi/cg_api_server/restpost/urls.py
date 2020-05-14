from restpost import views
from django.conf.urls import url
from django.urls import path, re_path

urlpatterns = [

	#фильмы
	url(r'^films/list/$', views.FilmsListAPIView.as_view(), name='restpost-films-list'),
	url(r'^films/(?P<film_id>[\w-]+)/$', views.ShowFilm.as_view(), name='restpost-show-film'),
	url(r'^add/film/$', views.AddFilm.as_view(), name='restpost-add-film'),

	#локации
	url(r'^locations/list/$', views.LocationsListAPIView.as_view(), name='restpost-locations-list'),
	url(r'^locations/(?P<location_id>[\w-]+)/$', views.ShowLocation.as_view(), name='restpost-show-location'),
	url(r'^add/location/$', views.AddLocation.as_view(), name='restpost-add-film'),

	#локации по фильму
	path(r'^filmslocs/<int:fk_film>', views.ListFilmLocations.as_view(), name='restpost-film-locations-list'),

	#url(r'^add/$', views.AddPost.as_view(), name='restpost-add'),
	#url(r'^add/$', views.AddPost.as_view(), name='restpost-add'),
	#url(r'^(?P<id>[\w-]+)/$', views.ShowPost.as_view(), name='restpost-show'),
	#url(r'^category/list/$', views.CategoryAllAPIView.as_view(), name='restpost-list-caregory'),
	#url(r'^list/(?P<id>[\w-]+)/$', views.CategoryIdAPIView.as_view(), name='restpost-category-id'),
]