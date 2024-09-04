package com.example.backendfiveflowers.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RouteDto {

    private LocationDto startLocation;
    private LocationDto endLocation;
    private Date journeyDate;

    public RouteDto(Object route) {
    }

    public static class LocationDto {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}