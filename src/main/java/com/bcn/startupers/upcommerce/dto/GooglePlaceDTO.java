package com.bcn.startupers.upcommerce.dto;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.google.maps.model.PlacesSearchResult;

import lombok.Getter;
import lombok.Setter;

/**
 * at the moment is not used 
 * @author yhuzo
 *
 */
public class GooglePlaceDTO implements Serializable{
	
	@Getter @Setter
	private List<PlacesSearchResult> placesList;
	@Getter @Setter
	private LocalTime now = LocalTime.now();
	
	public GooglePlaceDTO(List<PlacesSearchResult> placesList) {		
		this.placesList=placesList;
	}

}
