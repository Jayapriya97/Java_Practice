package com.softwareBasket.demo.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareBasket.demo.dao.SelectedSoftwareAbsRepo;
import com.softwareBasket.demo.dao.SoftwareAvailableRepo;
import com.softwareBasket.demo.dao.SoftwareDetailsRepo;
import com.softwareBasket.demo.dao.SoftwareSelectedRepo;
import com.softwareBasket.demo.model.ApprovalPojo;
import com.softwareBasket.demo.model.FinalSoftwareDetails;
import com.softwareBasket.demo.model.SelectedSoftware;
import com.softwareBasket.demo.model.SelectedSoftwareAbs;
import com.softwareBasket.demo.model.SelectedSoftwareAbsDb;
import com.softwareBasket.demo.model.SelectedSoftwareDb;
import com.softwareBasket.demo.model.SoftwareAvailable;
import com.softwareBasket.demo.model.SoftwareDetails;


@Service
public class SoftwareService {
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@Autowired
	SoftwareAvailableRepo avilableRepo;
	
	@Autowired
	SoftwareDetailsRepo softwareDetailsRepo;
	
	@Autowired
	SoftwareAvailableRepo softwareAvailableRepo;
	
	@Autowired
	SoftwareSelectedRepo softwareSelectedRepo;
	
	@Autowired
	SelectedSoftwareAbsRepo selectedSoftwareAbsRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private JavaMailSender javaMailSender;
	
	public SoftwareService(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}
	
	Iterable<SoftwareAvailable> soft2;
	
	public List<FinalSoftwareDetails> getSoftwareList() throws JsonProcessingException{
		
		
		List<FinalSoftwareDetails> softwareDetailsList = new ArrayList<FinalSoftwareDetails>();
		soft2 = avilableRepo.findAll();
		
		for(SoftwareAvailable swAvailable : soft2) {
			for(SoftwareDetails swDetails : swAvailable.getSoftwareDetails()) {
				
				FinalSoftwareDetails softwareDetails = new FinalSoftwareDetails();
				
				softwareDetails.setSoftwareName(swAvailable.getSoftwareName());
				softwareDetails.setVersion(swDetails.getVersions());
				softwareDetails.setSoftwareNameVersion(swAvailable.getSoftwareName() +"-"+ swDetails.getVersions());
				softwareDetails.setPaid(swDetails.isPaid());
				softwareDetailsList.add(softwareDetails);
			}
		}
		
		String JsonOutput = objectMapper.writeValueAsString(softwareDetailsList);
		System.out.println(JsonOutput);

		return softwareDetailsList;
	}

