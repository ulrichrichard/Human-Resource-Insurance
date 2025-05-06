package com.ulrichcoding.hrmi.service.LifeInsBeneficiary;

import com.ulrichcoding.hrmi.dto.LifeInsBeneficiaryDto;
import com.ulrichcoding.hrmi.model.Employee;
import com.ulrichcoding.hrmi.model.LifeInsBeneficiary;
import com.ulrichcoding.hrmi.request.AddLifeInsBeneficiaryRequest;
import com.ulrichcoding.hrmi.request.UpdateLifeInsBeneficiaryRequest;
import jakarta.mail.MessagingException;

import java.util.List;

public interface ILifeInsBeneficiaryService {
    LifeInsBeneficiary getLifeInsBeneficiariesById(Long id);
    void deleteLifeInsBeneficiariesById(Long id);
    LifeInsBeneficiary saveLifeInsBeneficiaries(AddLifeInsBeneficiaryRequest request, String employeeCode);
    LifeInsBeneficiary updateLifeInsBeneficiaries(UpdateLifeInsBeneficiaryRequest request, Long beneficiaryId);
    List<LifeInsBeneficiary> getAllLifeInsBeneficiaries();

//    LifeInsBeneficiary getLifeInsBeneficiaryByEmployee(AddEmployeeRequest request);

    List<LifeInsBeneficiaryDto> getConvertedLifeInsBeneficiaries(List<LifeInsBeneficiary> lifeInsBeneficiaries);

    LifeInsBeneficiaryDto convertToDto(LifeInsBeneficiary lifeInsBeneficiary);

    Long countLifeInsBeneficiaries();

    Long countLifeInsBeneficiariesByEmployeeCode(String employeeCode);

    LifeInsBeneficiary getLifeInsBeneficiariesByEmployeeCode(String employeeCode);

    void confirmationForUpdate(Employee employee) throws MessagingException;

    void notificationForUpdate(Employee employee) throws MessagingException;
}
