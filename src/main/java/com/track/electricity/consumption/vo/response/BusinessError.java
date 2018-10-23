package com.track.electricity.consumption.vo.response;

/**BusinessError class provides error message.
 * 
 * @author VIDYA
 *
 */
public class BusinessError {
	
	public BusinessError(String message) {
		super();
		this.message = message;
	}

	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
}