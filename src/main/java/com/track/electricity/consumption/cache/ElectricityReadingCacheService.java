package com.track.electricity.consumption.cache;

import com.track.electricity.consumption.vo.request.ElectricityConsumptionRequest;
import com.track.electricity.consumption.vo.request.GetReadingsRequest;
import com.track.electricity.consumption.vo.request.CacheReading;
import com.track.electricity.consumption.vo.request.RemoveReadingsRequest;
import com.track.electricity.consumption.vo.request.SaveOrUpdateReadingRequest;

/**
 * @author VIDYA
 * 
 * ElectricityReadingCacheService is a interface containing CRUD operations for Meter Readings
 *
 */
public interface ElectricityReadingCacheService {
	
	/** addOrUpdateReadings operation add/updates meter Readings in cache
	 * @param saveOrUpdateReadingRequest
	 * @return
	 */
	public boolean addOrUpdateReadings(SaveOrUpdateReadingRequest saveOrUpdateReadingRequest);
	
	/** removeReadingsFromCache removes meter readings from cache.
	 * @param removeReadingsRequest
	 * @return
	 */
	public boolean removeReadingsFromCache(RemoveReadingsRequest removeReadingsRequest);
	
	/** getReadingsFromCache fetches meter readings from cache.
	 * @param getReadingsRequest
	 * @return
	 */
	public CacheReading getReadingsFromCache(GetReadingsRequest getReadingsRequest);
	/**
	 * getConsumptionValueFromCache retrives consumption value for meter readings
	 * @param electricityConsumptionRequest
	 * @return
	 */
	public CacheReading getConsumptionValueFromCache(ElectricityConsumptionRequest electricityConsumptionRequest);


}