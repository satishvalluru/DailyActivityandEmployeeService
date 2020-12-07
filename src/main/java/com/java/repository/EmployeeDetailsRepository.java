package com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.model.EmployeeDetails;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

	@Query("From EmployeeDetails f where f.empCode =:empCode")
	public List<EmployeeDetails> findByEmpCode(Long empCode);

	List<EmployeeDetails> findByEmpNameContains(@Param("empName") String empName);

}
