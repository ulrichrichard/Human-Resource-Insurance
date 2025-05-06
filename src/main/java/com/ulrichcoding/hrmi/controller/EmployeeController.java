package com.ulrichcoding.hrmi.controller;

import com.ulrichcoding.hrmi.Response.ApiResponse;
import com.ulrichcoding.hrmi.dto.EmployeeDto;
import com.ulrichcoding.hrmi.exceptions.ResourceNotFoundException;
import com.ulrichcoding.hrmi.model.Employee;
import com.ulrichcoding.hrmi.request.AddEmployeeRequest;
import com.ulrichcoding.hrmi.request.IdentityEmployeeRequest;
import com.ulrichcoding.hrmi.request.UpdateEmployeeRequest;
import com.ulrichcoding.hrmi.service.Employee.EmployeeService;
import lombok.RequiredArgsConstructor;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            if (employees.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No employees founded!", null));
            }
            List<EmployeeDto> convertedEmployees = employeeService.getConvertedEmployees(employees);
            return ResponseEntity.ok(new ApiResponse("success", convertedEmployees));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{employee_id}")
    public ResponseEntity<ApiResponse> getEmployeeById(
            @PathVariable Long employee_id
    ) {
        try {
            Employee employee = employeeService.getEmployeeById(employee_id);
            EmployeeDto employeeDto = employeeService.convertToDto(employee);

            return ResponseEntity.ok(new ApiResponse("success", employeeDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEmployee(
            @RequestBody AddEmployeeRequest employee
    ) {
        try {
            Employee theEmployee = employeeService.addEmployee(employee);
            EmployeeDto employeeDto = employeeService.convertToDto(theEmployee);
            return ResponseEntity.ok(new ApiResponse("Add employee success!", employeeDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{employee_id}")
    public ResponseEntity<ApiResponse> updateEmployee(
            @RequestBody UpdateEmployeeRequest request,
            @PathVariable Long employee_id
    ) {
        try {
            Employee theEmployee = employeeService.updateEmployee(request, employee_id);
            EmployeeDto employeeDto = employeeService.convertToDto(theEmployee);
            return ResponseEntity.ok(new ApiResponse("Update employee success!", employeeDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{employee_id}/delete")
    public ResponseEntity<ApiResponse> deleteEmployee(
            @PathVariable Long employee_id
    ) {
        try {
            employeeService.deleteEmployee(employee_id);
            return ResponseEntity.ok(new ApiResponse("Delete employee success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{insurancecoCode}/find")
    public ResponseEntity<ApiResponse> getEmployeesByInsuranceco(
            @PathVariable String insurancecoCode
    ) {
        try {
            List<Employee> employees = employeeService.getEmployeesByInsuranceco(insurancecoCode);
            if (employees.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No employee found", null));
            }
            List<EmployeeDto> convertedEmployees = employeeService.getConvertedEmployees(employees);
            return ResponseEntity.ok(new ApiResponse("success", convertedEmployees));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/identityEmployee")
    public ResponseEntity<ApiResponse> getIdentityEmplByEmployeeAndInsuranceCode(
            @RequestBody IdentityEmployeeRequest request,
            @RequestParam String insuranceco_code
    ) {
        try {
            Employee employee = employeeService.getIdentityEmplByEmployeeAndInsuranceCode(request, insuranceco_code);
            EmployeeDto employeeDto = employeeService.convertToDto(employee);

            return ResponseEntity.ok(new ApiResponse("success", employeeDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countEmployees() {
        try {
            Long count = employeeService.countEmployees();
            return ResponseEntity.ok(new ApiResponse("success", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{insurancecoCode}/count")
    public ResponseEntity<ApiResponse> countEmployeesByInsurancecoCode(
            @PathVariable String insurancecoCode
    ) {
       try {
           Long count = employeeService.countEmployeesByInsurancecoCode(insurancecoCode);
           return ResponseEntity.ok(new ApiResponse("success", count));
       } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
       }
    }


}