	public String saveTicketRequest(SelectedSoftware selectedSoftware) {
		
		SelectedSoftwareDb selectedSoftwareDb = new SelectedSoftwareDb();
		
		List<SelectedSoftwareAbsDb> selectedSoftwareAbsDbList = new ArrayList<>();
		SoftwareAvailable softAvailable = new SoftwareAvailable();
		SoftwareDetails softDetails = new SoftwareDetails();
		
		String ticketNo = UUID.randomUUID().toString();
		OffsetDateTime date = OffsetDateTime.now();
		selectedSoftwareDb.setDate(date);
		selectedSoftwareDb.setTicketNo(ticketNo);
		selectedSoftwareDb.setEmployeeId(selectedSoftware.getEmployeeId());
		selectedSoftwareDb.setEmployeeEmail(selectedSoftware.getEmployeeEmail());
		selectedSoftwareDb.setManagerEmail(selectedSoftware.getManagerEmail());
		selectedSoftwareDb.setDirectorEmail(selectedSoftware.getDirectorEmail());
		
		int cost = 0;

		ApprovalPojo managerApproval = new ApprovalPojo();
		List<ApprovalPojo> managerApprovalList = new ArrayList<>();
		ApprovalPojo dhApproval = new ApprovalPojo();
		List<ApprovalPojo> dhApprovalList = new ArrayList<>();
		
		for (SelectedSoftwareAbs selectedSoftwareAbs : selectedSoftware.getSelectedSoftware()) {
			
			SelectedSoftwareAbsDb selectedSoftwareAbsDb = new SelectedSoftwareAbsDb();
			selectedSoftwareAbsDb.setTicketNo(ticketNo);
			selectedSoftwareAbsDb.setSoftware(selectedSoftwareAbs.getSoftwareName());
			selectedSoftwareAbsDb.setSoftwareVersion(selectedSoftwareAbs.getSoftwareVersion());
			
			softAvailable = softwareAvailableRepo.findBySoftwareName(selectedSoftwareAbs.getSoftwareName()).get(0);
			softDetails = softwareDetailsRepo.findBySoftwareIdAndVersions(softAvailable.getSoftwareId(), selectedSoftwareAbs.getSoftwareVersion()).get(0);
			
			if (softDetails.isPaid()) {
			cost = cost + Integer.valueOf(softDetails.getCost());
			}
			System.out.println(cost);
			
			
			int softCost = Integer.valueOf(softDetails.getCost());
			
			if (softCost > 19 && softCost <100 ) {	
				selectedSoftwareAbsDb.setManagerApproval(true);
				selectedSoftwareAbsDb.setApprovedBy("Waiting");
			} else if (softCost > 100) {
				selectedSoftwareAbsDb.setDhApproval(true);
				selectedSoftwareAbsDb.setApprovedBy("Waiting");
			} else {
				selectedSoftwareAbsDb.setManagerApproval(false);
				selectedSoftwareAbsDb.setDhApproval(false);
				selectedSoftwareAbsDb.setApprovedBy("Free");
			}
			
				if (selectedSoftwareAbsDb.isManagerApproval()) {
					managerApproval.setTicketNo(ticketNo);
					managerApproval.setSoftware(selectedSoftwareAbs.getSoftwareName());
					managerApproval.setSoftwareVersion(selectedSoftwareAbs.getSoftwareVersion());
					managerApproval.setCost(softDetails.getCost());
					managerApprovalList.add(managerApproval);
				} else if (selectedSoftwareAbsDb.isDhApproval()) {
					dhApproval.setTicketNo(ticketNo);
					dhApproval.setSoftware(selectedSoftwareAbs.getSoftwareName());
					dhApproval.setCost(softDetails.getCost());
					dhApprovalList.add(managerApproval);
				}
			
			selectedSoftwareAbsDbList.add(selectedSoftwareAbsDb);

		}
		
		selectedSoftwareDb.setSoftwareSelected(selectedSoftwareAbsDbList);
		selectedSoftwareDb.setTotalCost(cost);
		softwareSelectedRepo.save(selectedSoftwareDb);
		
		if (cost > 10000) {
//			mail to DH for approval
			emailSender(selectedSoftwareDb.getDirectorEmail(), "Director", selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo(), dhApprovalList);
		} else if(managerApprovalList.size() > 0 && dhApprovalList.size() > 0) {
//				mail to manager & dh
				emailSender(selectedSoftwareDb.getManagerEmail(),"Manager" , selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo(), managerApprovalList);
				emailSender(selectedSoftwareDb.getDirectorEmail(),"Director" , selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo(), dhApprovalList);
				System.out.println("manager & director email triggered");
			} else if (managerApprovalList.size() > 0) {
//				mail to manager alone
				emailSender(selectedSoftwareDb.getManagerEmail(), "Manager", selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo(), managerApprovalList);
				System.out.println("manager email triggered");
			} else if (dhApprovalList.size() > 0) {
//				mail to DH
				emailSender(selectedSoftwareDb.getDirectorEmail(), "Director", selectedSoftwareDb.getEmployeeEmail(), selectedSoftwareDb.getTicketNo(), dhApprovalList);
				System.out.println("director email triggered");
			}
		return "Success";
	}
	
	private void emailSender(String approverEmail, String approver , String employeeEmail, String ticketNo, List<ApprovalPojo> ApprovalList) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper msgHelper = new MimeMessageHelper(message, "UTF-8");
		String approvedUrl = "http://localhost:8080/approval/" +ticketNo+"/"+approver+"/"+"Approved";
		String rejectedUrl = "http://localhost:8080/approval/" +ticketNo+"/"+approver+"/"+"Rejected";
		String main = "<h2>Please Approve by click below link</h2><br>";
		String htmlContent = "";
		for ( ApprovalPojo pojo : ApprovalList) {
			
			htmlContent = ""+pojo.getSoftware()+"-"+pojo.getSoftwareVersion()+"  Cost-"+pojo.getCost()+"<a href="+approvedUrl+">Approve</a>  <a href="+rejectedUrl+">Denay</a><br>";
		}

		String htmlContent2 = "Approve All - <a href="+approvedUrl+">Approve</a>  <a href="+rejectedUrl+">Denay</a>"; 
		htmlContent = main + htmlContent + htmlContent2;
		try {
			msgHelper.setTo(approverEmail);
			msgHelper.setFrom(fromEmail);
			msgHelper.setCc(employeeEmail);
			msgHelper.setPriority(1);
			msgHelper.setSubject("Software | Approval Required");
			msgHelper.setText(htmlContent, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.getStackTrace();
		}
		javaMailSender.send(message);
	}
	
	public List<SelectedSoftwareDb> getEmployeeTicketDetails(Long employeeId) {
		List<SelectedSoftwareDb> ticketList = new ArrayList<>();
		ticketList = softwareSelectedRepo.findByEmployeeId(employeeId);
		return ticketList;
	}

	public String approveTicker(String tickerNo, String approver, String status) {
		
		List<SelectedSoftwareAbsDb> softwareAbsDb= new ArrayList<>(); 
		softwareAbsDb = selectedSoftwareAbsRepo.findByTicketNo(tickerNo);
		
		for (SelectedSoftwareAbsDb soft : softwareAbsDb) {
			if (soft.isManagerApproval() && approver.equalsIgnoreCase("Manager")) {
				soft.setApprovedBy(status);
			} else if (soft.isDhApproval() && approver.equalsIgnoreCase("Director")) {
				soft.setApprovedBy(status);
			}
		}
		selectedSoftwareAbsRepo.saveAll(softwareAbsDb);
		return "success";
	}
}
