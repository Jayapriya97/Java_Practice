package com.softwareBasket.demo.model;

import java.util.List;

public class SelectedSoftware {
	
	private Long employeeId;
	private String employeeName;
	private String employeeEmail;
	private String managerName;
	private String managerEmail;
	private String directorName;
	private String directorEmail;
	private List<SelectedSoftwareAbs> selectedSoftware;
	
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getDirectorName() {
		return directorName;
	}
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	public String getDirectorEmail() {
		return directorEmail;
	}
	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}
	public List<SelectedSoftwareAbs> getSelectedSoftware() {
		return selectedSoftware;
	}
	public void setSelectedSoftware(List<SelectedSoftwareAbs> selectedSoftware) {
		this.selectedSoftware = selectedSoftware;
	}
	@Override
	public String toString() {
		return "SelectedSoftware [employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeEmail="
				+ employeeEmail + ", managerName=" + managerName + ", managerEmail=" + managerEmail + ", directorName="
				+ directorName + ", directorEmail=" + directorEmail + ", selectedSoftware=" + selectedSoftware + "]";
	}
	

}
