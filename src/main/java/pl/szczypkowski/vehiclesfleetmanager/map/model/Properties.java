package pl.szczypkowski.vehiclesfleetmanager.map.model;

import java.util.List;

public class Properties {
    private Double distance;
    private Double duration;
    private List<Segment> segments;

    public Properties() {
    }

    public Properties(Double distance, Double duration, List<Segment> segments) {
        this.distance = distance;
        this.duration = duration;
        this.segments = segments;
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

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "distance=" + distance +
                ", duration=" + duration +
                ", segments=" + segments +
                '}';
    }
}
