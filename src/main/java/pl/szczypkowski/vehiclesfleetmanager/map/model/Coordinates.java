package pl.szczypkowski.vehiclesfleetmanager.map.model;

public class Coordinates {

    private String lon;
    private String lat;

    public Coordinates() {
    }

    public Coordinates(String lot, String lat) {
        this.lon = lot;
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lot='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
