package com.softwareBasket.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class SoftwareAvailable {
	
	@Id
	@GeneratedValue
	private int Id;
	
	private int softwareId;
	
	private String softwareName;

	@OneToMany(targetEntity = SoftwareDetails.class, mappedBy = "softwareId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<SoftwareDetails> softwareDetails;

	public int getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(int softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	
	public List<SoftwareDetails> getSoftwareDetails() {
		return softwareDetails;
	}

	public void setSoftwareDetails(List<SoftwareDetails> softwareDetails) {
		this.softwareDetails = softwareDetails;
	}

	@Override
	public String toString() {
		return "SoftwareAvailable [softwareId=" + softwareId + ", softwareName=" + softwareName + ", softwareDetails="
				+ softwareDetails + "]";
	}
	
}
