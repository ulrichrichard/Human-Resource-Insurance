package com.ulrichcoding.hrmi.controller;

import com.ulrichcoding.hrmi.Response.ApiResponse;
import com.ulrichcoding.hrmi.dto.LifeInsBeneficiaryDto;
import com.ulrichcoding.hrmi.exceptions.ResourceNotFoundException;
import com.ulrichcoding.hrmi.model.LifeInsBeneficiary;
import com.ulrichcoding.hrmi.request.AddLifeInsBeneficiaryRequest;
import com.ulrichcoding.hrmi.request.UpdateLifeInsBeneficiaryRequest;
import com.ulrichcoding.hrmi.service.LifeInsBeneficiary.LifeInsBeneficiaryService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("beneficiaries")
@RequiredArgsConstructor
public class LifeInsBeneficiaryController {
    private final LifeInsBeneficiaryService service;

    @GetMapping("{beneficiary_id}/find")
    public ResponseEntity<ApiResponse> getLifeInsBeneficiariesById(
            @PathVariable Long beneficiary_id
    ) {
        try {
            LifeInsBeneficiary lifeInsBeneficiary = service.getLifeInsBeneficiariesById(beneficiary_id);
            LifeInsBeneficiaryDto lifeInsBeneficiaryDto = service.convertToDto(lifeInsBeneficiary);
            return ResponseEntity.ok(new ApiResponse("success", lifeInsBeneficiaryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("{employee_code}/find")
    public ResponseEntity<ApiResponse> getLifeInsBeneficiariesByEmployeeCode(
            @PathVariable String employee_code
    ) {
        try {
            LifeInsBeneficiary lifeInsBeneficiary = service.getLifeInsBeneficiariesByEmployeeCode(employee_code);
            LifeInsBeneficiaryDto lifeInsBeneficiaryDto = service.convertToDto(lifeInsBeneficiary);
            return ResponseEntity.ok(new ApiResponse("success", lifeInsBeneficiaryDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("{beneficiary_id}/delete")
    public ResponseEntity<ApiResponse> deleteLifeInsBeneficiariesById(
            @PathVariable Long beneficiary_id
    ) {
        try {
            service.deleteLifeInsBeneficiariesById(beneficiary_id);
            return ResponseEntity.ok(new ApiResponse("Delete beneficiary success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addLifeInsBeneficiary(
            @RequestBody AddLifeInsBeneficiaryRequest request,
            @RequestParam String employee_code
    ) {
        try {
            LifeInsBeneficiary lifeInsBeneficiary = service.saveLifeInsBeneficiaries(request, employee_code);
            LifeInsBeneficiaryDto lifeInsBeneficiaryDto = service.convertToDto(lifeInsBeneficiary);
            return ResponseEntity.ok(new ApiResponse("Add beneficiary success", lifeInsBeneficiaryDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{beneficiary_id}/update")
    public ResponseEntity<ApiResponse> updateLifeInsBeneficiary(
            @RequestBody UpdateLifeInsBeneficiaryRequest request,
            @PathVariable Long beneficiary_id
    ) {
        try {
            LifeInsBeneficiary updatedLifeInsBeneficiary = service.updateLifeInsBeneficiaries(request, beneficiary_id);
            if (!updatedLifeInsBeneficiary.getEmployee().getCode().isEmpty()) {
                 // We send a validation mail to this employee who wants to update his life insurance beneficiary
                // And we sent a notification to the insurance company associated to this employee
                service.confirmationForUpdate(updatedLifeInsBeneficiary.getEmployee());
                service.notificationForUpdate(updatedLifeInsBeneficiary.getEmployee());
            }
            LifeInsBeneficiaryDto lifeInsBeneficiaryDto = service.convertToDto(updatedLifeInsBeneficiary);
            return ResponseEntity.ok(new ApiResponse("Update beneficiary success", lifeInsBeneficiaryDto));
        } catch (ResourceNotFoundException | MessagingException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllLifeInsBeneficiaries() {
        try {
            List<LifeInsBeneficiary> lifeInsBeneficiaries = service.getAllLifeInsBeneficiaries();
            if (lifeInsBeneficiaries == null) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No beneficiary", null));
            }
            List<LifeInsBeneficiaryDto> lifeInsBeneficiaryDtos = service.getConvertedLifeInsBeneficiaries(lifeInsBeneficiaries);
            return ResponseEntity.ok(new ApiResponse("success", lifeInsBeneficiaryDtos));
        } catch (Exception e) {
           return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countBeneficiaries() {
        try {
            Long count = service.countLifeInsBeneficiaries();
            return ResponseEntity.ok(new ApiResponse("success", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("{beneficiary_code}/count")
    public ResponseEntity<ApiResponse> countBeneficiariesByEmployeeCode(
            @PathVariable String beneficiary_code
    ) {
        try {
            Long count = service.countLifeInsBeneficiariesByEmployeeCode(beneficiary_code);
            return ResponseEntity.ok(new ApiResponse("success", count));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
