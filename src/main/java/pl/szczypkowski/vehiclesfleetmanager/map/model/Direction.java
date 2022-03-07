package pl.szczypkowski.vehiclesfleetmanager.map.model;


import javax.persistence.*;
import java.util.List;

//@Table(name = "firection")
//@Entity
public class Direction {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private List<Properties> properties;
    private List<Coordinates> coordinates;


    public Direction() {
    }

    public Direction(List<Properties> properties, List<Coordinates> coordinates) {
        this.properties = properties;
        this.coordinates = coordinates;
    }

    public List<Properties> getProperties() {
        return properties;
    }


    public void setProperties(List<Properties> properties) {
        this.properties = properties;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "properties=" + properties +
                ", coordinates=" + coordinates +
                '}';
    }
}
