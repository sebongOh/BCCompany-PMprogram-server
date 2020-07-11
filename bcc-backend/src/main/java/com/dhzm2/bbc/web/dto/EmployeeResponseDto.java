package com.dhzm2.bbc.web.dto;

import com.dhzm2.bbc.domain.employee.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeResponseDto {
  private Long id;
  private String name;
  private String identityNumber;
  private String phoneNumber;
  private String address;

  public EmployeeResponseDto(Employee entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.identityNumber = entity.getIdentityNumber();
    this.phoneNumber = entity.getPhoneNumber();
    this.address = entity.getAddress();
  }
}
