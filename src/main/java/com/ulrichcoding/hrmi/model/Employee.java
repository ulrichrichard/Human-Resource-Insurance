package com.ulrichcoding.hrmi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String address;
    private String nationality;
    private Date birthdate;
    private String placeOfBirth;
    private String gender;
    private String maritalStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "insuranceco_id")
    private Insuranceco insuranceco;
    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LifeInsBeneficiary> beneficiaries;

    public Employee(String code, String name, String email, String phone, String address, String nationality, Date birthdate, String placeOfBirth, String gender, String maritalStatus, Insuranceco insuranceco) {
        this.code = code;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.nationality = nationality;
        this.birthdate = birthdate;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.insuranceco = insuranceco;
    }
}
