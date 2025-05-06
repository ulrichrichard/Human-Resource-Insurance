package com.ulrichcoding.hrmi.service.LifeInsBeneficiary;

import com.ulrichcoding.hrmi.dto.LifeInsBeneficiaryDto;
import com.ulrichcoding.hrmi.email.EMailTemplateName;
import com.ulrichcoding.hrmi.email.EmailService;
import com.ulrichcoding.hrmi.exceptions.ResourceNotFoundException;
import com.ulrichcoding.hrmi.model.Employee;
import com.ulrichcoding.hrmi.model.Insuranceco;
import com.ulrichcoding.hrmi.model.LifeInsBeneficiary;
import com.ulrichcoding.hrmi.repository.EmployeeRepository;
import com.ulrichcoding.hrmi.repository.InsurancecoRepository;
import com.ulrichcoding.hrmi.repository.LifeInsBeneficiaryRepository;
import com.ulrichcoding.hrmi.request.AddLifeInsBeneficiaryRequest;
import com.ulrichcoding.hrmi.request.UpdateLifeInsBeneficiaryRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LifeInsBeneficiaryService implements ILifeInsBeneficiaryService{

    private final LifeInsBeneficiaryRepository lifeInsBeneficiaryRepository;
    private final EmployeeRepository employeeRepository;
    private final InsurancecoRepository insurancecoRepository;
    private final EmailService emailService;
    private final ModelMapper modelMapper;

    @Value("${application.mailing.frontend.activate-url}")
    private String updateConfirmationUrl;

    @Override
    public LifeInsBeneficiary getLifeInsBeneficiariesById(Long id) {
        return lifeInsBeneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Beneficiary found with id: " + id));
    }

    @Override
    public void deleteLifeInsBeneficiariesById(Long id) {
        lifeInsBeneficiaryRepository.findById(id)
                .ifPresentOrElse(lifeInsBeneficiaryRepository::delete, () -> {
                    throw new ResourceNotFoundException("No Beneficiary found with id: " + id);
                });
    }

    @Override
    public LifeInsBeneficiary saveLifeInsBeneficiaries(AddLifeInsBeneficiaryRequest request, String employeeCode) {

        Employee employee = Optional.ofNullable(employeeRepository.findByCode(employeeCode))
                .orElseThrow(() -> new ResourceNotFoundException("No Employee found with code: " + employeeCode));
        Insuranceco insuranceco = Optional.ofNullable(insurancecoRepository.findByCode(employee.getInsuranceco().getCode()))
                .orElseThrow(() -> new ResourceNotFoundException("No Insuranceco found for this employee"));

        LifeInsBeneficiary lifeInsBeneficiary = new LifeInsBeneficiary();
        lifeInsBeneficiary.setName(request.getName());
        lifeInsBeneficiary.setEmail(request.getEmail());
        lifeInsBeneficiary.setPhone(request.getPhone());
        lifeInsBeneficiary.setAddress(request.getAddress());
        lifeInsBeneficiary.setBirthday(request.getBirthday());
        lifeInsBeneficiary.setPlaceOfBirth(request.getPlaceOfBirth());
        lifeInsBeneficiary.setGender(request.getGender());
        lifeInsBeneficiary.setNationality(request.getNationality());
        lifeInsBeneficiary.setRelationshipPolicyHolder(request.getRelationshipPolicyHolder());
        lifeInsBeneficiary.setEmployee(employee);

        return lifeInsBeneficiaryRepository.save(lifeInsBeneficiary);
    }

    @Override
    public LifeInsBeneficiary updateLifeInsBeneficiaries(UpdateLifeInsBeneficiaryRequest request, Long id) {
        return lifeInsBeneficiaryRepository.findById(id)
                .map(existingLifeInsBeneficiary -> updateExistingLifeInsBeneficiary(existingLifeInsBeneficiary, request))
                .map(lifeInsBeneficiaryRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("No Beneficiary found"));
    }

    private LifeInsBeneficiary updateExistingLifeInsBeneficiary(LifeInsBeneficiary existingLifeInsBeneficiary, UpdateLifeInsBeneficiaryRequest request) {
        existingLifeInsBeneficiary.setName(request.getName());
        existingLifeInsBeneficiary.setAddress(request.getAddress());
        existingLifeInsBeneficiary.setBirthday(request.getBirthday());
        existingLifeInsBeneficiary.setPlaceOfBirth(request.getPlaceOfBirth());
        existingLifeInsBeneficiary.setGender(request.getGender());
        existingLifeInsBeneficiary.setNationality(request.getNationality());
        existingLifeInsBeneficiary.setRelationshipPolicyHolder(request.getRelationshipPolicyHolder());

        return existingLifeInsBeneficiary;
    }

    @Override
    public List<LifeInsBeneficiary> getAllLifeInsBeneficiaries() {
        return lifeInsBeneficiaryRepository.findAll();
    }

    @Override
    public List<LifeInsBeneficiaryDto> getConvertedLifeInsBeneficiaries(List<LifeInsBeneficiary> lifeInsBeneficiaries) {
        return lifeInsBeneficiaries.stream().map(this::convertToDto).toList();
    }


    @Override
    public LifeInsBeneficiaryDto convertToDto(LifeInsBeneficiary lifeInsBeneficiary) {
        LifeInsBeneficiaryDto lifeInsBeneficiaryDto = modelMapper.map(lifeInsBeneficiary, LifeInsBeneficiaryDto.class);
        List<LifeInsBeneficiary> insBeneficiaries = lifeInsBeneficiaryRepository.findByEmployeeId(lifeInsBeneficiary.getId());
        List<LifeInsBeneficiaryDto> lifeInsBeneficiaryDtos = insBeneficiaries.stream()
                .map(insBeneficiarie -> modelMapper.map(insBeneficiarie, LifeInsBeneficiaryDto.class))
                .toList();

        return lifeInsBeneficiaryDto;
    }

    @Override
    public Long countLifeInsBeneficiaries() {
        return lifeInsBeneficiaryRepository.count();
    }

    @Override
    public Long countLifeInsBeneficiariesByEmployeeCode(String employeeCode) {
        return lifeInsBeneficiaryRepository.countByEmployeeCode(employeeCode);
    }

    @Override
    public LifeInsBeneficiary getLifeInsBeneficiariesByEmployeeCode(String employeeCode) {
        return lifeInsBeneficiaryRepository.findByEmployeeCode(employeeCode);
    }

    @Override
    public void confirmationForUpdate(Employee employee) throws MessagingException {
        emailService.sentEmail(
                employee.getEmail(),
                employee.getName(),
                EMailTemplateName.UPDATE_CONFIRMATION,
                updateConfirmationUrl,
                "Your life insurance beneficiary was updated successfully"
        );
    }

    @Override
    public void notificationForUpdate(Employee employee) throws MessagingException {
        String subject = "The employee : " + employee.getName() + " having this number: "
                + employee.getCode() + " was changed his life insurance beneficiary";
        emailService.sentEmail(
                employee.getInsuranceco().getEmail(),
                employee.getInsuranceco().getName(),
                EMailTemplateName.UPDATE_CONFIRMATION,
                updateConfirmationUrl,
                subject
        );
    }
}
