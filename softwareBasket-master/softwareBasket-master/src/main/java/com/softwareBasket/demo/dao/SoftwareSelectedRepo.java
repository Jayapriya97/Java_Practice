package com.softwareBasket.demo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.softwareBasket.demo.model.SelectedSoftwareDb;

public interface SoftwareSelectedRepo extends CrudRepository<SelectedSoftwareDb,Integer> {

	List<SelectedSoftwareDb> findByEmployeeId(Long employeeId);
}
