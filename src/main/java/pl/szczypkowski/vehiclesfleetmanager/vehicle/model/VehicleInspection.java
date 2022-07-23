package pl.szczypkowski.vehiclesfleetmanager.vehicle.model;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "vehicle_inspection")
@Entity
@Indexed
public class VehicleInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @FullTextField(analyzer = "name")
    @Column(name = "car_repair_shop_name")
    private String carRepairShopName;

    @Column(name = "date")
    private Date date;

    @Column(name = "validity_of_the_vehicle_inspection")
    private Date validityOfTheVehicleInspection;
    @FullTextField(analyzer = "name")
    @Column(name = "description")
    private String description;
    @FullTextField(analyzer = "name")
    @Column(name = "performed_by")
    private String performedBy;

    @ManyToOne
    @JoinColumn(name = "id_vehicle")
    private Vehicle idVehicle;


    public VehicleInspection() {
    }

    public VehicleInspection(Long id, String carRepairShopName, Date date, Date validityOfTheVehicleInspection, String description, String performedBy) {
        this.id = id;
        this.carRepairShopName = carRepairShopName;
        this.date = date;
        this.validityOfTheVehicleInspection = validityOfTheVehicleInspection;
        this.description = description;
        this.performedBy = performedBy;
    }


    public Vehicle getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(Vehicle idVehicle) {
        this.idVehicle = idVehicle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarRepairShopName() {
        return carRepairShopName;
    }

    public void setCarRepairShopName(String carRepairShopName) {
        this.carRepairShopName = carRepairShopName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getValidityOfTheVehicleInspection() {
        return validityOfTheVehicleInspection;
    }

    public void setValidityOfTheVehicleInspection(Date validityOfTheVehicleInspection) {
        this.validityOfTheVehicleInspection = validityOfTheVehicleInspection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleInspection that = (VehicleInspection) o;
        return Objects.equals(id, that.id) && Objects.equals(carRepairShopName, that.carRepairShopName) && Objects.equals(date, that.date) && Objects.equals(validityOfTheVehicleInspection, that.validityOfTheVehicleInspection) && Objects.equals(description, that.description) && Objects.equals(performedBy, that.performedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carRepairShopName, date, validityOfTheVehicleInspection, description, performedBy);
    }

    @Override
    public String toString() {
        return "VehicleInspection{" +
                "id=" + id +
                ", carRepairShopName='" + carRepairShopName + '\'' +
                ", date=" + date +
                ", validityOfTheVehicleInspection=" + validityOfTheVehicleInspection +
                ", description='" + description + '\'' +
                ", performedBy='" + performedBy + '\'' +
                '}';
    }
}
