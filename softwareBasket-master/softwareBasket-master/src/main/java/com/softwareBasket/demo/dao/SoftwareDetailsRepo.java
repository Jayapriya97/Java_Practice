package com.softwareBasket.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softwareBasket.demo.model.SoftwareDetails;

public interface SoftwareDetailsRepo extends CrudRepository<SoftwareDetails,Integer> {

	List<SoftwareDetails> findBySoftwareIdAndVersions(int softwareId, String versions);
}
