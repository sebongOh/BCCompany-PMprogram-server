package com.dhzm2.bbc.domain.employee;

import com.dhzm2.bbc.web.dto.EmployeeUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String identityNumber;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String address;

  @Builder
  public Employee(String name, String identityNumber, String phoneNumber, String address){
    this.name = name;
    this.identityNumber = identityNumber;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public void update(String name, String identityNumber, String phoneNumber, String address){
    this.name = name;
    this.identityNumber = identityNumber;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

}
