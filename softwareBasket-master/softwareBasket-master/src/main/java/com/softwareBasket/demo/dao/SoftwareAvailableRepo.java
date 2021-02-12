package com.softwareBasket.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softwareBasket.demo.model.SoftwareAvailable;

public interface SoftwareAvailableRepo extends CrudRepository<SoftwareAvailable,Integer> {

	List<SoftwareAvailable> findBySoftwareName(String softwareName);
}
