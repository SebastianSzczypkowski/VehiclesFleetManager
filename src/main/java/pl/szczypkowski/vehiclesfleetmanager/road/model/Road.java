package pl.szczypkowski.vehiclesfleetmanager.road.model;


import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "road")
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "start")
    private Coordinates start;
    @OneToOne
    @JoinColumn(name = "end")
    private Coordinates end;
    @OneToOne
    @JoinColumn(name = "driver")
    private Driver driver;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name ="update_date" )
    private LocalDate updateDate;
    @Column(name = "finished")
    private Boolean finished;
    @OneToOne
    @JoinColumn (name = "cargo")
    private Cargo cargo;

    @OneToOne
    @JoinColumn (name = "vehicle")
    private Vehicle vehicle;


    public Road() {
    }

    public Road(Long id, Coordinates start, Coordinates end, Driver driver, LocalDate creationDate, LocalDate updateDate) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.driver = driver;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }


    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coordinates getStart() {
        return start;
    }

    public void setStart(Coordinates start) {
        this.start = start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Road{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", driver=" + driver +
                '}';
    }
}
