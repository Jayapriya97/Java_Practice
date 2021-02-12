package com.softwareBasket.demo.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SelectedSoftwareAbsDb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "ticket_no")
	private String ticketNo;
	private String software;
	private String softwareVersion;
	private boolean managerApproval;
	private boolean dhApproval;
	private String approvedBy;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public String getSoftware() {
		return software;
	}
	public void setSoftware(String software) {
		this.software = software;
	}
	public String getSoftwareVersion() {
		return softwareVersion;
	}
	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public boolean isManagerApproval() {
		return managerApproval;
	}
	public void setManagerApproval(boolean managerApproval) {
		this.managerApproval = managerApproval;
	}
	public boolean isDhApproval() {
		return dhApproval;
	}
	public void setDhApproval(boolean dhApproval) {
		this.dhApproval = dhApproval;
	}
	@Override
	public String toString() {
		return "SelectedSoftwareAbsDb [id=" + id + ", ticketNo=" + ticketNo + ", software=" + software
				+ ", softwareVersion=" + softwareVersion + ", managerApproval=" + managerApproval + ", dhApproval="
				+ dhApproval + ", approvedBy=" + approvedBy + "]";
	}	

}
