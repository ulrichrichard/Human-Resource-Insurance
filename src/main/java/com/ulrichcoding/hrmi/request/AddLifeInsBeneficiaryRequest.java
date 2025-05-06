package com.ulrichcoding.hrmi.request;

import lombok.Data;

import java.util.Date;

@Data
public class AddLifeInsBeneficiaryRequest {
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
}
