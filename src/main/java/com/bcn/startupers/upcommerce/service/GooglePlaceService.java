package com.bcn.startupers.upcommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.google.maps.model.PlacesSearchResult;
/**
 * 
 * @author yhuzo
 *
 */
@Service
public interface GooglePlaceService {		
	List<PlacesSearchResult> findPlacesFromText(String inputText);

}
