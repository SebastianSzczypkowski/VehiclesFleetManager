package pl.szczypkowski.vehiclesfleetmanager.vehicle.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "vehicle_inspection")
@Entity
public class VehicleInspection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_repair_shop_name")
    private String carRepairShopName;

    @Column(name = "date")
    private Date date;

    @Column(name = "validity_of_the_vehicle_inspection")
    private Date validityOfTheVehicleInspection;

    @Column(name = "description")
    private String description;

    @Column(name = "performedBy")
    private String performedBy;

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
