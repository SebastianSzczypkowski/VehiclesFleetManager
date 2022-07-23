package pl.szczypkowski.vehiclesfleetmanager.road.model;

public class RoadFromExel {

    private String startLocation;
    private String endLocation;
    private String cargoName;
    private String driverPESEL;
    private String vehicleRegistrationNumber;

    public RoadFromExel() {
    }

    public RoadFromExel(String startLocation, String endLocation, String cargoName, String driverPESEL, String vehicleRegistrationNumber) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.cargoName = cargoName;
        this.driverPESEL = driverPESEL;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getCargoName() {
        return cargoName;
    }

    public void setCargoName(String cargoName) {
        this.cargoName = cargoName;
    }

    public String getDriverPESEL() {
        return driverPESEL;
    }

    public void setDriverPESEL(String driverPESEL) {
        this.driverPESEL = driverPESEL;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    @Override
    public String toString() {
        return "RoadFromExel{" +
                "startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", cargoName='" + cargoName + '\'' +
                ", driverPESEL='" + driverPESEL + '\'' +
                ", vehicleRegistrationNumber='" + vehicleRegistrationNumber + '\'' +
                '}';
    }
}
