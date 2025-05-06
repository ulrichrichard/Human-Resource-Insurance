package com.ulrichcoding.hrmi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Insuranceco {
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
    private String registrationNum;
    private Date foundedDate;
    private String type;
    private String status;

    // We break the loop
    @JsonIgnore
    @OneToMany(mappedBy = "insuranceco")
    private List<Employee> employees;
}
