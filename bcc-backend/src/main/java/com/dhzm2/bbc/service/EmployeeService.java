package com.dhzm2.bbc.service;

import com.dhzm2.bbc.domain.employee.EmployeeRepository;
import com.dhzm2.bbc.web.dto.EmployeeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  @Transactional
  public Long save(EmployeeSaveRequestDto requestDto){
    return employeeRepository.save(requestDto.toEntity()).getId();
  }
}
