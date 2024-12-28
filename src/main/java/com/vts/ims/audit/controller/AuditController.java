package com.vts.ims.audit.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vts.ims.audit.dto.AuditCheckListDTO;
import com.vts.ims.audit.dto.AuditRescheduleDto;
import com.vts.ims.audit.dto.AuditScheduleDto;
import com.vts.ims.audit.dto.AuditScheduleListDto;
import com.vts.ims.audit.dto.AuditScheduleRemarksDto;
import com.vts.ims.audit.dto.AuditTeamEmployeeDto;
import com.vts.ims.audit.dto.AuditTeamMembersDto;
import com.vts.ims.audit.dto.AuditTotalTeamMembersDto;
import com.vts.ims.audit.dto.AuditTranDto;
import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.AuditorTeamDto;
import com.vts.ims.audit.dto.CheckListDto;
import com.vts.ims.audit.dto.IqaAuditeeDto;
import com.vts.ims.audit.dto.IqaAuditeeListDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.AuditObservation;
import com.vts.ims.audit.model.AuditTeam;
import com.vts.ims.audit.service.AuditService;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;
import com.vts.ims.util.Response;



@CrossOrigin("*")
@RestController
public class AuditController {

	private static final Logger logger = LogManager.getLogger(AuditController.class);
	
	@Value("${appStorage}")
	private String storageDrive ;
	
