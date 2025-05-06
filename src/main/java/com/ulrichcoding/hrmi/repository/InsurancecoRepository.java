package com.ulrichcoding.hrmi.repository;

import com.ulrichcoding.hrmi.model.Insuranceco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsurancecoRepository extends JpaRepository<Insuranceco, Long> {

    Insuranceco findByCode(String code);

    List<Insuranceco> findByName(String insurancecoName);

    boolean existsByName(String name);

    boolean existsByCode(String code);
}
