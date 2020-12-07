package com.java.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.EmployeeDetailsRequestDTO;
import com.java.exceptions.EmployeeNotFoundException;
import com.java.model.EmployeeDetails;
import com.java.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/saveEmployee")
	public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeDetailsRequestDTO employeeDetailsDTO) throws Exception {
		employeeService.saveEmployee(employeeDetailsDTO);
		return new ResponseEntity<>("employee added successfully", HttpStatus.CREATED);

	}

	@GetMapping("/viewEmployees")
	public ResponseEntity<List<EmployeeDetails>> viewEmployees() {
		List<EmployeeDetails> details = employeeService.getEmployees();
		return new ResponseEntity<>(details, HttpStatus.CREATED);

	}

	@GetMapping("/viewEmployee/{empName}")
	public ResponseEntity<List<EmployeeDetailsRequestDTO>> viewEmployee(@RequestParam String empName) throws EmployeeNotFoundException {
		List<EmployeeDetailsRequestDTO> details = employeeService.viewEmployeeNameContains(empName);
		return new ResponseEntity<>(details, HttpStatus.CREATED);

	}

	@DeleteMapping("/deleteEmployee/{empCode}")
	public ResponseEntity<String> deleteEmployees(@PathVariable Long empCode) throws EmployeeNotFoundException {
		employeeService.deleteEmployee(empCode);
		return new ResponseEntity<>("employee details deleted successfully", HttpStatus.CREATED);

	}

	@PatchMapping("/update/{empCode}")
	public ResponseEntity<String> modifyEmployee(@RequestBody Map<String, Object> updates, @PathVariable Long empCode) throws EmployeeNotFoundException {
		System.out.println(updates.size());

		boolean isUpdated = employeeService.modifyEmployee(updates, empCode);
		if (isUpdated)
			return new ResponseEntity<>("Successfully modified employee", HttpStatus.OK);

		return new ResponseEntity<>("Failed to modify employee", HttpStatus.BAD_REQUEST);
	}

}
