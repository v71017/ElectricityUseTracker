package com.track.electricity.consumption.vo.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ElectricityConsumptionResponse implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5312709041204445448L;

	private Double consumption;
	
	private String message;

}
