package com.ulrichcoding.hrmi.dto;

import com.ulrichcoding.hrmi.model.Employee;
import lombok.Data;

import java.util.Date;

@Data
public class LifeInsBeneficiaryDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Date birthday;
    private String placeOfBirth;
    private String gender;
    private String nationality;
    private String relationshipPolicyHolder;
    private Employee employee;
}
