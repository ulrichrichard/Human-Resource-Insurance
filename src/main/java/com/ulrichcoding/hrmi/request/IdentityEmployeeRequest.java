package com.ulrichcoding.hrmi.request;

import lombok.Data;

@Data
public class IdentityEmployeeRequest {
    private String code;
    private String name;
    private String address;
}
