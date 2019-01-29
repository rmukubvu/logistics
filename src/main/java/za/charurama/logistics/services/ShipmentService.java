package za.charurama.logistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.charurama.logistics.constants.ClearingStatusTypes;
import za.charurama.logistics.models.*;
import za.charurama.logistics.repository.ShipmentMovementRepository;
import za.charurama.logistics.repository.ShipmentRepository;
import za.charurama.logistics.repository.ShipmentStatusRepository;
import za.charurama.logistics.repository.StatusHistoryRepository;

import java.util.*;

@Service
public class ShipmentService {

    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    ShipmentStatusRepository shipmentStatusRepository;
    @Autowired
    ShipmentMovementRepository shipmentMovementRepository;
    @Autowired
    AutoNumberService autoNumberService;
    @Autowired
    LocationLoggerService locationLoggerService;
    @Autowired
    CacheService cacheService;
    @Autowired
    ConsignService consignService;
    @Autowired
    StatusHistoryRepository statusHistoryRepository;

    public Shipment saveShipment(Shipment shipment){
        if (shipment.getManifestReference().isEmpty())
            shipment.setManifestReference(autoNumberService.getManifestReference());
        shipment.setWayBillNumber(autoNumberService.getWayBillSequence());

        Shipment result = shipmentRepository.save(shipment);
        //do status also
        ShipmentStatus shipmentStatus = new ShipmentStatus();
        shipmentStatus.setStatusId(12); //initial status - loaded
        shipmentStatus.setWayBillNumber(result.getWayBillNumber());
        shipmentStatus.setManifestReference(result.getManifestReference());
        shipmentStatus.setCreatedDate(new Date());
        shipmentStatus.setVehicleId(result.getVehicleId());
        shipmentStatusRepository.save(shipmentStatus);
        //log status change
        logInitialStatus(result.getWayBillNumber());
        //done
        return result;
    }

    public Shipment getShipmentByWayBill(long waybill){
        return shipmentRepository.findFirstByWayBillNumberEquals(waybill);
    }

    public Iterable<Shipment> getShipmentsByManifestReference(String manifestReference){
        return shipmentRepository.findShipmentsByManifestReferenceEquals(manifestReference);
    }

    public Iterable<Shipment> getShipmentByVehicle(String vehicleId){
        return shipmentRepository.findShipmentsByVehicleIdEquals(vehicleId);
    }

    public Iterable<Shipment> getShipmentsForConsignee(String consigneeId){
        return shipmentRepository.findShipmentsByConsigneeIdEquals(consigneeId);
    }

    public Iterable<ShipmentWithStatus> getShipmentWithStatusByConsignee(String consigneeId){
        List<ShipmentWithStatus> shipmentWithStatus = new ArrayList<>();
        Iterable<Shipment> shipmentsForConsignee = getShipmentsForConsignee(consigneeId);
        for (Shipment shipment: shipmentsForConsignee
             ) {
            DashboardStatus dashboardStatus = getDashboardStatusByWaybill(shipment.getWayBillNumber());
            shipmentWithStatus.add(new ShipmentWithStatus(shipment,dashboardStatus));
        }
        return shipmentWithStatus;
    }

    public ShipmentStatus gteShipmentStatusByWaybill(long waybillNumber){
        return shipmentStatusRepository.findFirstByWayBillNumberEquals(waybillNumber);
    }

    public ShipmentStatus saveShipmentStatus(ShipmentStatus shipmentStatus){
        //do update
        ShipmentStatus result = shipmentStatusRepository.findFirstByWayBillNumberEquals(shipmentStatus.getWayBillNumber());
        if ( result != null ){
            result.setCreatedDate(new Date());
            result.setStatusId(shipmentStatus.getStatusId());
            logStatusHistory(result);
            return shipmentStatusRepository.save(result);
        }// do an insert
        logStatusHistory(shipmentStatus);
        return shipmentStatusRepository.save(shipmentStatus);
    }

    private void logStatusHistory(ShipmentStatus shipmentStatus){
        String statusText = cacheService.getClearingStatusById(shipmentStatus.getStatusId()).getStatus();
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus(statusText);
        statusHistory.setStatusChangeDate(new Date());
        statusHistory.setWayBillNumber(shipmentStatus.getWayBillNumber());
        statusHistoryRepository.save(statusHistory);
    }

    private void logInitialStatus(long waybill){
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setStatus("LOADED FROM SA DEPOT");
        statusHistory.setStatusChangeDate(new Date());
        statusHistory.setWayBillNumber(waybill);
        statusHistoryRepository.save(statusHistory);
    }

