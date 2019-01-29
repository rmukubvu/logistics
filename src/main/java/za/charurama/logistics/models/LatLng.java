package za.charurama.logistics.models;

import lombok.Data;

@Data
public class LatLng {
    private double latitude;
    private double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
