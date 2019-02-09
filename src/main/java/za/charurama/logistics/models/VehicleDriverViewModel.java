package za.charurama.logistics.models;

import lombok.Data;

@Data
public class VehicleDriverViewModel {
    private boolean hasDriver;
    private String driverId;
    private String firstName;
    private String lastName;
    private String telephone;
    private String vehicleId;
    private String make;
    private String model;
    private String licenseId;

}
