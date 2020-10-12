package com.bcn.startupers.upcommerce.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bcn.startupers.upcommerce.service.GooglePlaceService;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.http.ResponseEntity;

/***
 * 
 * @author yhuzo
 */

@RestController
@RequestMapping("/api/places")
public class GooglePlaceController {
	
	private static final Logger log = LoggerFactory.getLogger(GooglePlaceController.class);

	@Autowired
	private GooglePlaceService placeService;

	@GetMapping("/findFromText")
	public ResponseEntity<List<PlacesSearchResult>>findPlacesFromText(@RequestParam String text)  {	

		return ResponseEntity.ok(placeService.findPlacesFromText(text));

	}	
}
