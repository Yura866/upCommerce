package com.bcn.startupers.upcommerce.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcn.startupers.upcommerce.dto.GooglePlaceDTO;
import com.bcn.startupers.upcommerce.service.GooglePlaceService;
import com.bcn.startupers.upcommerce.utils.GoogleApiUtils;
import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.FindPlaceFromTextRequest.InputType;
import com.google.maps.FindPlaceFromTextRequest.LocationBiasIP;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlacesSearchResult;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author yhuzo
 *
 */

@Slf4j
@Service
public class GooglePlaceServiceImpl implements GooglePlaceService {
	
	private static Logger log = LoggerFactory.getLogger(GooglePlaceServiceImpl.class);

	@Autowired
	private GoogleApiUtils googleApiUtils;	

	
	@Override
	public List<PlacesSearchResult> findPlacesFromText(String inputText) {		
		FindPlaceFromText results = null;
		try {
			final String inputTextEncoded = googleApiUtils.encodeURL(inputText);
			final GeoApiContext context = googleApiUtils.getGeoApiContext();
			results = PlacesApi.findPlaceFromText(context, inputTextEncoded, InputType.TEXT_QUERY)
					.fields(
							FindPlaceFromTextRequest.FieldMask.ID,
							FindPlaceFromTextRequest.FieldMask.PLACE_ID,
							FindPlaceFromTextRequest.FieldMask.FORMATTED_ADDRESS,
							FindPlaceFromTextRequest.FieldMask.NAME,
							FindPlaceFromTextRequest.FieldMask.RATING,
							FindPlaceFromTextRequest.FieldMask.OPENING_HOURS,							
							FindPlaceFromTextRequest.FieldMask.BUSINESS_STATUS,
							FindPlaceFromTextRequest.FieldMask.GEOMETRY,
							FindPlaceFromTextRequest.FieldMask.ICON,							
							FindPlaceFromTextRequest.FieldMask.PRICE_LEVEL,
							FindPlaceFromTextRequest.FieldMask.TYPES)
					.locationBias(new LocationBiasIP())
					.await();			
			
		} catch (ApiException e) {	
			log.error("[ GooglePlaceServiceImpl, ApiException ] - Unable to find place from text ",e);		
		} catch (InterruptedException e) {			
			log.error("[ GooglePlaceServiceImpl, InterruptedException ] - Unable to find place from text ",e);
		} catch (IOException e) {			
			log.error("[ GooglePlaceServiceImpl, IOException ] - Unable to find place from text ",e);
		}

		return getPlacesList(results);
	}
	
	private List<PlacesSearchResult> getPlacesList(FindPlaceFromText results) {			
		List <PlacesSearchResult> placesList = new ArrayList<>();
		if (results != null && results.candidates.length>0) {
				placesList = Arrays
				.stream(results.candidates)
				.collect(Collectors.toList());	
			
		}		
		return placesList;		
	}
}