    public List<ShipmentStatus> getShipmentStatus(String vehicleId){
        return shipmentStatusRepository.findShipmentStatusesByVehicleIdEqualsAndStatusIdNot(vehicleId, ClearingStatusTypes.OFFLOADED);
    }

    public Iterable<StatusHistory> getStatusHistory(long waybillNumber){
        return statusHistoryRepository.findStatusHistoryByWayBillNumberEquals(waybillNumber);
    }

    public DashboardStatus getDashboardStatusByWaybill(long waybill) {
        String statusText = "";
        ShipmentStatus shipmentStatus = shipmentStatusRepository.findFirstByWayBillNumberEquals(waybill);
        if (shipmentStatus != null) {
            int bars = 10;
            int progress = 0;
            if (shipmentStatus.getStatusId() == 12 || shipmentStatus.getStatusId() == 1 || shipmentStatus.getStatusId() == 2) {
                progress = bars * 1;
            } else if (shipmentStatus.getStatusId() == 11) {
                progress = 100;
            } else {
                progress = bars * shipmentStatus.getStatusId();
            }
            statusText = cacheService.getClearingStatusById(shipmentStatus.getStatusId()).getStatus();
            return new DashboardStatus(waybill, progress, progress + "%", statusText);
        }
        statusText = cacheService.getClearingStatusById(shipmentStatus.getStatusId()).getStatus();
        return new DashboardStatus(waybill, 0, "0%", statusText);
    }

    public Iterable<ShipmentViewModel> getShipmentViewModel(String vehicleId) {
        List<ShipmentViewModel> shipmentViewModelList = new ArrayList<>();
        Iterable<ConsigneeContactDetails> consigneeContactDetails;
        VehicleLocation lastKnownLocation = locationLoggerService.getLastKnownLocation(vehicleId);
        List<ShipmentStatus> shipmentStatuses = getShipmentStatus(vehicleId);
        List<Shipment> shipments = getShipmentsOnTruck(vehicleId);

        String statusText = "";
        for (Shipment shipment : shipments
        ) {
            if ( shipment == null ) continue;
            consigneeContactDetails = consignService.getConsigneeContactDetailsById(shipment.getConsigneeId());
            Optional<ShipmentStatus> optionalShipmentStatus = shipmentStatuses.stream().filter(e -> e.getWayBillNumber() == shipment.getWayBillNumber()).findFirst();
            if (optionalShipmentStatus.isPresent()) {
                ShipmentStatus s = optionalShipmentStatus.get();
                statusText = cacheService.getClearingStatusById(s.getStatusId()).getStatus();
            }
            String consignee = consignService.getConsigneeById(shipment.getConsigneeId()).getName();
            ShipmentViewModel e = new ShipmentViewModel(consignee, statusText, shipment.getManifestReference(), lastKnownLocation.getLatitude(),
                    lastKnownLocation.getLongitude(), shipment.getWayBillNumber(), consigneeContactDetails);
            shipmentViewModelList.add(e);
        }
        return shipmentViewModelList;
    }

    public void logShipmentMovement(VehicleLocation vehicleLocation){
        List<Shipment> shipments = getShipmentsOnTruck(vehicleLocation.getVehicleId());
        for (Shipment shipment:shipments
             ) {
            ShipmentMovement shipmentMovement = new ShipmentMovement();
            shipmentMovement.setCreatedDate(new Date());
            shipmentMovement.setLongitude(vehicleLocation.getLongitude());
            shipmentMovement.setLatitude(vehicleLocation.getLatitude());
            shipmentMovement.setWayBillNumber(shipment.getWayBillNumber());
            shipmentMovement.setManifestReference(shipment.getManifestReference());
            shipmentMovementRepository.save(shipmentMovement);
            //cache it
            cacheService.saveLastKnownLocationByWaybill(shipment.getWayBillNumber(),vehicleLocation);
        }
    }

    private List<Shipment> getShipmentsOnTruck(String vehicleId) {
        List<ShipmentStatus> shipmentStatuses = getShipmentStatus(vehicleId);
        List<Shipment> shipmentsOnTruck = new ArrayList<>();
        for (ShipmentStatus shipmentStatus : shipmentStatuses
        ) {
            shipmentsOnTruck.add(shipmentRepository.findFirstByWayBillNumberEquals(shipmentStatus.getWayBillNumber()));
        }
        return shipmentsOnTruck;
    }
}
