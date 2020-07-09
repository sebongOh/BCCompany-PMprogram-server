package com.dhzm2.bbc.web.dto;

import com.dhzm2.bbc.domain.employee.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeSaveRequestDto {
  private String name;
  private String identityNumber;
  private String phoneNumber;
  private String address;

  @Builder
  public EmployeeSaveRequestDto(String name, String identityNumber, String phoneNumber, String address) {
    this.name = name;
    this.identityNumber = identityNumber;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

  public Employee toEntity(){
    return Employee.builder()
            .name(name)
            .identityNumber(identityNumber)
            .phoneNumber(phoneNumber)
            .address(address)
            .build();
  }
}
