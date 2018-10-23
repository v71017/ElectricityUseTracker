package com.track.electricity.consumption.controller.impl;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;

import com.track.electricity.consumption.cache.ElectricityReadingCacheService;
import com.track.electricity.consumption.constants.ErrorConstants;
import com.track.electricity.consumption.controller.ElectricityReadingController;
import com.track.electricity.consumption.vo.request.GetReadingsRequest;
import com.track.electricity.consumption.vo.request.CacheReading;
import com.track.electricity.consumption.vo.request.RemoveReadingsRequest;
import com.track.electricity.consumption.vo.request.SaveOrUpdateReadingRequest;
import com.track.electricity.consumption.vo.response.BusinessError;
import com.track.electricity.consumption.vo.response.GetElectricityReadingsResponse;
import com.track.electricity.consumption.vo.response.RemoveElectricityReadingResponse;
import com.track.electricity.consumption.vo.response.SaveOrUpdateElectricityReadingResponse;

/**
 * MeterReadingsControllerImpl provides CRUD operations for meter readings data.
 * 1)Create Meter readings. 2)Fetch Meter Readings. 3)Remove meter readings.
 * 4)Update meter readings.
 * 
 * @author VIDYA
 *
 */
@Service
public class ElectricityReadingControllerImpl implements ElectricityReadingController {

	@Autowired
	ElectricityReadingCacheService meterReadingsCacheService;

	/**
	 * saveReadings operation save Electricity data in the system.
	 * 
	 * @param saveMeterReadingRequest
	 * @param errors
	 * @return
	 */
	@Override
	public ResponseEntity<SaveOrUpdateElectricityReadingResponse> saveReadings(
			@RequestBody @Valid SaveOrUpdateReadingRequest saveMeterReadingRequest, BindingResult errors) {

		if (errors.hasErrors()) {
			List<BusinessError> errorList = new ArrayList<BusinessError>();
			for (ObjectError er : errors.getAllErrors()) {
				BusinessError br = new BusinessError(er.getDefaultMessage());
				errorList.add(br);
			}

			if (!CollectionUtils.isEmpty(saveMeterReadingRequest.getMeterReadingData())
					&& meterReadingsCacheService.addOrUpdateReadings(saveMeterReadingRequest)) {
				return new ResponseEntity<>(new SaveOrUpdateElectricityReadingResponse(true, errorList), HttpStatus.OK);
			}
			return new ResponseEntity<>(new SaveOrUpdateElectricityReadingResponse(false, errorList),
					HttpStatus.BAD_REQUEST);
		}

		else if (meterReadingsCacheService.addOrUpdateReadings(saveMeterReadingRequest)) {
			return new ResponseEntity<>(new SaveOrUpdateElectricityReadingResponse(true), HttpStatus.OK);
		}

		return new ResponseEntity<>(new SaveOrUpdateElectricityReadingResponse(false),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * getReadings operation fetches Electricity readings data from the system.
	 * 
	 * @param meterId
	 * @param month
	 * @return
	 */
	@Override
	public ResponseEntity<GetElectricityReadingsResponse> getReadings(String meterId, String month) {

		GetReadingsRequest getMeteringDataRequest = BuildCacheServiceRequest(meterId, month);
		CacheReading meterCache = meterReadingsCacheService.getReadingsFromCache(getMeteringDataRequest);
		GetElectricityReadingsResponse getMeteringDataResponse = new GetElectricityReadingsResponse();
		if (null != meterCache) {
			getMeteringDataResponse.setMeterId(meterCache.getMeterId());
			getMeteringDataResponse.setMeterReading(meterCache.getConsumption());

			return new ResponseEntity<>(getMeteringDataResponse, HttpStatus.OK);
		}

		BusinessError br = new BusinessError(ErrorConstants.NO_DATA_FOUND);
		getMeteringDataResponse.setBusinessError(br);
		return new ResponseEntity<>(getMeteringDataResponse, HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<SaveOrUpdateElectricityReadingResponse> removeReadings1(
			@RequestBody @Valid SaveOrUpdateReadingRequest saveMeterReadingRequest, BindingResult errors) {

		System.out.println(saveMeterReadingRequest.getMeterReadingData().get(0).getMeterId());
		System.out.println(saveMeterReadingRequest.getMeterReadingData().get(0).getMonth());

		RemoveReadingsRequest removeReadingsRequest = new RemoveReadingsRequest();

		removeReadingsRequest.setMeterId(saveMeterReadingRequest.getMeterReadingData().get(0).getMeterId());
		removeReadingsRequest.setMonth(saveMeterReadingRequest.getMeterReadingData().get(0).getMonth());

		if (meterReadingsCacheService.removeReadingsFromCache(removeReadingsRequest)) {
			return new ResponseEntity<>(new SaveOrUpdateElectricityReadingResponse(Boolean.TRUE), HttpStatus.OK);
		}
		return new ResponseEntity<>(new SaveOrUpdateElectricityReadingResponse(Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * removeMeterReadings operation removes Electricity reading data from the
	 * system.
	 * 
	 * @param removeReadings
	 * @return
	 */
	@Override
	public ResponseEntity<RemoveElectricityReadingResponse> removeReadings(RemoveReadingsRequest removeReadingsRequest, BindingResult errors) {

		System.out.println(removeReadingsRequest.getMeterId());
		System.out.println(removeReadingsRequest.getMonth());

		if (meterReadingsCacheService.removeReadingsFromCache(removeReadingsRequest)) {
			return new ResponseEntity<>(new RemoveElectricityReadingResponse(Boolean.TRUE), HttpStatus.OK);
		}
		return new ResponseEntity<>(new RemoveElectricityReadingResponse(Boolean.FALSE),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

	/**
	 * BuildCacheServiceRequest build request fro fetching meter readings
	 * 
	 * @param meterId
	 * @param month
	 * @return
	 */
	private GetReadingsRequest BuildCacheServiceRequest(String meterId, String month) {
		GetReadingsRequest getMeteringDataRequest = new GetReadingsRequest();
		getMeteringDataRequest.setMeterId(meterId);
		getMeteringDataRequest.setMonth(month);
		return getMeteringDataRequest;
	}

}