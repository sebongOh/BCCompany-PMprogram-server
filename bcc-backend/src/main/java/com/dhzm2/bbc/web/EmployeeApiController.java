package com.dhzm2.bbc.web;

import com.dhzm2.bbc.service.EmployeeService;
import com.dhzm2.bbc.web.dto.EmployeeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmployeeApiController {

  private final EmployeeService employeeService;

  @PostMapping("/api/v1/employee")
  public Long save(@RequestBody EmployeeSaveRequestDto requestDto){
    return employeeService.save(requestDto);
  }
}
