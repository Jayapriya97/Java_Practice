package com.softwareBasket.demo.model;

public class SelectedSoftwareAbs {
	
	private String softwareName;
	private String softwareVersion;
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	@Override
	public String toString() {
		return "SelectedSoftwareAbs [softwareName=" + softwareName + ", softwareVersion=" + softwareVersion + "]";
	}

	
}
