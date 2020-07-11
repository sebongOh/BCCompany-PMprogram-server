package com.dhzm2.bbc.service;

import com.dhzm2.bbc.domain.employee.Employee;
import com.dhzm2.bbc.domain.employee.EmployeeRepository;
import com.dhzm2.bbc.web.dto.EmployeeResponseDto;
import com.dhzm2.bbc.web.dto.EmployeeSaveRequestDto;
import com.dhzm2.bbc.web.dto.EmployeeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  @Transactional
  public Long save(EmployeeSaveRequestDto requestDto){
    return employeeRepository.save(requestDto.toEntity()).getId();
  }

  @Transactional
  public Long update(Long id, EmployeeUpdateRequestDto requestDto){
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("해당 직원이 없습니다. id = "+ id));

    employee.update(requestDto.getName(),requestDto.getIdentityNumber(),requestDto.getPhoneNumber(),requestDto.getAddress());

    return id;
  }

  @Transactional(readOnly = true)
  public EmployeeResponseDto findById(Long id){
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("해당 직원이 없습니다. id = " + id));
    return new EmployeeResponseDto(employee);
  }

  @Transactional(readOnly = true)
  public List<EmployeeResponseDto> findAll(){
    return employeeRepository.findAll()
            .stream()
            .map(EmployeeResponseDto::new)
            .collect(Collectors.toList());
  }

  @Transactional
  public void delete(Long id){
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("해당 직원이 없습니다. id = " + id));
    employeeRepository.delete(employee);
  }
}
