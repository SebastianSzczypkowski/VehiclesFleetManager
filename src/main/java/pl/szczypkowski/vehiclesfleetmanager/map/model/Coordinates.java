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
    @Column(name="color")
    private String color;
    @Column(name = "name")
    private String name;


    public Coordinates() {
    }

    public Coordinates(Long id, String lon, String lat, String details) {
        this.id = id;
        this.lon = lon;
        this.lat = lat;
        this.details = details;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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
