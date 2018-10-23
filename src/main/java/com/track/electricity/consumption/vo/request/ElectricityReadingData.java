package com.track.electricity.consumption.vo.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ElectricityReadingData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553197647744233417L;

	private String meterId;

	private String month;

	private Double meterReading;

}
