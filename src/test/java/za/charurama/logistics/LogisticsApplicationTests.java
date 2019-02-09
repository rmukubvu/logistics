package za.charurama.logistics;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import za.charurama.logistics.models.Consignee;
import za.charurama.logistics.models.Consignor;
import za.charurama.logistics.models.RestResponse;
import za.charurama.logistics.models.Shipment;
import za.charurama.logistics.services.AutoNumberService;
import za.charurama.logistics.services.ConsignService;
import za.charurama.logistics.services.DriverVehicleService;
import za.charurama.logistics.services.ShipmentService;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LogisticsApplicationTests {

	@Autowired
	ShipmentService shipmentService;
	@Autowired
	ConsignService consignService;
	@Autowired
	DriverVehicleService driverVehicleService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void saveConsignor(){
		Consignor actual = new Consignor();
		actual.setName("BOWLER2");
		RestResponse expected = consignService.saveConsignor(actual);
		Assert.assertTrue(expected.isError());
	}

	/*@Test
	public void saveConsignee(){
		Consignee actual = new Consignee();
		actual.setName("DATALABS");
		RestResponse expected = consignService.saveConsigee(actual);
		Assert.assertTrue(expected.isError());
	}*/



	/*@Test
	public void saveShipment(){
		Shipment shipment = new Shipment();
		shipment.setConsigneeId("5c06dec2261cd39458df385f");
		shipment.setSourceLatitude(-26.2237019);
		shipment.setSourceLongitude(28.0697914);
		shipment.setDestinationLatitude(-17.829331);
		shipment.setDestinationLongitude(31.0147496);
		shipment.setLoadedDate(new Date());
		shipment.setVehicleId();
		shipmentService.saveShipment(shipment);
		Assert.assertEquals(10004,sequence);
	}*/

}
