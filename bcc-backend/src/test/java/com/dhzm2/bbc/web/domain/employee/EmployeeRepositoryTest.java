package com.dhzm2.bbc.web.domain.employee;

import com.dhzm2.bbc.domain.employee.Employee;
import com.dhzm2.bbc.domain.employee.EmployeeRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeRepositoryTest {

  @Autowired
  EmployeeRepository employeeRepository;

  @After
  public void cleanup(){
    employeeRepository.deleteAll();
  }

  @Test
  public void 직원조회(){
    //given
    String name = "직원1";
    String identityNumber = "940527-1685016";
    String phoneNumber = "010-8262-3777";
    String address = "대구시 북구 동천동 동화골든빌 112-1507";

    employeeRepository.save(Employee.builder()
            .name(name)
            .identityNumber(identityNumber)
            .phoneNumber(phoneNumber)
            .address(address)
            .build());
    //when
    List<Employee> employeeList = employeeRepository.findAll();

    //then
    Employee employee = employeeList.get(0);
    assertThat(employee.getName()).isEqualTo(name);
    assertThat(employee.getIdentityNumber()).isEqualTo(identityNumber);
    assertThat(employee.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(employee.getAddress()).isEqualTo(address);
  }
}
