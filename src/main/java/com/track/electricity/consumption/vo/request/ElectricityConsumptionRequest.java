package com.track.electricity.consumption.vo.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ElectricityConsumptionRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3271515983419188169L;

	private String meterId;

}
