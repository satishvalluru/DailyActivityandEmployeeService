package com.java.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dto.EmployeeDetailsRequestDTO;
import com.java.exceptions.EmployeeNotFoundException;
import com.java.model.EmployeeDetails;
import com.java.repository.EmployeeDetailsRepository;
import com.java.repository.EmployeeRepository;
import com.java.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepo;

	@Autowired
	EmployeeRepository employeeRepo;

	@Override
	public EmployeeDetailsRequestDTO saveEmployee(EmployeeDetailsRequestDTO employeeDetailsDTO) throws Exception {
		EmployeeDetails employeeDetails = new EmployeeDetails();

		employeeDetails.setFlag(false);
		BeanUtils.copyProperties(employeeDetailsDTO, employeeDetails);
		employeeDetailsRepo.save(employeeDetails);
		return employeeDetailsDTO;
	}

	@Override
	public List<EmployeeDetails> getEmployees() {
		return employeeDetailsRepo.findAll();
	}

	@Override
	public List<EmployeeDetailsRequestDTO> viewEmployeeNameContains(String empName) throws EmployeeNotFoundException {
		List<EmployeeDetails> empDetails = employeeDetailsRepo.findByEmpNameContains(empName);
		if (empDetails.isEmpty()) {
			throw new EmployeeNotFoundException("employee name not found");
		}
		List<EmployeeDetailsRequestDTO> employeeDetailsRequestDTO = new ArrayList<>();
		EmployeeDetailsRequestDTO employeeDetailsDTO = null;
		for (EmployeeDetails employeeDetails : empDetails) {
			employeeDetailsDTO = new EmployeeDetailsRequestDTO();
			BeanUtils.copyProperties(employeeDetails, employeeDetailsDTO);
			employeeDetailsRequestDTO.add(employeeDetailsDTO);
		}
		return employeeDetailsRequestDTO;
	}

	@Override
	public boolean modifyEmployee(Map<String, Object> updates, Long empCode) throws EmployeeNotFoundException {
		EmployeeDetails employee = new EmployeeDetails();
		Optional<EmployeeDetails> optionalEmployee = employeeDetailsRepo.findById(empCode);

		if (!optionalEmployee.isPresent()) {
			throw new EmployeeNotFoundException("employee code not found");
		}
		if (!optionalEmployee.isPresent())
			return false;
		employee = optionalEmployee.get();

		for (Map.Entry<String, Object> entry : updates.entrySet()) {
			try {
				org.apache.commons.beanutils.BeanUtils.setProperty(employee, entry.getKey(), entry.getValue());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		employeeDetailsRepo.save(employee);
		return true;
	}

	@Override
	public boolean deleteEmployee(Long empCode) throws EmployeeNotFoundException {
		EmployeeDetails employee = new EmployeeDetails();

		List<EmployeeDetails> optionalEmployee = employeeDetailsRepo.findByEmpCode(empCode);
		if (optionalEmployee.isEmpty()) {
			throw new EmployeeNotFoundException("employee code not found");
		}
		employee = optionalEmployee.get(0);

		employee.setFlag(true);
		employeeDetailsRepo.save(employee);
		return true;

	}

}
