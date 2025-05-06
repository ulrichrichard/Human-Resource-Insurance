package com.ulrichcoding.hrmi.service.Employee;

import com.ulrichcoding.hrmi.dto.EmployeeDto;
import com.ulrichcoding.hrmi.model.Employee;
import com.ulrichcoding.hrmi.request.AddEmployeeRequest;
import com.ulrichcoding.hrmi.request.UpdateEmployeeRequest;

import java.util.List;

public interface IEmployeeService {
    Employee addEmployee(AddEmployeeRequest request);

    Employee getEmployeeById(Long id);
    void deleteEmployee(Long id);
    Employee updateEmployee(UpdateEmployeeRequest request, Long id);
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByName(String name);
    List<Employee> getEmployeesByInsuranceco(String insuranceco);
    Long countEmployees();
    Long countEmployeesByInsurancecoCode(String insurancecoCode);

    List<EmployeeDto> getConvertedEmployees(List<Employee> employees);

    EmployeeDto convertToDto(Employee employee);
}
