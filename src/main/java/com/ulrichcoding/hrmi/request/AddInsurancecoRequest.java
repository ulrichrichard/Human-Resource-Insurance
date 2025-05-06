package com.ulrichcoding.hrmi.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.Date;

@Data
public class AddInsurancecoRequest {
    private Long id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String registrationNum;
    private Date foundedDate;
    private String type;
    private String status;
}
