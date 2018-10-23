package com.track.electricity.consumption.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.track.electricity.consumption.vo.request.RemoveReadingsRequest;
import com.track.electricity.consumption.vo.request.SaveOrUpdateReadingRequest;
import com.track.electricity.consumption.vo.response.GetElectricityReadingsResponse;
import com.track.electricity.consumption.vo.response.RemoveElectricityReadingResponse;
import com.track.electricity.consumption.vo.response.SaveOrUpdateElectricityReadingResponse;

import io.swagger.annotations.ApiOperation;

/**
 * ElectricityReadingController provides CRUD operations for electricity
 * readings data. 1)Save electricity readings. 2)Query electricity Readings.
 * 3)Delete electricity readings. 4)Update electricity readings.
 * 
 * @author VIDYA
 *
 */
@RestController
@RequestMapping("/electricityReading")
public interface ElectricityReadingController {

	/**
	 * saveReadings operation save customer data in the system.
	 * 
	 * @param saveOrUpdateReadingRequest
	 * @param errors
	 * @return
	 */
	@ApiOperation(value = "Save Electricity data", notes = "This API saved Electricity readings in the system after performing validations")
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveOrUpdateElectricityReadingResponse> saveReadings(
			@RequestBody @Valid SaveOrUpdateReadingRequest saveOrUpdateReadingRequest, BindingResult errors);

	/**
	 * getReadings operation fetches electricity readings data from the system.
	 * 
	 * @param meterId
	 * @param month
	 * @return
	 */
	@ApiOperation(value = "Fetch Electricity Data", notes = "This API fetches Electricity readings from the system")
	@RequestMapping(value = "/query", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GetElectricityReadingsResponse> getReadings(
			@RequestParam(value = "meterId", required = true) String meterId,
			@RequestParam(value = "month", required = true) String month);

	
	/**
	 * getReadings operation fetches electricity readings data from the system.
	 * 
	 * @param meterId
	 * @param month
	 * @return
	 */
	@ApiOperation(value = "Save Electricity data", notes = "This API saved Electricity readings in the system after performing validations")
	@RequestMapping(value = "/delete1", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SaveOrUpdateElectricityReadingResponse> removeReadings1(
			@RequestBody @Valid SaveOrUpdateReadingRequest saveOrUpdateReadingRequest, BindingResult errors);
	
	
	
	
	/**
	 * removeReadings operation removes customer meter reading data from the
	 * system.
	 * 
	 * @param removeReadingsRequest
	 * @return
	 */
	//@ApiOperation(value = "Remove Electricity Data", notes = "This API removes Electricity readings from the system")
	//@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//public ResponseEntity<RemoveElectricityReadingResponse> removeReadings(
			
			//@RequestBody @Valid RemoveReadingsRequest removeReadingsRequest);

	
	@ApiOperation(value="Remove Customer Data",notes="This API removes Meter readings from the system")
	@RequestMapping(value = "/evict", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RemoveElectricityReadingResponse> removeReadings(@RequestBody @Valid RemoveReadingsRequest removeReadingsRequest, BindingResult errors);
	
	
	
	
	//@ApiOperation(value = "Update Electricity Data", notes = "This API updates Electricity Readings in the system")
	//@RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	//public ResponseEntity<SaveOrUpdateElectricityReadingResponse> updateReadings(
			//@RequestBody @Valid SaveOrUpdateReadingRequest updateReadings, BindingResult errors);

}
