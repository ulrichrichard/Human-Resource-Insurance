package com.ulrichcoding.hrmi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LifeInsBeneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String address;
    private Date birthday;
    private String placeOfBirth;
    private String gender;
    private String nationality;
    private String relationshipPolicyHolder;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public LifeInsBeneficiary(String name, String email, String phone, String address, Date birthday, String placeOfBirth, String gender, String nationality, String relationshipPolicyHolder, Employee employee) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.relationshipPolicyHolder = relationshipPolicyHolder;
    }
}
