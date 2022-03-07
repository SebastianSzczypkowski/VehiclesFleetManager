package pl.szczypkowski.vehiclesfleetmanager.vehicle.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Table(name = "car_repair_serwis")
public class CarRepairSerwis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_repair_shop_name")
    private String carRepairShopName;

    @Column(name = "date")
    private Date date;

    @Column(name = "cost")
    private Double cost;

    @Column(name ="currency")
    private String currency;

    @Column(name = "description")
    private String description;


    public CarRepairSerwis() {
    }

    public CarRepairSerwis(Long id, String carRepairShopName, Date date, Double cost, String currency, String description) {
        this.id = id;
        this.carRepairShopName = carRepairShopName;
        this.date = date;
        this.cost = cost;
        this.currency = currency;
        this.description = description;
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRepairSerwis that = (CarRepairSerwis) o;
        return Objects.equals(id, that.id) && Objects.equals(carRepairShopName, that.carRepairShopName) && Objects.equals(date, that.date) && Objects.equals(cost, that.cost) && Objects.equals(currency, that.currency) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carRepairShopName, date, cost, currency, description);
    }

    @Override
    public String toString() {
        return "CarRepairSerwis{" +
                "id=" + id +
                ", carRepairShopName='" + carRepairShopName + '\'' +
                ", date=" + date +
                ", cost=" + cost +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
