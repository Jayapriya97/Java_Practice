package com.softwareBasket.demo.dao;

import org.springframework.data.repository.CrudRepository;

import com.softwareBasket.demo.model.Alien;

public interface AlienRepo extends CrudRepository<Alien,Integer>
{

}
