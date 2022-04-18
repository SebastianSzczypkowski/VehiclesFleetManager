package pl.szczypkowski.vehiclesfleetmanager.vehicle.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "vehicle")
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "vin")
    private String vin;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "car_mileage")
    private Integer carMileage;

    @Column(name = "car_load_capacity")
    private Integer carLoadCapacity;

    @Column(name = "lorry_semitrailer")
    private Boolean lorrySemitrailer;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "engine_capacity")
    private Double engineCapacity;

    @Column(name = "average_fuel_consumption")
    private Double averageFuelConsumption;

    @OneToMany
    @JoinColumn(name = "id_vehicle_inspection")
    private List<VehicleInspection> vehicleInspection;

    @Column(name = "roadworthy")
    private Boolean roadworthy;

    @Column(name = "occupied")
    private Boolean occupied;

    public Vehicle() {
    }

    public Vehicle(Long id, String name, String vin, String registrationNumber, Integer carMileage, Integer carLoadCapacity, Boolean lorrySemitrailer, Integer numberOfSeats, Double engineCapacity, Double averageFuelConsumption, List<VehicleInspection> vehicleInspection, Boolean roadworthy, Boolean occupied) {
        this.id = id;
        this.name = name;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
        this.carMileage = carMileage;
        this.carLoadCapacity = carLoadCapacity;
        this.lorrySemitrailer = lorrySemitrailer;
        this.numberOfSeats = numberOfSeats;
        this.engineCapacity = engineCapacity;
        this.averageFuelConsumption = averageFuelConsumption;
        this.vehicleInspection = vehicleInspection;
        this.roadworthy = roadworthy;
        this.occupied = occupied;
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getCarMileage() {
        return carMileage;
    }

    public void setCarMileage(Integer carMileage) {
        this.carMileage = carMileage;
    }

    public Integer getCarLoadCapacity() {
        return carLoadCapacity;
    }

    public void setCarLoadCapacity(Integer carLoadCapacity) {
        this.carLoadCapacity = carLoadCapacity;
    }

    public Boolean getLorrySemitrailer() {
        return lorrySemitrailer;
    }

    public void setLorrySemitrailer(Boolean lorrySemitrailer) {
        this.lorrySemitrailer = lorrySemitrailer;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Double getAverageFuelConsumption() {
        return averageFuelConsumption;
    }

    public void setAverageFuelConsumption(Double averageFuelConsumption) {
        this.averageFuelConsumption = averageFuelConsumption;
    }

    public List<VehicleInspection> getVehicleInspection() {
        return vehicleInspection;
    }

    public void setVehicleInspection(List<VehicleInspection> vehicleInspection) {
        this.vehicleInspection = vehicleInspection;
    }

    public Boolean getRoadworthy() {
        return roadworthy;
    }

    public void setRoadworthy(Boolean roadworthy) {
        this.roadworthy = roadworthy;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(name, vehicle.name) && Objects.equals(vin, vehicle.vin) && Objects.equals(registrationNumber, vehicle.registrationNumber) && Objects.equals(carMileage, vehicle.carMileage) && Objects.equals(carLoadCapacity, vehicle.carLoadCapacity) && Objects.equals(lorrySemitrailer, vehicle.lorrySemitrailer) && Objects.equals(numberOfSeats, vehicle.numberOfSeats) && Objects.equals(engineCapacity, vehicle.engineCapacity) && Objects.equals(averageFuelConsumption, vehicle.averageFuelConsumption) && Objects.equals(vehicleInspection, vehicle.vehicleInspection) && Objects.equals(roadworthy, vehicle.roadworthy) && Objects.equals(occupied, vehicle.occupied);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vin, registrationNumber, carMileage, carLoadCapacity, lorrySemitrailer, numberOfSeats, engineCapacity, averageFuelConsumption, vehicleInspection, roadworthy, occupied);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vin='" + vin + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", carMileage=" + carMileage +
                ", carLoadCapacity=" + carLoadCapacity +
                ", lorrySemitrailer=" + lorrySemitrailer +
                ", numberOfSeats=" + numberOfSeats +
                ", engineCapacity=" + engineCapacity +
                ", averageFuelConsumption=" + averageFuelConsumption +
                ", vehicleInspection=" + vehicleInspection +
                ", roadworthy=" + roadworthy +
                ", occupied=" + occupied +
                '}';
    }
}
