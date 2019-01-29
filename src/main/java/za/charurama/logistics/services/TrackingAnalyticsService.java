package za.charurama.logistics.services;

import com.google.common.collect.Iterators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.charurama.logistics.common.GeometryHelper;
import za.charurama.logistics.common.TimeUtil;
import za.charurama.logistics.models.DistanceMetrics;
import za.charurama.logistics.models.Shipment;
import za.charurama.logistics.models.VehicleLocation;
import za.charurama.logistics.repository.ShipmentRepository;

@Service
public class TrackingAnalyticsService {

    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    CacheService cacheService;

    private GeometryHelper geometryHelper = new GeometryHelper();

    public DistanceMetrics getDistanceMetricsByManifestReference(String manifestReference) {
        Iterable<Shipment> shipments = shipmentRepository.findShipmentsByManifestReferenceEquals(manifestReference);
        if (shipments != null) {
            int size = Iterators.size(shipments.iterator());
            if (size > 0)
                return getDistanceMetrics(shipments.iterator().next().getWayBillNumber());
        }
        return null;
    }

    public DistanceMetrics getDistanceMetrics(long waybill) {
        //get current coordinate and compute against the shipment destination time
        Shipment shipment = shipmentRepository.findFirstByWayBillNumberEquals(waybill);
        VehicleLocation location = cacheService.getLastKnownLocationByWaybill(waybill);
        if (location != null) {
            //calculate distance between source and destination
            double distance = geometryHelper.distanceBetweenTwoCoordinates(shipment.getSourceLatitude(), shipment.getSourceLongitude(),
                    shipment.getDestinationLatitude(), shipment.getDestinationLongitude());

            //calculate distance between source and current location
            double distanceFromOrigin = geometryHelper.distanceBetweenTwoCoordinates(location.getLatitude(), location.getLongitude(), shipment.getSourceLatitude(), shipment.getSourceLongitude());
            //distance to destination
            double distanceToDestination = geometryHelper.distanceBetweenTwoCoordinates(location.getLatitude(), location.getLongitude(), shipment.getDestinationLatitude(), shipment.getDestinationLongitude());
            //based on average truck speed of 100km/h
            double estimatedTimeOfArrival = (distanceToDestination / 100);
            String prettyTime = TimeUtil.getPrettyTime(estimatedTimeOfArrival);

            String completeDistance = String.format("%.2f KM", distance);
            String dOrigin = String.format("%.2f KM", distanceFromOrigin);
            String dDestination = String.format("%.2f KM", distanceToDestination);

            return new DistanceMetrics(dOrigin, dDestination, completeDistance, prettyTime,location.getLatitude(),location.getLongitude());
        }
        return null;
    }

}
