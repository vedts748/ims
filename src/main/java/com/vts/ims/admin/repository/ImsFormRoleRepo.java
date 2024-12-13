package com.vts.ims.admin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vts.ims.admin.entity.ImsFormRole;



@Repository
public interface ImsFormRoleRepo extends JpaRepository<ImsFormRole, Long> {
	
	
	ImsFormRole findByImsFormRoleId(Long imsFormRoleId);
	
}