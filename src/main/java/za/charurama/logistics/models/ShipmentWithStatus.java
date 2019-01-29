package za.charurama.logistics.models;

import lombok.Data;

@Data
public class ShipmentWithStatus {
    private Shipment shipment;
    private DashboardStatus dashboardStatus;


    public ShipmentWithStatus() {
    }

    public ShipmentWithStatus(Shipment shipment, DashboardStatus dashboardStatus) {
        this.shipment = shipment;
        this.dashboardStatus = dashboardStatus;
    }
}
