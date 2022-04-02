package pl.szczypkowski.vehiclesfleetmanager.map.model;

import javax.persistence.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "lon")
    private String lon;
    @Column(name = "lat")
    private String lat;
    @Column(name = "details")
    private String details;


    public Coordinates() {
    }

    public Coordinates(Long id, String lon, String lat, String details) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
                "id=" + id +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
