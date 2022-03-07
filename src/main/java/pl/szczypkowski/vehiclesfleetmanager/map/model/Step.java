package pl.szczypkowski.vehiclesfleetmanager.map.model;

import java.util.List;

public class Step {

    private Double distance;
    private Double duration;
    private Integer type;
    private String instrucion;
    private String name;
    private List<Integer> wayPoints;

    public Step() {
    }

    public Step(Double distance, Double duration, Integer type, String instrucion, String name, List<Integer> wayPoints) {
        this.distance = distance;
        this.duration = duration;
        this.type = type;
        this.instrucion = instrucion;
        this.name = name;
        this.wayPoints = wayPoints;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getInstrucion() {
        return instrucion;
    }

    public void setInstrucion(String instrucion) {
        this.instrucion = instrucion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getWayPoints() {
        return wayPoints;
    }

    public void setWayPoints(List<Integer> wayPoints) {
        this.wayPoints = wayPoints;
    }


    @Override
    public String toString() {
        return "Step{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", type=" + type +
                ", instrucion='" + instrucion + '\'' +
                ", name='" + name + '\'' +
                ", wayPoints=" + wayPoints +
                '}';
    }
}
