package com.softwareBasket.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

@Entity
public class SoftwareDetails {
	
	@Id
	@GeneratedValue
	private int Id;
	
	private int softwareId;
	
	private String versions;
	
	private boolean paid;
	
	private String cost;
	
	@ManyToOne
	@JoinColumn(name = "software_mapped_id")
	public int getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(int softwareId) {
		this.softwareId = softwareId;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		return "SoftwareDetails [softwareId=" + softwareId + ", versions=" + versions + ", paid=" + paid + ", cost="
				+ cost + "]";
	}
	

}
