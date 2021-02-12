package com.softwareBasket.demo.model;

public class FinalSoftwareDetails {
	
	private String softwareName;
	private String version;
	private String softwareNameVersion;
	private boolean paid;
	
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSoftwareNameVersion() {
		return softwareNameVersion;
	}
	public void setSoftwareNameVersion(String softwareNameVersion) {
		this.softwareNameVersion = softwareNameVersion;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	@Override
	public String toString() {
		return "FinalSoftwareDetails [softwareName=" + softwareName + ", version=" + version + ", softwareNameVersion="
				+ softwareNameVersion + ", paid=" + paid + "]";
	}


}
