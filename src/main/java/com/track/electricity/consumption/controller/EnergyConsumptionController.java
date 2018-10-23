package com.track.electricity.consumption.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.track.electricity.consumption.vo.response.ElectricityConsumptionResponse;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/consumption")
public interface EnergyConsumptionController {
	
	
	@ApiOperation(value="Retrive Consumption Value",notes="This operation is use to retrieve consumption value of a meter for a"
			+ " particluar month based on rule set and customer data")
	@RequestMapping(value = "/retrieve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ElectricityConsumptionResponse> retrieveConsumption( @RequestParam(value = "meterId", required = true) String meterId);

}

