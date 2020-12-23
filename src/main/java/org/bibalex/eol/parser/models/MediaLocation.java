package org.bibalex.eol.parser.models;

public class MediaLocation {
    String locationCreated;
    String genericLocation;
    String latitude;
    String longitude;
    String altitude;


    public MediaLocation(String locationCreated, String genericLocation, String latitude, String longitude,
                         String altitude) {
        this.locationCreated = locationCreated;
        this.genericLocation = genericLocation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String getLocationCreated() {
        return locationCreated;
    }

    public void setLocationCreated(String locationCreated) {
        this.locationCreated = locationCreated;
    }

    public String getGenericLocation() {
        return genericLocation;
    }

    public void setGenericLocation(String genericLocation) {
        this.genericLocation = genericLocation;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    @Override
    public String toString() {
        return "MediaLocation{" +
                "locationCreated='" + locationCreated + '\'' +
                ", genericLocation='" + genericLocation + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", altitude='" + altitude + '\'' +
                '}';
    }
}