	@Autowired
	AuditService auditService;
	
	
	@PostMapping(value = "/auditor-list", produces = "application/json")
	public ResponseEntity<List<AuditorDto>> auditorList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside auditorList" );
			List<AuditorDto> dto=auditService.getAuditorList();
			return new ResponseEntity<List<AuditorDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching auditorList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/get-employee-list", produces = "application/json")
	public ResponseEntity<List<EmployeeDto>> getEmployelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getEmployelist" );
			List<EmployeeDto> dto=auditService.getEmployelist();
			return new ResponseEntity<List<EmployeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getEmployelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/insert-auditor-employees", produces = "application/json")
	public ResponseEntity<String> insertAuditorEmployees(@RequestHeader String username, @RequestBody String[] empIds) throws Exception {
		try {
			logger.info(new Date() + " Inside insert-selected-employees" );
			 long insertAuditor=auditService.insertAuditor(empIds,username);
			 if(insertAuditor > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in insert-selected-employees"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	
	@RequestMapping (value="/auditor-inactive", method=RequestMethod.POST,produces="application/json")
    public ResponseEntity<String> auditorInactive(@RequestBody AuditorDto auditordto, @RequestHeader  String username) throws Exception{
   		 try {
   			logger.info("{} Inside auditor-inactive");
   			long result=auditService.updateAuditor(auditordto,username);
		    if(result > 0) {
		    	return new ResponseEntity<String>("200" , HttpStatus.OK);
		    }else {
		    	return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
		    }
   		 } catch (Exception e) {
  			  logger.error(new Date() +" error in auditor-inactive");
  		         e.printStackTrace();
  		       return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
  		}
	}
	
	
	@PostMapping(value = "/iqa-list", produces = "application/json")
	public ResponseEntity<List<IqaDto>> iqalist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside iqalist" );
			List<IqaDto> dto=auditService.getIqaList();
			return new ResponseEntity<List<IqaDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching iqalist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/insert-iqa", produces = "application/json")
	public ResponseEntity<String> insertIqa(@RequestHeader String username, @RequestBody IqaDto iqadto) throws Exception {
		try {
			logger.info(new Date() + " Inside insert-iqa" );
			long insertIqa=auditService.insertIqa(iqadto,username);
			 if(insertIqa > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in insert-iqa"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	
	@PostMapping(value = "/auditee-list", produces = "application/json")
	public ResponseEntity<List<AuditeeDto>> auditeelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside auditeelist" );
			List<AuditeeDto> dto=auditService.getAuditeeList();
			return new ResponseEntity<List<AuditeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching auditeelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/iqa-auditee-list", produces = "application/json")
	public ResponseEntity<List<IqaAuditeeListDto>> getIqaAuditeelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside iqaAuditeelist" );
			List<IqaAuditeeListDto> dto=auditService.getIqaAuditeelist();
			return new ResponseEntity<List<IqaAuditeeListDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching iqaAuditeelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-division-list", produces = "application/json")
	public ResponseEntity<List<DivisionMasterDto>> getDivisionlist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getDivisionlist" );
			List<DivisionMasterDto> dto=auditService.getDivisionMaster();
			return new ResponseEntity<List<DivisionMasterDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getDivisionlist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-division-group-list", produces = "application/json")
	public ResponseEntity<List<DivisionGroupDto>> getDivisionGrouplist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getDivisionGrouplist" );
			List<DivisionGroupDto> dto=auditService.getDivisionGroupList();
			return new ResponseEntity<List<DivisionGroupDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getDivisionGrouplist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-project-list", produces = "application/json")
	public ResponseEntity<List<ProjectMasterDto>> getProjectlist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getProjectlist" );
			List<ProjectMasterDto> dto=auditService.getProjectMasterList();
			return new ResponseEntity<List<ProjectMasterDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getProjectlist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	@PostMapping(value = "/auditee-insert", produces = "application/json")
	public ResponseEntity<String> auditeeinsert(@RequestHeader String username, @RequestBody AuditeeDto auditeedto) throws Exception {
		try {
			logger.info(new Date() + " Inside auditeeinsert" );
			long insertAuditee=auditService.insertAuditee(auditeedto,username);
			 if(insertAuditee > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in auditeeinsert"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	
	@RequestMapping (value="/auditee-inactive", method=RequestMethod.POST,produces="application/json")
    public ResponseEntity<String> auditeeinactive(@RequestBody String auditeeId, @RequestHeader  String username) throws Exception{
   		 try {
   			logger.info("{} Inside auditee-inactive");
   			long result=auditService.updateAuditee(auditeeId,username);
		    if(result > 0) {
		    	return new ResponseEntity<String>("200" , HttpStatus.OK);
		    }else {
		    	return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
		    }
   		 } catch (Exception e) {
  			  logger.error(new Date() +" error in auditee-inactive");
  		         e.printStackTrace();
  		       return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
  		}
	}
	
	@PostMapping(value = "/insert-audit-schedule", produces = "application/json")
	public ResponseEntity<String> insertAuditSchedule(@RequestHeader String username, @RequestBody AuditScheduleDto auditScheduleDto) throws Exception {
		try {
			logger.info( " Inside insert-audit-schedule" );
			 long insertAuditor=auditService.insertAuditSchedule(auditScheduleDto,username);
			 if(insertAuditor > 0) {
				 return new ResponseEntity<String>("Audit schedule Added Successfully" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("Audit schedule Added Unsuccessful" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error("error in insert-audit-schedule"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	@PostMapping(value = "/edit-audit-schedule", produces = "application/json")
	public ResponseEntity<String> editAuditSchedule(@RequestHeader String username, @RequestBody AuditScheduleDto auditScheduleDto) throws Exception {
		try {
			logger.info( " Inside edit-audit-schedule" );
			 long result=auditService.editAuditSchedule(auditScheduleDto,username);
			 if(result > 0) {
				 return new ResponseEntity<String>("Audit schedule Edited Successfully" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("Audit schedule Edited Unsuccessful" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error("error in edit-audit-schedule"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	@PostMapping(value = "/insert-audit-reschedule", produces = "application/json")
	public ResponseEntity<String> insertAuditReSchedule(@RequestHeader String username, @RequestBody AuditRescheduleDto auditRescheduleDto) throws Exception {
		try {
			logger.info(" Inside insert-audit-reSchedule" );
			 long insertAuditor=auditService.insertAuditReSchedule(auditRescheduleDto,username);
			 if(insertAuditor > 0) {
				 return new ResponseEntity<String>("Audit Rescheduled Successfully" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("Audit Rescheduled Unsuccessful" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error("error in insert-audit-reSchedule"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	@PostMapping(value = "/schedule-list", produces = "application/json")
	public ResponseEntity<List<AuditScheduleListDto>> getScheduleList(@RequestHeader String username) throws Exception {
		try {
			logger.info(" Inside scheduleList" );
			List<AuditScheduleListDto> dto=auditService.getScheduleList();
			return new ResponseEntity<List<AuditScheduleListDto>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching scheduleList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/audit-team-list", produces = "application/json")
	public ResponseEntity<List<AuditorTeamDto>> getAuditTeamList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getAuditTeamList" );
			List<AuditorTeamDto> dto=auditService.getAuditTeamMainList();
			return new ResponseEntity<List<AuditorTeamDto>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getAuditTeamList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/forward-schedule", produces = "application/json")
	public ResponseEntity<Response> forwardSchedule(@RequestHeader String username, @RequestBody List<Long> auditScheduleIds) throws Exception {
		try {
			logger.info( " Inside forward-schedule" );
			 long result=auditService.forwardSchedule(auditScheduleIds,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Audit Schedule Forwarded Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Audit Schedule Forwarded Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in forward-schedule"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/auditor-forward", produces = "application/json")
	public ResponseEntity<Response> auditorForward(@RequestHeader String username, @RequestBody AuditScheduleListDto auditScheduleListDto) throws Exception {
		try {
			logger.info( " Inside auditor-forward" );
			 long result=auditService.auditorForward(auditScheduleListDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("CheckList Forwarded Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("CheckList Forwarded Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in auditor-forward"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/schedule-mail-send", produces = "application/json")
	public ResponseEntity<Response> scheduleMailSend(@RequestHeader String username, @RequestBody List<AuditScheduleListDto> auditScheduleListDto) throws Exception {
		try {
			logger.info(" Inside schedule-mail-send" );
			 long result=auditService.scheduleMailSend(auditScheduleListDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Audit Schedule Mail Send Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("audit Schedule Mail Send Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in schedule-mail-send"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/get-total-team-members-list", produces = "application/json")
	public ResponseEntity<List<AuditTotalTeamMembersDto>> getTotalTeamMembersList(@RequestHeader String username) throws Exception {
		try {
			logger.info(" Inside get-total-team-members-list" );
			List<AuditTotalTeamMembersDto> dto=auditService.getTotalTeamMembersList();
			return new ResponseEntity<List<AuditTotalTeamMembersDto>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching get-total-team-members-list: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/reschedule-mail-send", produces = "application/json")
	public ResponseEntity<Response> rescheduleMailSend(@RequestHeader String username, @RequestBody AuditRescheduleDto auditRescheduleDto) throws Exception {
		try {
			 logger.info(" Inside reschedule-mail-send" );
			 long result=auditService.rescheduleMailSend(auditRescheduleDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Audit reschedule Mail Send Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Audit reschedule Mail Send Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in reschedule-mail-send"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	
	
	@PostMapping(value = "/audit-team-insert", produces = "application/json")
	public ResponseEntity<String> auditteaminsert(@RequestHeader String username, @RequestBody AuditorTeamDto auditorteamdto) throws Exception {
		try {
			logger.info(new Date() + " Inside audit-team-insert" );
			long insertAuditTeam=auditService.insertAuditTeam(auditorteamdto,username);
			 if(insertAuditTeam > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in audit-team-insert"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	@PostMapping(value = "/auditor-isactive-list", produces = "application/json")
	public ResponseEntity<List<AuditorDto>> auditorisactivelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside auditorisactivelist" );
			List<AuditorDto> dto=auditService.getAuditorIsActiveList();
			return new ResponseEntity<List<AuditorDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching auditorisactivelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/team-member-isactive-list", produces = "application/json")
	public ResponseEntity<List<AuditTeamMembersDto>> teammemberisactivelist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside teammemberisactivelist" );
			List<AuditTeamMembersDto> dto=auditService.getTeamMmberIsActiveList();
			return new ResponseEntity<List<AuditTeamMembersDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching teammemberisactivelist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/audit-team-member-list", produces = "application/json")
	public ResponseEntity<List<AuditTeamEmployeeDto>> getauditteammemberlist(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getauditteammemberlist" );
			List<AuditTeamEmployeeDto> dto=auditService.getauditteammemberlist();
			return new ResponseEntity<List<AuditTeamEmployeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getauditteammemberlist: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/get-team-list", produces = "application/json")
	public ResponseEntity<List<AuditTeam>> getTeamList(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getTeamList" );
			List<AuditTeam> dto=auditService.getTeamList();
			return new ResponseEntity<List<AuditTeam>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getTeamList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/schedule-approval-list", produces = "application/json")
	public ResponseEntity<List<AuditScheduleListDto>> getScheduleApprovalList(@RequestHeader String username) throws Exception {
		try {
			logger.info(" Inside getScheduleApprovalList" );
			List<AuditScheduleListDto> dto=auditService.getScheduleApprovalList(username);
			return new ResponseEntity<List<AuditScheduleListDto>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getScheduleApprovalList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/approve-schedule", produces = "application/json")
	public ResponseEntity<Response> approveSchedule(@RequestHeader String username, @RequestBody AuditScheduleListDto auditScheduleListDto) throws Exception {
		try {
			logger.info( " Inside approve-schedule" );
			 long result=auditService.approveSchedule(auditScheduleListDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Audit Schedule Acknowledged Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Audit Schedule Acknowledged Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in approve-schedule"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/return-schedule", produces = "application/json")
	public ResponseEntity<Response> returnSchedule(@RequestHeader String username, @RequestBody AuditScheduleListDto auditScheduleListDto) throws Exception {
		try {
			logger.info( " Inside return-schedule" );
			 long result=auditService.returnSchedule(auditScheduleListDto,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Audit Schedule Returned Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Audit Schedule Returned Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in return-schedule"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/schedule-remarks", produces = "application/json")
	public ResponseEntity<List<AuditScheduleRemarksDto>> getScheduleRemarks(@RequestHeader String username) throws Exception {
		try {
			logger.info(" Inside schedule-remarks "+username );
			List<AuditScheduleRemarksDto> dto=auditService.getScheduleRemarks();
			return new ResponseEntity<List<AuditScheduleRemarksDto>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching schedule-remarks: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping(value = "/schedule-tran", produces = "application/json")
	public ResponseEntity<List<AuditTranDto>> scheduleTran(@RequestHeader String username,@RequestBody String scheduleId) throws Exception {
		try {
			logger.info(" Inside scheduleTran"+username );
			List<AuditTranDto> dto=auditService.scheduleTran(scheduleId);
			return new ResponseEntity<List<AuditTranDto>>(dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching scheduleTran: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/get-iqa-auditee-list", produces = "application/json")
	public ResponseEntity<List<IqaAuditeeDto>> iqaAuditeeList(@RequestHeader String username, @RequestBody String iqaId) throws Exception {
		try {
			logger.info(new Date() + " Inside iqaAuditeeList" );
			List<IqaAuditeeDto> dto=auditService.getIqaAuditeeList(Long.parseLong(iqaId));
			return new ResponseEntity<List<IqaAuditeeDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching iqaAuditeeList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PostMapping(value = "/insert-iqa-auditee", produces = "application/json")
	public ResponseEntity<String> insertiqaauditee(@RequestHeader String username, @RequestBody IqaAuditeeDto iqaAuditeeDto) throws Exception {
		try {
			logger.info(new Date() + " Inside insertiqaauditee" );
			long insertIqaAuditee=auditService.insertIqaAuditee(iqaAuditeeDto,username);
			 if(insertIqaAuditee > 0) {
				 return new ResponseEntity<String>("200" , HttpStatus.OK);
			 }else {
				 return new ResponseEntity<String>("500" , HttpStatus.BAD_REQUEST);
			 }
		} catch (Exception e) {
			 logger.error(new Date() +"error in insertiqaauditee"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
		}
	}
	
	@PostMapping(value = "/get-observation", produces = "application/json")
	public ResponseEntity<List<AuditObservation>> getObservation(@RequestHeader String username) throws Exception {
		try {
			logger.info(new Date() + " Inside getObservation" );
			List<AuditObservation> dto=auditService.getObservation();
			return new ResponseEntity<List<AuditObservation>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getObservation: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PostMapping(value = "/add-audit-check-list", produces = "application/json")
	public ResponseEntity<Response> addAuditCheckList(@RequestHeader String username, @RequestBody AuditCheckListDTO auditCheckListDTO) throws Exception {
		try {
			logger.info( " Inside add-audit-check-list" );
			 long result=auditService.addAuditCheckList(auditCheckListDTO,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Auditor Remarks Added Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Auditor Remarks Add Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in add-audit-check-list"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/add-auditee-remarks", produces = "application/json")
	public ResponseEntity<Response> addAuditeeRemarks(@RequestHeader String username, @RequestBody AuditCheckListDTO auditCheckListDTO) throws Exception {
		try {
			logger.info( " Inside add-auditee-remarks" );
			 long result=auditService.addAuditeeRemarks(auditCheckListDTO,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Auditee Remarks Added Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Auditee Remarks Add Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in add-auditee-remarks"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/update-auditee-remarks", produces = "application/json")
	public ResponseEntity<Response> updateAuditeeRemarks(@RequestHeader String username, @RequestBody AuditCheckListDTO auditCheckListDTO) throws Exception {
		try {
			logger.info( " Inside update-auditee-remarks" );
			 int result=auditService.updateAuditeeRemarks(auditCheckListDTO,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Auditee Remarks Updated Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Auditee Remarks Update Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in update-auditee-remarks"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping(value = "/get-audit-check-list", produces = "application/json")
	public ResponseEntity<List<CheckListDto>> getAuditCheckList(@RequestHeader String username,@RequestBody String scheduleId) throws Exception {
		try {
			logger.info(new Date() + " Inside getAuditCheckList" );
			List<CheckListDto> dto=auditService.getAuditCheckList(scheduleId);
			return new ResponseEntity<List<CheckListDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getAuditCheckList: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PostMapping(value = "/update-audit-check-list", produces = "application/json")
	public ResponseEntity<Response> updateAuditCheckList(@RequestHeader String username, @RequestBody AuditCheckListDTO auditCheckListDTO) throws Exception {
		try {
			logger.info( " Inside update-audit-check-list" );
			 long result=auditService.updateAuditCheckList(auditCheckListDTO,username);
			 if(result > 0) {
				 return ResponseEntity.status(HttpStatus.OK).body(new Response("Audit Check List Updated Successfully","S"));
			 }else {
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Audit Check List Update Unsuccessful","F"));			 
			 }
		} catch (Exception e) {
			 logger.error("error in update-audit-check-list"+ e.getMessage());
			 e.printStackTrace();
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error occurred: " + e.getMessage(),"I"));
		}
	}
	
	@PostMapping("/upload-check-list-img")
	public ResponseEntity<Response> uploadCheckListImage(@RequestHeader  String username, @RequestParam("ad") String attachment,
			@RequestParam("file") MultipartFile file) throws Exception {

		logger.info(new Date() + " Inside upload-check-list-img " + username);
		long result = 0;
		String message = "attachment------------- " + attachment;
		Map<String, Object> response = null;
		try {
			response = new ObjectMapper().readValue(attachment, HashMap.class);
			result =	auditService.uploadCheckListImage(file, response, username);
		} catch (Exception e) {
			logger.info(new Date() + " Inside uploadCheckListImage " + e);
		}
		if (result > 0) {
			message = "Image successfully uploaded: " + response.get("attachmentName");
			return ResponseEntity.status(HttpStatus.OK).body(new Response(message));
		} else {
			message = "Could not Upload the Image " + response.get("attachmentName");
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Response(message));
		}

	}
	
	@PostMapping(value = "/get-check-list-img", produces = "application/json")
	public ResponseEntity<String> getCheckListimg(@RequestHeader String username, @RequestBody AuditScheduleListDto auditScheduleListDto) throws Exception {
		try {
			logger.info( " Inside get-check-list-img" );
			 String result=auditService.getCheckListimg(auditScheduleListDto);
			 return new ResponseEntity<String>( result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getCheckListimg: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PostMapping(value = "/check-auditor-present", produces = "application/json")
	public ResponseEntity<Long> checkAuditorPresent(@RequestHeader String username, @RequestBody String auditorId) throws Exception {
		try {
			logger.info( " Inside check-auditor-present" );
			 Long result=auditService.checkAuditorPresent(auditorId);
			 return new ResponseEntity<Long>(result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching check-auditor-present: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PostMapping(value = "/delete-auditor", produces = "application/json")
	public ResponseEntity<Integer> deleteAuditor(@RequestHeader String username, @RequestBody String auditorId) throws Exception {
		try {
			logger.info( " Inside delete-auditor" );
			Integer result=auditService.deleteAuditor(auditorId);
			 return new ResponseEntity<Integer>(result,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching delete-auditor: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	
	@PostMapping(value = "/auditcheck-list-byObsIds", produces = "application/json")
	public ResponseEntity<List<CheckListDto>> getAuditCheckListbyObsIds(@RequestHeader String username,@RequestBody String scheduleId) throws Exception {
		try {
			logger.info(new Date() + " Inside getAuditCheckListbyObsIds" );
			List<CheckListDto> dto=auditService.getAuditCheckListbyObsIds();
			return new ResponseEntity<List<CheckListDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getAuditCheckListbyObsIds: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}
	@PostMapping(value = "/mostFrequent-Nc-list", produces = "application/json")
	public ResponseEntity<List<CheckListDto>> getMostFrequentNC(@RequestHeader String username,@RequestBody String scheduleId) throws Exception {
		try {
			logger.info(new Date() + " Inside getMostFrequentNC" );
			List<CheckListDto> dto=auditService.getMostFrequentNC();
			return new ResponseEntity<List<CheckListDto>>( dto,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error fetching getMostFrequentNC: ", e);
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST); 
		}
	}

}
