package com.ulrichcoding.hrmi.controller;

import com.ulrichcoding.hrmi.Response.ApiResponse;
import com.ulrichcoding.hrmi.exceptions.ResourceNotFoundException;
import com.ulrichcoding.hrmi.model.Insuranceco;
import com.ulrichcoding.hrmi.service.Insuranceco.InsurancecoService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("insurance_companies")
@RequiredArgsConstructor
public class InsurancecoController {
    private final InsurancecoService insurancecoService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllInsurancecos() {
        try {
            List<Insuranceco> insurancecos = insurancecoService.getAllInsuranceco();
            if (insurancecos.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Insurance Company", null));
            }
            return ResponseEntity.ok(new ApiResponse("Success!", insurancecos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addInsuranceco(
            @RequestBody Insuranceco insuranceco
    ) {
        try {
            Insuranceco theInsuranceco = insurancecoService.addInsuranceco(insuranceco);
            return ResponseEntity.ok(new ApiResponse("Insurance company is added successfully!", theInsuranceco));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{insuranceco_id}")
    public ResponseEntity<ApiResponse> getInsurancecoById(
        @PathVariable("insuranceco_id") Long insurancecoId
    ) {
        try {
            Insuranceco insuranceco = insurancecoService.getInsurancecoById(insurancecoId);
            return ResponseEntity.ok(new ApiResponse("Found!", insuranceco));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/{name}/find")
    public ResponseEntity<ApiResponse> getInsurancecoByName(
            @PathVariable String name
    ) {
        try {
            List<Insuranceco> insuranceco = insurancecoService.getInsurancecoByName(name);
            return ResponseEntity.ok(new ApiResponse("Found!", insuranceco));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteInsuranceco(
            @PathVariable Long id
    ) {
        try {
            insurancecoService.deleteInsuranceco(id);
            return ResponseEntity.ok(new ApiResponse("Found!", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateInsuranceco(
            @PathVariable Long id,
            @RequestBody Insuranceco insuranceco
    ) {
        try {
            Insuranceco updatedInsuranceco = insurancecoService.updateInsuranceco(insuranceco, id);
            return ResponseEntity.ok(new ApiResponse("Found!", updatedInsuranceco));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
