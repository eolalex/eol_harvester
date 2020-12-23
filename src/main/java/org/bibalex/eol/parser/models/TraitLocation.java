package org.bibalex.eol.parser.models;

public class TraitLocation {

    private String locality;
    private String decimalLatitude;
    private String decimalLongitude;
    private String verbatimLatitude;
    private String verabatimLongitude;
    private String elevation;

    public TraitLocation(String locality,String decimalLatitude, String decimalLongitude, String verbatimLatitude, String verabatimLongitude, String elevation) {
        this.locality =  locality;
        this.decimalLatitude = decimalLatitude;
        this.decimalLongitude = decimalLongitude;
        this.verbatimLatitude = verbatimLatitude;
        this.verabatimLongitude = verabatimLongitude;
        this.elevation = elevation;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDecimalLatitude() {
        return decimalLatitude;
    }

    public void setDecimalLatitude(String decimalLatitude) {
        this.decimalLatitude = decimalLatitude;
    }

    public String getDecimalLongitude() {
        return decimalLongitude;
    }

    public void setDecimalLongitude(String decimalLongitude) {
        this.decimalLongitude = decimalLongitude;
    }

    public String getVerbatimLatitude() {
        return verbatimLatitude;
    }

    public void setVerbatimLatitude(String verbatimLatitude) {
        this.verbatimLatitude = verbatimLatitude;
    }

    public String getVerabatimLongitude() {
        return verabatimLongitude;
    }

    public void setVerabatimLongitude(String verabatimLongitude) {
        this.verabatimLongitude = verabatimLongitude;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    @Override
    public String toString() {
        return "TraitLocation{" +
                "locality='" + locality + '\'' +
                "decimalLatitude='" + decimalLatitude + '\'' +
                ", decimalLongitude='" + decimalLongitude + '\'' +
                ", verbatimLatitude='" + verbatimLatitude + '\'' +
                ", verabatimLongitude='" + verabatimLongitude + '\'' +
                ", elevation='" + elevation + '\'' +
                '}';
    }
}
