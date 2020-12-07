package com.java.service;

import java.util.List;
import java.util.Map;

import com.java.dto.EmployeeDetailsRequestDTO;
import com.java.exceptions.EmployeeNotFoundException;
import com.java.model.EmployeeDetails;

public interface EmployeeService {

	public EmployeeDetailsRequestDTO saveEmployee(EmployeeDetailsRequestDTO employeeDetailsDTO) throws Exception;

	public List<EmployeeDetails> getEmployees();

	public boolean deleteEmployee(Long empCode) throws EmployeeNotFoundException;

	public List<EmployeeDetailsRequestDTO> viewEmployeeNameContains(String empName) throws EmployeeNotFoundException;

	public boolean modifyEmployee(Map<String, Object> updates, Long empCode) throws EmployeeNotFoundException;

}
