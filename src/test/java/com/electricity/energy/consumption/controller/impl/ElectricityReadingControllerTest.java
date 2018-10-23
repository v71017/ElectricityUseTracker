package com.electricity.energy.consumption.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.track.electricity.consumption.cache.ElectricityReadingCacheService;
import com.track.electricity.consumption.controller.impl.ElectricityReadingControllerImpl;
import com.track.electricity.consumption.vo.request.ElectricityReadingData;
import com.track.electricity.consumption.vo.request.SaveOrUpdateReadingRequest;
import com.track.electricity.consumption.vo.response.SaveOrUpdateElectricityReadingResponse;

@RunWith(MockitoJUnitRunner.class)
public class ElectricityReadingControllerTest {

	@InjectMocks
	ElectricityReadingControllerImpl electricityReadingControllerImpl;

	@Mock
	ElectricityReadingCacheService electricityReadingCacheService;

	@Test
	public void testCreateMeteringDataForCustomerForSuceessScenario() {

		Mockito.when(electricityReadingCacheService.addOrUpdateReadings(Mockito.any(SaveOrUpdateReadingRequest.class)))
				.thenReturn(true);

		SaveOrUpdateReadingRequest createMeteringDataRequest = new SaveOrUpdateReadingRequest();
		List<ElectricityReadingData> listOfMeteringData = new ArrayList<ElectricityReadingData>();

		ElectricityReadingData meterReadingData = new ElectricityReadingData();
		meterReadingData.setMonth("JAN");
		meterReadingData.setMeterId("0001");
		meterReadingData.setMeterReading(1000.00);

		listOfMeteringData.add(meterReadingData);
		createMeteringDataRequest.setMeterReadingData(listOfMeteringData);

		BindingResult mocked = Mockito.mock(BindingResult.class);
		ResponseEntity<SaveOrUpdateElectricityReadingResponse> reponse = electricityReadingControllerImpl
				.saveReadings(createMeteringDataRequest, mocked);
		Assert.notNull(reponse);
		Assert.isNull(reponse.getBody().getErrors());

	}
}
