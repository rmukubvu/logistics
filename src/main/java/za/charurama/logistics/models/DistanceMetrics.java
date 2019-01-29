package za.charurama.logistics.models;

import lombok.Data;

@Data
public class DistanceMetrics {
    private String distanceFromOrigin;
    private String distanceToDestination;
    private String completeDistanceToDestination;
    private String estimatedTimeOfArrival;
    private double latitude;
    private double longitude;

    public DistanceMetrics(String distanceFromOrigin, String distanceToDestination, String completeDistanceToDestination, String estimatedTimeOfArrival, double latitude, double longitude) {
        this.distanceFromOrigin = distanceFromOrigin;
        this.distanceToDestination = distanceToDestination;
        this.completeDistanceToDestination = completeDistanceToDestination;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public DistanceMetrics(String distanceFromOrigin, String distanceToDestination, String completeDistanceToDestination, String estimatedTimeOfArrival) {
        this.distanceFromOrigin = distanceFromOrigin;
        this.distanceToDestination = distanceToDestination;
        this.completeDistanceToDestination = completeDistanceToDestination;
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }
}
