package com.track.electricity.consumption.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.track.electricity.consumption.cache.ElectricityReadingCacheService;
import com.track.electricity.consumption.constants.ErrorConstants;
import com.track.electricity.consumption.controller.EnergyConsumptionController;
import com.track.electricity.consumption.vo.request.ElectricityConsumptionRequest;
import com.track.electricity.consumption.vo.request.CacheReading;
import com.track.electricity.consumption.vo.response.ElectricityConsumptionResponse;

/** EnergyConsumptionController controller provides endpoint for retrieving consumption value based on
 *  Meter readings and Rule Set.
 *  
 * @author VIDYA
 *
 */
@Service
public class EnergyConsumptionControllerImpl implements EnergyConsumptionController{

		
		@Autowired
		ElectricityReadingCacheService meterReadingsCacheService;
		
	
		@Override
		public ResponseEntity<ElectricityConsumptionResponse> retrieveConsumption(@RequestParam String meterId)
		{
			ElectricityConsumptionRequest consumptionRequest = new ElectricityConsumptionRequest();
			consumptionRequest.setMeterId(meterId);
			
			CacheReading meterCacheData  = meterReadingsCacheService.getConsumptionValueFromCache(consumptionRequest);
			
			ElectricityConsumptionResponse consumptionResponse = new ElectricityConsumptionResponse();
			if(null!=meterCacheData && null!=meterCacheData.getConsumption()) {
				consumptionResponse.setConsumption(meterCacheData.getConsumption());
				return new ResponseEntity<>(consumptionResponse, HttpStatus.OK);
			}
			consumptionResponse.setMessage(ErrorConstants.NO_DATA_FOUND);
			return new ResponseEntity<>(consumptionResponse, HttpStatus.NO_CONTENT);
		}

	

}
