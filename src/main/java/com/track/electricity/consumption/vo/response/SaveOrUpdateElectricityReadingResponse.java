package com.track.electricity.consumption.vo.response;

import java.io.Serializable;
import java.util.List;

public class SaveOrUpdateElectricityReadingResponse implements Serializable {


	public SaveOrUpdateElectricityReadingResponse(boolean sUCCESS) {
		super();
		SUCCESS = sUCCESS;
	}

	public SaveOrUpdateElectricityReadingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SaveOrUpdateElectricityReadingResponse(boolean sUCCESS, List<BusinessError> errors) {
		super();
		SUCCESS = sUCCESS;
		this.errors = errors;
	}

	private static final long serialVersionUID = -7658355027254848510L;

	private boolean SUCCESS;

	private List<BusinessError> errors;

	public boolean isSUCCESS() {
		return SUCCESS;
	}

	public void setSUCCESS(boolean sUCCESS) {
		SUCCESS = sUCCESS;
	}

	public List<BusinessError> getErrors() {
		return errors;
	}

	public void setErrors(List<BusinessError> errors) {
		this.errors = errors;
	}


}
