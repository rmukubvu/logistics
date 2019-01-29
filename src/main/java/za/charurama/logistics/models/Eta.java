package za.charurama.logistics.models;

import lombok.Data;

@Data
public class Eta {
    private String distanceToDestination;
    private String estimatedTimeOfArrival;

    public Eta(double distanceToDestination, String estimatedTimeOfArrival) {
        this.distanceToDestination = String.format("%.2f km",distanceToDestination);
        this.estimatedTimeOfArrival = estimatedTimeOfArrival;
    }
}
