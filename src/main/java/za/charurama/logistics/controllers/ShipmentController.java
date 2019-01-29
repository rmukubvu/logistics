package za.charurama.logistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import za.charurama.logistics.models.*;
import za.charurama.logistics.services.ShipmentService;


@RestController
public class ShipmentController {
    @Autowired
    ShipmentService shipmentService;

    @PostMapping(value = "/shipment",produces = MediaType.APPLICATION_JSON_VALUE)
    public Shipment saveShipment(@RequestBody Shipment shipment){
        return shipmentService.saveShipment(shipment);
    }

    @GetMapping(value = "/shipment/vehicle")
    public Iterable<ShipmentViewModel> getShipmentByVehicle(String vehicleId){
        return shipmentService.getShipmentViewModel(vehicleId);
    }

    @GetMapping(value = "/shipment/waybill")
    public Shipment getShipment(@RequestParam("waybill") long waybill){
        return shipmentService.getShipmentByWayBill(waybill);
    }

    @GetMapping(value = "/shipment/consignee")
    public Iterable<Shipment> getShipmentsForConsignee(String consigneeId){
        return shipmentService.getShipmentsForConsignee(consigneeId);
    }

    @GetMapping(value = "/shipment/status/waybill")
    public ShipmentStatus gteShipmentStatusByWaybill(long waybillNumber){
        return shipmentService.gteShipmentStatusByWaybill(waybillNumber);
    }

    @GetMapping(value = "/shipment/dashboard/waybill")
    public DashboardStatus getDashboardStatusByWaybill(long waybillNumber){
        return shipmentService.getDashboardStatusByWaybill(waybillNumber);
    }

    @GetMapping(value = "/shipment/status/history")
    public Iterable<StatusHistory> getStatusHistory(long waybillNumber){
        return shipmentService.getStatusHistory(waybillNumber);
    }

    @GetMapping(value = "/shipment/status/consignee")
    public Iterable<ShipmentWithStatus> getShipmentWithStatusByConsignee(String consigneeId){
        return shipmentService.getShipmentWithStatusByConsignee(consigneeId);
    }

}
