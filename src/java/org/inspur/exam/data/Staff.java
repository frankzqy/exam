package org.inspur.exam.data;

import java.io.Serializable;


public class Staff implements Serializable{

	private String staffId;	
	
	private String name;
	
	private Double wages;
	
	private Double tax;

	private Double afterTax;
	
	public Double getAfterTax() {
		return afterTax;
	}

	public void setAfterTax(Double afterTax) {
		this.afterTax = afterTax;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWages() {
		return wages;
	}

	public void setWages(Double wages) {
		this.wages = wages;
	}

	public Double getTax() {
	    return tax;
	}

	public void setTax(Double tax) {
	    this.tax = tax;
	}	

}
