package com.vts.ims.master.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginDetailsDto {
    private Long loginId;
    private String username;
    private Long empId;
    private String empNo;
    private String empDesigCode;
    private String title;
    private String salutation;
    private String empName;
    private Long imsFormRoleId;
    private String photo;
    private Long divisionId;
    private String loginType;
    private String formRoleName;
    private String labCode;
    
    
    //extra field
    private String empDesigName;
}
