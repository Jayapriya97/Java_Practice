package com.softwareBasket.demo.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareBasket.demo.dao.AlienRepo;
import com.softwareBasket.demo.dao.SoftwareAvailableRepo;
import com.softwareBasket.demo.dao.SoftwareDetailsRepo;
import com.softwareBasket.demo.model.Alien;
import com.softwareBasket.demo.model.FinalSoftwareDetails;
import com.softwareBasket.demo.model.SelectedSoftware;
import com.softwareBasket.demo.model.SelectedSoftwareAbsDb;
import com.softwareBasket.demo.model.SelectedSoftwareDb;
import com.softwareBasket.demo.model.SoftwareAvailable;
import com.softwareBasket.demo.model.SoftwareDetails;
import com.softwareBasket.demo.service.SoftwareService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SoftwareBasketController
{
	@Autowired
	AlienRepo repo;
	
	@Autowired
	SoftwareAvailableRepo avilableRepo;
	
	@Autowired
	SoftwareDetailsRepo detailsRepo;
	
	@Autowired
	SoftwareService softwareService;
	
	
	
//	This API is used to get the List of Software Available
	@GetMapping("/getSoftwares")
	@ApiOperation(value = "/getSoftwares API to get the list of softwares")
	public List<FinalSoftwareDetails> getSoftware() throws JsonProcessingException {
		
		List<FinalSoftwareDetails> softwareDetails = new ArrayList<FinalSoftwareDetails>();
		softwareDetails = softwareService.getSoftwareList();
		
		return softwareDetails;
	}
	
	@PostMapping("/chosedSoftware")
	@ApiOperation(value = "/chosedSoftware API which gets values after submit")
	public String chosedSoftware(@RequestBody SelectedSoftware selectedSoftware) {
		
		String val = softwareService.saveTicketRequest(selectedSoftware);
		
		return val;
	}
	
	@GetMapping(value = "/employeeTicketDetails/{employeeId}")
	@ApiOperation( value = "/employeeTicketDetails which get triggered when we go to the dashboard")
	public List<SelectedSoftwareDb> employeeTicketDetails(@PathVariable Long employeeId) {
		List<SelectedSoftwareDb> ticketList = new ArrayList<>();
		ticketList = softwareService.getEmployeeTicketDetails(employeeId);
		return ticketList;
	}
	
	@GetMapping(value = "/approval/{ticketNo}/{approver}/{status}")
	@ApiOperation(value = "/approval to get approved by manager/DH")
	public String ticketApproval(@PathVariable String ticketNo, @PathVariable String approver, @PathVariable String status) {
		
		String result = softwareService.approveTicker(ticketNo, approver, status);
		
		return result;	
	}
}
