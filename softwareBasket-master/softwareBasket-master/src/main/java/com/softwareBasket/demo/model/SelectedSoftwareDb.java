package com.softwareBasket.demo.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class SelectedSoftwareDb implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@Id @GeneratedValue(generator="system-uuid")
//	@GenericGenerator(name="system-uuid", strategy = "uuid")
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private Long employeeId;
	private String employeeEmail;
	@Column(name = "ticket_no")
	private String ticketNo;
	private OffsetDateTime date;
	private String managerEmail;
	private String directorEmail;
	private int totalCost;
	
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ticket_no", referencedColumnName = "ticket_no")
	private List<SelectedSoftwareAbsDb> softwareSelected;
	
	public List<SelectedSoftwareAbsDb> getSoftwareSelected() {
		return softwareSelected;
	}
	public void setSoftwareSelected(List<SelectedSoftwareAbsDb> softwareSelected) {
		this.softwareSelected = softwareSelected;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	public OffsetDateTime getDate() {
		return date;
	}
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getManagerEmail() {
		return managerEmail;
	}
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}
	public String getDirectorEmail() {
		return directorEmail;
	}
	public void setDirectorEmail(String directorEmail) {
		this.directorEmail = directorEmail;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	@Override
	public String toString() {
		return "SelectedSoftwareDb [id=" + id + ", employeeId=" + employeeId + ", employeeEmail=" + employeeEmail
				+ ", ticketNo=" + ticketNo + ", date=" + date + ", managerEmail=" + managerEmail + ", directorEmail="
				+ directorEmail + ", totalCost=" + totalCost + ", softwareSelected=" + softwareSelected + "]";
	}
	
	

}
