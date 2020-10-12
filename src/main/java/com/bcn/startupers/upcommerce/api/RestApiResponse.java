package com.bcn.startupers.upcommerce.api;

/***
 *  at the moment this class is not used 
 * @author yhuzo
 * @param <T>
 */
public class RestApiResponse <T>{		
	private boolean success;
	protected T responseData;	  
	private Long timestamp;	   
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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public int getElements() {
		return elements;
	}

	public void setElements(int elements) {
		this.elements = elements;
	}

	public T getResponseData() {
		return responseData;
	}	 

}
