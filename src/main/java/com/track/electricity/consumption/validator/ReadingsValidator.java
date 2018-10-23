package com.track.electricity.consumption.validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.track.electricity.consumption.annotations.ValidReadings;
import com.track.electricity.consumption.config.CacheConfig;
import com.track.electricity.consumption.constants.EnergyConsumptionConstants;
import com.track.electricity.consumption.enums.MonthEnum;
import com.track.electricity.consumption.vo.request.ElectricityReadingData;
import com.track.electricity.consumption.vo.request.SaveOrUpdateReadingRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * MeterReadingsValidator performs validations on meter readings received in the
 * application.
 * 
 * 1) Meter Reading of a month must not be greater than the previous month. 2)
 * Profile data must exists. 3) Consumption for a month must be consistent based
 * on the rule set and tolearance.
 * 
 * @author VIDYA
 *
 */
@Slf4j
@Component
public class ReadingsValidator implements ConstraintValidator<ValidReadings, SaveOrUpdateReadingRequest> {

	@Autowired
	CacheConfig cacheConfig;

	@Override
	public void initialize(ValidReadings constraintAnnotation) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.validation.ConstraintValidator#isValid(java.lang.Object,
	 * javax.validation.ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(SaveOrUpdateReadingRequest request, ConstraintValidatorContext context) {

		boolean valid = true;
		if (null != request && !CollectionUtils.isEmpty(request.getMeterReadingData())) {

			Map<String, List<ElectricityReadingData>> groupedMap = request.getMeterReadingData().stream()
					.collect(Collectors.groupingBy(ElectricityReadingData::getMeterId));

			for (Map.Entry<String, List<ElectricityReadingData>> entry : groupedMap.entrySet()) {

				log.info("::Processing Data for the MeterId::->" + entry.getKey());
				List<ElectricityReadingData> listOfMeteringData = entry.getValue();
				for (ElectricityReadingData temp : listOfMeteringData) {
					if (temp.getMeterReading() < 0)
						return false;
				}
			}
		}
		return valid;
	}

}
