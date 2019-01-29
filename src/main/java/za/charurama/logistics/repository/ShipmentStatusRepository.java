package za.charurama.logistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import za.charurama.logistics.models.ShipmentStatus;

import java.util.List;

public interface ShipmentStatusRepository extends MongoRepository<ShipmentStatus,String> {
    ShipmentStatus findFirstByWayBillNumberEquals(long waybillNumber);
    List<ShipmentStatus> findShipmentStatusesByVehicleIdEqualsAndStatusIdNot(String vehicleId, int statusId);
}
