package com.ulrichcoding.hrmi.service.Employee;
import com.ulrichcoding.hrmi.dto.EmployeeDto;
import com.ulrichcoding.hrmi.dto.LifeInsBeneficiaryDto;
import com.ulrichcoding.hrmi.exceptions.ResourceNotFoundException;
import com.ulrichcoding.hrmi.model.Employee;
import com.ulrichcoding.hrmi.model.Insuranceco;
import com.ulrichcoding.hrmi.model.LifeInsBeneficiary;
import com.ulrichcoding.hrmi.repository.EmployeeRepository;
import com.ulrichcoding.hrmi.repository.InsurancecoRepository;
import com.ulrichcoding.hrmi.repository.LifeInsBeneficiaryRepository;
import com.ulrichcoding.hrmi.request.AddEmployeeRequest;
import com.ulrichcoding.hrmi.request.IdentityEmployeeRequest;
import com.ulrichcoding.hrmi.request.UpdateEmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final InsurancecoRepository insurancecoRepository;
    private final ModelMapper modelMapper;
    private final LifeInsBeneficiaryRepository lifeInsBeneficiaryRepository;


    @Override
    public Employee addEmployee(AddEmployeeRequest request) {
        // check if the insurance compagnie is found in the DB
        // If Yes, set it as the new employee insurance compagnie
        // If No, then we catch an Error that Insurance compagnie doesn't exist

        Insuranceco insuranceco = Optional.ofNullable(insurancecoRepository.findByCode(request.getInsuranceco().getCode()))
                        .orElseThrow(() -> new ResourceNotFoundException("Insurance compagnie Not Found!"));
        request.setInsuranceco(insuranceco);

        return employeeRepository.save(createEmployee(request, insuranceco));
    }

    private Employee createEmployee(AddEmployeeRequest request, Insuranceco insuranceco) {
        return new Employee(
            request.getCode(),
            request.getName(),
            request.getEmail(),
            request.getPhone(),
            request.getAddress(),
            request.getNationality(),
            request.getBirthdate(),
            request.getPlaceOfBirth(),
            request.getGender(),
            request.getMaritalStatus(),
            insuranceco
        );
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found!"));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.findById(id)
                .ifPresentOrElse(employeeRepository::delete,
                        () -> { throw new ResourceNotFoundException("Employee Not Found!"); });
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeRequest request, Long id) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> updateExistingEmployee(existingEmployee, request))
                .map(employeeRepository :: save)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found!"));
    }

    private Employee updateExistingEmployee(Employee existingEmployee, UpdateEmployeeRequest request) {

        //existingEmployee.setCode(request.getCode());
        existingEmployee.setName(request.getName());
        //existingEmployee.setEmail(request.getEmail());
        //existingEmployee.setPhone(request.getPhone());
        existingEmployee.setAddress(request.getAddress());
        existingEmployee.setNationality(request.getNationality());
        existingEmployee.setBirthdate(request.getBirthdate());
        existingEmployee.setPlaceOfBirth(request.getPlaceOfBirth());
        existingEmployee.setGender(request.getGender());
        existingEmployee.setMaritalStatus(request.getMaritalStatus());

        Insuranceco insuranceco = insurancecoRepository.findByCode(request.getInsuranceco().getCode());
        existingEmployee.setInsuranceco(insuranceco);

        return existingEmployee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }

    @Override
    public List<Employee> getEmployeesByInsuranceco(String insuranceco) {
        return employeeRepository.findByInsurancecoCode(insuranceco);
    }

    @Override
    public Long countEmployees() {
        return employeeRepository.count();
    }

    @Override
    public Long countEmployeesByInsurancecoCode(String insurancecoCode) {
        return employeeRepository.countByInsurancecoCode(insurancecoCode);
    }

    @Override
    public List<EmployeeDto> getConvertedEmployees(List<Employee> employees) {
        return employees.stream().map(this::convertToDto).toList();
    }

    @Override
    public EmployeeDto convertToDto(Employee employee) {
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        List<LifeInsBeneficiary> insBeneficiaries = lifeInsBeneficiaryRepository.findByEmployeeId(employee.getId());
        List<LifeInsBeneficiaryDto> lifeInsBeneficiaryDtos = insBeneficiaries.stream()
                .map(insBeneficiarie -> modelMapper.map(insBeneficiarie, LifeInsBeneficiaryDto.class))
                .toList();

        return employeeDto;
    }

    public Employee getIdentityEmplByEmployeeAndInsuranceCode(IdentityEmployeeRequest request, String insurancecoCode) {
        return employeeRepository.findByCodeAndNameAndAddressAndInsurancecoCode(request.getCode(), request.getName(), request.getAddress(), insurancecoCode);
    }
}
