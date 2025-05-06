package com.ulrichcoding.hrmi.repository;

import com.ulrichcoding.hrmi.model.Employee;
import com.ulrichcoding.hrmi.model.Insuranceco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    Long countByInsurancecoCode(String insurancecoCode);

    List<Employee> findByInsurancecoCode(String insuranceco);

    Employee findByCode(String code);

    Employee findByCodeAndNameAndAddressAndInsurancecoCode(String code, String name, String address, String insurancecoCode);
}
