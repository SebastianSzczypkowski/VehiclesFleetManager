package pl.szczypkowski.vehiclesfleetmanager.driver.model;


import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "driver_evaluation")
public class DriverEvaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate")
    private Integer rate;

    @OneToOne
    @JoinColumn(name = "road_Id")
    private Road roadId;

    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private Date date;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "valid")
    private Boolean valid;

    public DriverEvaluation() {
    }



    public DriverEvaluation(Long id, Integer rate, Road roadId, String description, Date date, Date updateDate, Boolean valid) {
        this.id = id;
        this.rate = rate;
        this.roadId = roadId;
        this.description = description;
        this.date = date;
        this.updateDate = updateDate;
        this.valid = valid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Road getRoadId() {
        return roadId;
    }

    public void setRoadId(Road roadId) {
        this.roadId = roadId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DriverEvaluation{" +
                "id=" + id +
                ", rate=" + rate +
                ", roadId=" + roadId +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", updateDate=" + updateDate +
                ", valid=" + valid +
                '}';
    }
}
