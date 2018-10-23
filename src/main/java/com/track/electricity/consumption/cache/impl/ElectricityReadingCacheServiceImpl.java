package com.track.electricity.consumption.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.track.electricity.consumption.cache.ElectricityReadingCacheService;
import com.track.electricity.consumption.config.CacheConfig;
import com.track.electricity.consumption.constants.EnergyConsumptionConstants;
import com.track.electricity.consumption.enums.MonthEnum;
import com.track.electricity.consumption.vo.request.ElectricityConsumptionRequest;
import com.track.electricity.consumption.vo.request.ElectricityReadingData;
import com.track.electricity.consumption.vo.request.GetReadingsRequest;
import com.track.electricity.consumption.vo.request.CacheReading;
import com.track.electricity.consumption.vo.request.RemoveReadingsRequest;
import com.track.electricity.consumption.vo.request.SaveOrUpdateReadingRequest;

/**
 * @author VIDYA
 * 
 *         MeterReadingsCacheServiceImpl is a interface containing CRUD
 *         operations for Meter Readings
 *
 */
@Service
public class ElectricityReadingCacheServiceImpl implements ElectricityReadingCacheService {

	private static final String HYPEN_CONSTANT = "-";

	@Autowired
	CacheConfig cacheConfig;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.electricity.energy.consumption.cache.ElectricityReadingCacheService#
	 * addOrUpdateReadings(com.electricity.energy.consumption.model.
	 * SaveOrUpdateReadingRequest)
	 */
	@Override
	@Cacheable(cacheManager = EnergyConsumptionConstants.DATA_CACHE_MANAGER, value = EnergyConsumptionConstants.METER_READINGS_CACHE)
	@Transactional(readOnly = true)
	public boolean addOrUpdateReadings(SaveOrUpdateReadingRequest saveMeterReadingRequest) {

		GuavaCache meterReadingCache = (GuavaCache) cacheConfig.cacheManager()
				.getCache(EnergyConsumptionConstants.METER_READINGS_CACHE);

		for (ElectricityReadingData item : saveMeterReadingRequest.getMeterReadingData()) {
			
			System.out.println(item.getMeterId() + HYPEN_CONSTANT + item.getMonth());
			meterReadingCache.put(item.getMeterId() + HYPEN_CONSTANT + item.getMonth(), item.getMeterReading());
		}
		// saveMeterReadingRequest.getMeterReadingData().stream().forEach(
		// a -> meterReadingCache.put(a.getMeterId() + HYPEN_CONSTANT +
		// a.getMonth(), a.getMeterReading()));

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.electricity.energy.consumption.cache.ElectricityReadingCacheService#
	 * removeReadingsFromCache(com.electricity.energy.consumption.model.
	 * RemoveReadingsRequest)
	 */
	@CacheEvict(cacheManager = EnergyConsumptionConstants.DATA_CACHE_MANAGER, value = EnergyConsumptionConstants.METER_READINGS_CACHE)
	@Transactional(readOnly = true)
	@Override
	public boolean removeReadingsFromCache(RemoveReadingsRequest removeMeterReadingsRequest) {

		GuavaCache meterReadingsCache = (GuavaCache) cacheConfig.cacheManager()
				.getCache(EnergyConsumptionConstants.METER_READINGS_CACHE);

		System.out.println(meterReadingsCache);
		System.out.println(removeMeterReadingsRequest);
		System.out.println(removeMeterReadingsRequest.getMeterId() + HYPEN_CONSTANT + removeMeterReadingsRequest.getMonth());
		meterReadingsCache.evict(
				removeMeterReadingsRequest.getMeterId() + HYPEN_CONSTANT + removeMeterReadingsRequest.getMonth());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.electricity.energy.consumption.cache.ElectricityReadingCacheService#
	 * getReadingsFromCache(com.electricity.energy.consumption.model.
	 * GetReadingsRequest)
	 */
	@Override
	public CacheReading getReadingsFromCache(GetReadingsRequest getMeteringDataRequest) {

		GuavaCache meterReadingCache = (GuavaCache) cacheConfig.cacheManager()
				.getCache(EnergyConsumptionConstants.METER_READINGS_CACHE);

		ValueWrapper value = meterReadingCache
				.get(getMeteringDataRequest.getMeterId() + HYPEN_CONSTANT + getMeteringDataRequest.getMonth());
		if (null != value) {

			CacheReading cacheReading = new CacheReading();
			cacheReading.setMeterId(getMeteringDataRequest.getMeterId());
			cacheReading.setConsumption((Double) value.get());
			return cacheReading;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.electricity.energy.consumption.cache.ElectricityReadingCacheService#
	 * getConsumptionValueFromCache(com.electricity.energy.consumption.
	 * model.ElectricityConsumptionRequest)
	 */
	@Override
	public CacheReading getConsumptionValueFromCache(ElectricityConsumptionRequest consumptionRequest) {

		GuavaCache meterReadingCache = (GuavaCache) cacheConfig.cacheManager()
				.getCache(EnergyConsumptionConstants.METER_READINGS_CACHE);

		Double sum = 0.0;
		for (MonthEnum d : MonthEnum.values()) {
			ValueWrapper value = meterReadingCache.get(consumptionRequest.getMeterId() + HYPEN_CONSTANT + d);

			if (null != value) {
				sum = sum + (Double) value.get();
			}
		}

		CacheReading data = new CacheReading();
		data.setMeterId(consumptionRequest.getMeterId());
		data.setConsumption(sum);

		return data;

		// return null;
	}

}
