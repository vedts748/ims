package com.vts.ims.audit.service;

import java.util.List;

import com.vts.ims.audit.dto.AuditRescheduleDto;
import com.vts.ims.audit.dto.AuditScheduleDto;
import com.vts.ims.audit.dto.AuditScheduleListDto;
import com.vts.ims.audit.dto.AuditScheduleRemarksDto;
import com.vts.ims.audit.dto.AuditTeamEmployeeDto;
import com.vts.ims.audit.dto.AuditTeamMembersDto;
import com.vts.ims.audit.dto.AuditTotalTeamMembersDto;
import com.vts.ims.audit.dto.AuditeeDto;
import com.vts.ims.audit.dto.AuditorDto;
import com.vts.ims.audit.dto.AuditorTeamDto;
import com.vts.ims.audit.dto.IqaDto;
import com.vts.ims.audit.model.AuditTeam;
import com.vts.ims.master.dto.DivisionGroupDto;
import com.vts.ims.master.dto.DivisionMasterDto;
import com.vts.ims.master.dto.EmployeeDto;
import com.vts.ims.master.dto.ProjectMasterDto;

public interface AuditService {

	public List<AuditorDto> getAuditorList() throws Exception;

	public List<EmployeeDto> getEmployelist() throws Exception;

	public long insertAuditor(String[] empIds,String username) throws Exception;

	public long updateAuditor(AuditorDto auditordto, String username) throws Exception;

	public List<IqaDto> getIqaList() throws Exception;

	public long insertIqa(IqaDto iqadto, String username) throws Exception;

	public List<AuditeeDto> getAuditeeList() throws Exception;

	public List<DivisionMasterDto> getDivisionMaster() throws Exception;

	public List<DivisionGroupDto> getDivisionGroupList() throws Exception;

	public List<ProjectMasterDto> getProjectMasterList() throws Exception;

	public long insertAuditee(AuditeeDto auditeedto, String username) throws Exception;
	
	public List<AuditTeam> getTeamList()throws Exception;

	public long updateAuditee(String auditeeId, String username) throws Exception;

	public long insertAuditSchedule(AuditScheduleDto auditScheduleDto, String username)throws Exception;
	public long editAuditSchedule(AuditScheduleDto auditScheduleDto, String username)throws Exception;
	public long insertAuditReSchedule(AuditRescheduleDto auditRescheduleDto, String username)throws Exception;

	public List<AuditScheduleListDto> getScheduleList()throws Exception;
	public List<AuditScheduleListDto> getScheduleApprovalList(String username)throws Exception;

	public long forwardSchedule(List<Long> auditScheduleIds, String username)throws Exception;
	public long approveSchedule(AuditScheduleListDto auditScheduleListDto, String username)throws Exception;
	public long returnSchedule(AuditScheduleListDto auditScheduleListDto, String username)throws Exception;

	public long scheduleMailSend(List<AuditScheduleListDto> auditScheduleListDto, String username)throws Exception;

	public List<AuditTotalTeamMembersDto> getTotalTeamMembersList()throws Exception;
	
	public long rescheduleMailSend(AuditRescheduleDto auditRescheduleDto, String username)throws Exception;

	public List<AuditorTeamDto> getAuditTeamMainList() throws Exception;

	public List<AuditorDto> getAuditorIsActiveList() throws Exception;

	public List<AuditTeamMembersDto> getTeamMmberIsActiveList() throws Exception;

	public long insertAuditTeam(AuditorTeamDto auditormemberteamdto, String username) throws Exception;

	public List<AuditTeamEmployeeDto> getauditteammemberlist() throws Exception;

	public List<AuditScheduleRemarksDto> getScheduleRemarks() throws Exception;

}
