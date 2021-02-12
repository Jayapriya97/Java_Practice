package com.softwareBasket.demo.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.softwareBasket.demo.model.SelectedSoftwareAbsDb;

public interface SelectedSoftwareAbsRepo extends CrudRepository<SelectedSoftwareAbsDb,Integer> {
	
	List<SelectedSoftwareAbsDb> findByTicketNo(String ticket);

}
