package com.bcn.startupers.upcommerce.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.google.maps.model.PlacesSearchResult;

/**
 * at the moment is not used 
 * @author yhuzo
 *
 */
public class GooglePlaceDto implements Serializable{	
	
	private List<PlacesSearchResult> placesList;
	
	private LocalTime now = LocalTime.now();
	
	public GooglePlaceDto(List<PlacesSearchResult> placesList) {		
		this.placesList=placesList;
	}

	public GooglePlaceDto() {		
	}

	public GooglePlaceDto(List<PlacesSearchResult> placesList, LocalTime now) {		
		this.placesList = placesList;
		this.now = now;
	}

	public List<PlacesSearchResult> getPlacesList() {
		return placesList;
	}

	public void setPlacesList(List<PlacesSearchResult> placesList) {
		this.placesList = placesList;
	}

	public LocalTime getNow() {
		return now;
	}

	public void setNow(LocalTime now) {
		this.now = now;
	}

}
