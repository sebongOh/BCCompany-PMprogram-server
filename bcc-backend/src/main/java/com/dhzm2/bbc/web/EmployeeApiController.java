package com.dhzm2.bbc.web;

import com.dhzm2.bbc.service.EmployeeService;
import com.dhzm2.bbc.web.dto.EmployeeResponseDto;
import com.dhzm2.bbc.web.dto.EmployeeSaveRequestDto;
import com.dhzm2.bbc.web.dto.EmployeeUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class EmployeeApiController {

  private final EmployeeService employeeService;

  @PostMapping("/api/v1/employee")
  public Long save(@RequestBody EmployeeSaveRequestDto requestDto){
    return employeeService.save(requestDto);
  }

  @PutMapping("/api/v1/employee/{id}")
  public Long update(@PathVariable("id") Long id, @RequestBody EmployeeUpdateRequestDto requestDto){
    return employeeService.update(id,requestDto);
  }

  @GetMapping("/api/v1/employee/{id}")
  public EmployeeResponseDto findById(@PathVariable("id") Long id){
    return employeeService.findById(id);
  }

  @GetMapping("/api/v1/employee")
  public List<EmployeeResponseDto> findAll(){
    return employeeService.findAll();
  }

  @DeleteMapping("/api/v1/employee/{id}")
  public Long delete(@PathVariable Long id){
    employeeService.delete(id);
    return id;
  }
}
