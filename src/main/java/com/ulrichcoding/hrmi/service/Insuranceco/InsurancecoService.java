package com.ulrichcoding.hrmi.service.Insuranceco;

import com.ulrichcoding.hrmi.exceptions.AlreadyExistsException;
import com.ulrichcoding.hrmi.exceptions.ResourceNotFoundException;
import com.ulrichcoding.hrmi.model.Insuranceco;
import com.ulrichcoding.hrmi.repository.InsurancecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsurancecoService implements IInsurancecoService {

    private final InsurancecoRepository insurancecoRepository;

    @Override
    public Insuranceco getInsurancecoById(Long id) {
        return insurancecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance compagnie not found!"));
    }

    @Override
    public List<Insuranceco> getInsurancecoByName(String insurancecoName) {
        return insurancecoRepository.findByName(insurancecoName);
    }

    @Override
    public List<Insuranceco> getAllInsuranceco() {
        return insurancecoRepository.findAll();
    }

    @Override
    public Insuranceco addInsuranceco(Insuranceco insuranceco) {
        return Optional.of(insuranceco).filter(ins -> !insurancecoRepository.existsByCode(ins.getCode()))
                .map(insurancecoRepository :: save)
                .orElseThrow(() -> new AlreadyExistsException(insuranceco.getCode() + " already exists"));
    }

    @Override
    public Insuranceco updateInsuranceco(Insuranceco insuranceco, Long id) {
        return Optional.ofNullable(getInsurancecoById(id))
                .map(oldInsuranceco -> {
                    oldInsuranceco.setName(insuranceco.getName());
                    oldInsuranceco.setCode(insuranceco.getCode());
                    oldInsuranceco.setEmail(insuranceco.getEmail());
                    oldInsuranceco.setPhone(insuranceco.getPhone());
                    oldInsuranceco.setStatus(insuranceco.getStatus());
                    oldInsuranceco.setFoundedDate(insuranceco.getFoundedDate());
                    oldInsuranceco.setRegistrationNum(insuranceco.getRegistrationNum());
                    oldInsuranceco.setType(insuranceco.getType());
                    return insurancecoRepository.save(oldInsuranceco);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Insurance compagnie not found!"));
    }

    @Override
    public void deleteInsuranceco(Long id) {
        insurancecoRepository.findById(id)
                .ifPresentOrElse(insurancecoRepository::delete, () -> {
                    throw new ResourceNotFoundException("Insurance compagnie not found!");
                });
    }
}
