package za.charurama.logistics.viewmodels;

import lombok.Data;
import za.charurama.logistics.models.Consignee;
import za.charurama.logistics.models.ConsigneeContactDetails;

@Data
public class ConsigneeViewModel {
    private Consignee consignee;
    private ConsigneeContactDetails consigneeContactDetails;

    public ConsigneeViewModel(Consignee consignee, ConsigneeContactDetails consigneeContactDetails) {
        this.consignee = consignee;
        this.consigneeContactDetails = consigneeContactDetails;
    }
}
