package za.charurama.logistics.models;

import lombok.Data;

@Data
public class DashboardStatus {
    private long waybill;
    private int progress;
    private String label;
    private String currentStatus;

   public DashboardStatus() {
    }

    public DashboardStatus(long waybill, int progress, String label) {
        this.waybill = waybill;
        this.progress = progress;
        this.label = label;
    }

    public DashboardStatus(long waybill, int progress, String label, String currentStatus) {
        this.waybill = waybill;
        this.progress = progress;
        this.label = label;
        this.currentStatus = currentStatus;
    }
}
