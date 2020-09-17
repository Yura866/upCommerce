package com.bcn.startupers.upcommerce.api;

import lombok.Getter;
import lombok.Setter;

/***
 *  at the moment this class is not used 
 * @author yhuzo
 * @param <T>
 */
public class RestApiResponse <T>{	
	
		@Getter @Setter
	    private boolean success;
	    protected T responseData;
	    @Getter @Setter
	    private Long timestamp;
	    @Getter @Setter
	    private int elements;

	    public RestApiResponse(boolean isSuccess) {
	    	this.success = isSuccess;
	    	timestamp = System.currentTimeMillis();
	    }

	    public RestApiResponse(T responseData) {
	    	this(true);
			this.setResponseData(responseData);
	    }	

	    public void setResponseData(T responseData) {
			this.responseData = responseData;
			if (responseData == null) {
			    return;
			}
			this.elements = 1;
			if (responseData instanceof java.util.List) {
			    elements = ((java.util.List<?>) responseData).size();
			} else if (responseData instanceof java.util.Map) {
			    elements = ((java.util.Map<?, ?>) responseData).size();
			} else if (responseData instanceof java.util.Set) {
			    elements = ((java.util.Set<?>) responseData).size();
			} else if (responseData instanceof java.util.Collection) {
			    elements = ((java.util.Collection<?>) responseData).size();
			}
	    }	 

}
