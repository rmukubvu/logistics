package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.Shipment;

public interface ShipmentHistoryRepository extends MongoRepository<Shipment,String> {
    Shipment findFirstByWayBillNumberEquals(long waybill);
    Iterable<Shipment> findShipmentsByVehicleIdEquals(String vehicleId);
    Iterable<Shipment> findShipmentsByManifestReferenceEquals(String manifestReference);
    Iterable<Shipment> findShipmentsByConsigneeIdEquals(String consigneeId);
}
