package pl.szczypkowski.vehiclesfleetmanager.road.model;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "road")
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start")
    private Long start;
    @Column(name = "end")
    private Long end;
    @Column(name = "driver")
    private Long driver;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name ="update_date" )
    private LocalDate updateDate;
    @Column(name = "finished")
    private Boolean finished;


    public Road() {
    }

    public Road(Long id, Long start, Long end, Long driver, LocalDate creationDate, LocalDate updateDate) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.driver = driver;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
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
