package com.bcn.startupers.upcommerce.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.maps.GeoApiContext;



@Component
public class GoogleApiUtils {
	
	private static Logger log = LoggerFactory.getLogger(GoogleApiUtils.class);
	
	public static final String URL_ENCODER_UTF8="UTF-8";	


	@Value("${google.maps.geocode.core.url}")
	private String url;	
	
	@Value("${google.maps.geocode.api.places.key}")
	private String placesApiKey;
	
	public String encodeURL(String inputText) throws UnsupportedEncodingException {			
		return URLEncoder.encode(inputText, URL_ENCODER_UTF8);			
	}
	
	public GeoApiContext getGeoApiContext() {		
		return new GeoApiContext.Builder()
	    	    .apiKey(placesApiKey)
	    	    .build();
	}
}
