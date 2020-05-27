package com.learn_django.cinemaguide.solving_problems;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class SalesmanProblem {


    private ArrayList<Integer> solvedLocsId = new ArrayList<>();
    private ArrayList<Integer> solvedFinalLocsId = new ArrayList<>();
    private ArrayList <Double> distancesFinal = new ArrayList<>() ;
    private float [] solvedFinalLat;
    private float [] solvedFinalLong;
    private float [] solvedLat;
    private float [] solvedLong;
    ArrayList<Integer> currentSolution = new ArrayList<>();


    private ArrayList<Integer> smLocsId = new ArrayList<>();
    private float [] smLocsLat;
    private float [] smLocsLong;

    public SalesmanProblem (ArrayList<Integer> locsId, float [] locsLat, float [] locsLong){

        this.smLocsId = locsId;
        this.smLocsLat = locsLat;
        this.smLocsLong = locsLong;

        solveSalesman();

    }

    private void solveSalesman() {
        // Set initial temp
        double temp = 10000;

        float[]currentLat = smLocsLat;
        float[]currentLong = smLocsLong;

        double shortestDistance, currentDistance;

        // Cooling rate
        double coolingRate = 0.003;

        currentSolution = smLocsId;
        solvedLocsId = smLocsId;
        solvedLat = smLocsLat;
        solvedLong = smLocsLong;
        shortestDistance = getDistance(currentLat, currentLong);
        Log.d("distance: ", " Initial solution "+ shortestDistance);

        while (temp > 1) {

            ArrayList<Integer> newSolution = currentSolution;
            float[]newLat = smLocsLat;
            float[]newLong = smLocsLong;
            int tourPos1=0, tourPos2=0;
            // Create new neighbour tour
            while (tourPos1==tourPos2) {
                tourPos1 = (int) (currentSolution.size() * Math.random());
                tourPos2 = (int) (currentSolution.size() * Math.random());
            }

            int locSwap1 = newSolution.get(tourPos1);
            int locSwap2 = newSolution.get(tourPos2);
            float latSwap1 = newLat[tourPos1];
            float latSwap2 = newLat[tourPos2];
            float longSwap1 = newLong[tourPos1];
            float longSwap2 = newLong[tourPos2];

            // Swap them
            newSolution.set(tourPos2, locSwap1);
            newSolution.set(tourPos1, locSwap2);
            newLat[tourPos2] = latSwap1;
            newLat[tourPos1] = latSwap2;
            newLong [tourPos2] = longSwap1;
            newLong [tourPos1] = longSwap2;

            // Get energy of solutions
            currentDistance = getDistance(currentLat, currentLong);
            double neighbourDistance = getDistance(newLat, newLong);

            if (neighbourDistance < currentDistance) {

                currentSolution = newSolution;
                currentLat = newLat;
                currentLong = newLong;

                // Keep track of the best solution found
                if (currentDistance < shortestDistance) {
                    solvedLocsId = currentSolution;
                    solvedLat = currentLat;
                    solvedLong = currentLong;
                }

            }

            // Cool system
            temp *= 1-coolingRate;
        }

        //Log.d("Final solution", " distance: " + getDistance(smLocsLat, solvedLong));
        completeFinalCalc();

        //return solvedLocsId;
    }


    private double getDistance(float [] lats, float [] longs){


        double distance=0, distanceBetween = 0;
        // Loop through our tour's cities
        for (int locationIndex=0; locationIndex < lats.length; locationIndex++) {
            // Get city we're traveling from
            if(locationIndex+1 < lats.length){

                //destinationCity = getCity(locationIndex+1);
                distanceBetween = getDistanceBetween(lats[locationIndex], longs[locationIndex], lats[locationIndex+1],
                        longs[locationIndex+1]);
            } else{
                //destinationCity = getCity(0);
                distanceBetween = getDistanceBetween(lats[locationIndex], longs[locationIndex], lats[0], longs[0]);
            }

            // Get the distance between the two cities
            //расстояние между городами
            distance+=distanceBetween;
            //tourDistance += fromCity.distanceTo(destinationCity);
        }
        //distance = tourDistance;
        return distance;

    }

    private double getDistanceBetween (double loc1lat, double loc1long, double loc2lat, double loc2long) {
        float distance = 0;
        float[] results = new float[2];

        LatLng from = new LatLng(loc1lat,loc1long);
        LatLng to = new LatLng(loc2lat,loc2long);

        Double mesurement = SphericalUtil.computeDistanceBetween(from, to);

        Location.distanceBetween(loc1lat, loc1long, loc2lat, loc2long, results);
        distance = results[0];

        for (float result : results) {
            if (result < distance) {
                distance = result;
            }
        }
        return mesurement;
    }

    public void completeFinalCalc () {

        double distanceBetween = 0, distanceMax = 0;
        int maxDistanceStartIndex  = -1;
        // Loop through our tour's cities
        for (int locationIndex=0; locationIndex < solvedLat.length; locationIndex++) {
            // Get city we're traveling from
            if(locationIndex+1 < solvedLat.length){
                //destinationCity = getCity(locationIndex+1);
                distanceBetween =  getDistanceBetween(solvedLat[locationIndex], solvedLong[locationIndex], solvedLat[locationIndex+1],
                        solvedLong[locationIndex+1]);

                Log.d("distence A B ", distanceBetween +" from "+solvedLat[locationIndex]+" "+solvedLong[locationIndex]+" to "+
                        solvedLat[locationIndex+1]+" "+ solvedLong[locationIndex+1]);

            } else{
                //destinationCity = getCity(0);
                distanceBetween = getDistanceBetween(solvedLat[locationIndex], solvedLong[locationIndex], solvedLat[0], solvedLong[0]);
                Log.d("distence A B ", distanceBetween +" from "+solvedLat[locationIndex]+" "+solvedLong[locationIndex]+" to "+
                        solvedLat[0]+" "+ solvedLong[0]);

            }


            if (distanceBetween > distanceMax) {
                distanceMax = distanceBetween;
                maxDistanceStartIndex = locationIndex;
                Log.d("max length", maxDistanceStartIndex +" from "+solvedLocsId.get(maxDistanceStartIndex));
            }
            // Get the distance between the two cities
            //расстояние между городами

        }

        solvedFinalLat = new float[solvedLat.length];
        solvedFinalLong = new float[solvedLong.length];

        for (int pos=0, index=maxDistanceStartIndex+1; index<solvedLocsId.size();index++, pos++){

            solvedFinalLocsId.add(solvedLocsId.get(index));
            solvedFinalLat[pos] = solvedLat[index];
            solvedFinalLong[pos] = solvedLong[index];

        }

        for (int pos=solvedLocsId.size()-(1+maxDistanceStartIndex), index=0; pos<solvedLocsId.size();index++, pos++){

            solvedFinalLocsId.add(solvedLocsId.get(index));
            solvedFinalLat[pos] = solvedLat[index];
            solvedFinalLong[pos] = solvedLong[index];

        }

        for (int locationIndex=0; locationIndex < solvedFinalLat.length-1; locationIndex++) {
            // Get city we're traveling from
            distanceBetween =  getDistanceBetween(solvedFinalLat[locationIndex], solvedFinalLong[locationIndex], solvedFinalLat[locationIndex+1],
                    solvedFinalLong[locationIndex+1]);
            Log.d("distence A B ", distanceBetween +" from "+solvedFinalLocsId.get(locationIndex)+" to "+
                    solvedFinalLocsId.get(locationIndex+1));
            // Get the distance between the two cities
            //расстояние между городами

            BigDecimal bd = new BigDecimal(Double.toString(distanceBetween/1000));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            distancesFinal.add(bd.doubleValue());
        }
    }

    public ArrayList<Integer> getSolvedFinalLocsId () {
        return solvedFinalLocsId;
    }

    public float[] getSolvedFinalLat () {
        return solvedFinalLong;
    }

    public float[] getSolvedFinalLong () {
        return solvedFinalLong;
    }

    public ArrayList<Double> getDistancesFinal () {
        return distancesFinal;
    }

}
