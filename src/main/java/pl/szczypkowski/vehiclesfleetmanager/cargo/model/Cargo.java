package pl.szczypkowski.vehiclesfleetmanager.cargo.model;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "cargo")
@Indexed
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @FullTextField(analyzer = "name")
    @Column(name = "name")
    private String name;
    @FullTextField(analyzer = "name")
    @Column(name = "description")
    private String description;
    @FullTextField(analyzer = "name")
    @Column(name = "type")
    private String type;
    @FullTextField(analyzer = "name")
    @Column(name = "sensitivity")
    private String sensitivity;
    @FullTextField(analyzer = "name")
    @Column(name ="special_remarks")
    private String specialRemarks;

    @Column(name = "delivered")
    private Boolean delivered;

    @Column(name = "delivered_date")
    private Date deliveredDate;

    @Column(name = "assigned")
    private Boolean assigned;

    @Column(name = "assigned_date")
    private Date assignedDate;

    @ManyToOne
    @JoinColumn(name = "driver")
    private Driver driver;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "depth")
    private Double depth;

    public Cargo() {
    }

    public Cargo(Long id, String name, String description, String type, String sensitivity, String specialRemarks, Boolean delivered, Date deliveredDate, Boolean assigned, Date assignedDate, Driver driver) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.sensitivity = sensitivity;
        this.specialRemarks = specialRemarks;
        this.delivered = delivered;
        this.deliveredDate = deliveredDate;
        this.assigned = assigned;
        this.assignedDate = assignedDate;
        this.driver = driver;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public String getSpecialRemarks() {
        return specialRemarks;
    }

    public void setSpecialRemarks(String specialRemarks) {
        this.specialRemarks = specialRemarks;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", sensitivity='" + sensitivity + '\'' +
                ", specialRemarks='" + specialRemarks + '\'' +
                '}';
    }
}
