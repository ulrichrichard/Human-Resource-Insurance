package com.ulrichcoding.hrmi.service.Insuranceco;

import com.ulrichcoding.hrmi.model.Insuranceco;

import java.util.List;

public interface IInsurancecoService {
    Insuranceco getInsurancecoById(Long id);
    List<Insuranceco> getInsurancecoByName(String insurancecoName);
    List<Insuranceco> getAllInsuranceco();
    Insuranceco addInsuranceco(Insuranceco insuranceco);
    Insuranceco updateInsuranceco(Insuranceco insuranceco, Long id);
    void deleteInsuranceco(Long id);
}
