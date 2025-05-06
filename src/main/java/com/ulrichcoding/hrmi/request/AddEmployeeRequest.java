package com.ulrichcoding.hrmi.request;

import com.ulrichcoding.hrmi.model.Insuranceco;
import lombok.Data;

import java.util.Date;

@Data
public class AddEmployeeRequest {
    private Long id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String nationality;
    private Date birthdate;
    private String placeOfBirth;
    private String gender;
    private String maritalStatus;
    private Insuranceco insuranceco;
}
