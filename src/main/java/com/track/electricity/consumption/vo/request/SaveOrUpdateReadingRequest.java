package com.track.electricity.consumption.vo.request;

import java.io.Serializable;
import java.util.List;

import com.track.electricity.consumption.annotations.ValidReadings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ValidReadings
@Getter
@Setter
@NoArgsConstructor
public class SaveOrUpdateReadingRequest implements Serializable {

	private static final long serialVersionUID = -6608189635177457679L;

	private List<ElectricityReadingData> meterReadingData;

}
