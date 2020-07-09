package com.dhzm2.bbc.web;

import com.dhzm2.bbc.domain.employee.Employee;
import com.dhzm2.bbc.domain.employee.EmployeeRepository;
import com.dhzm2.bbc.web.dto.EmployeeSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeApiControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private EmployeeRepository employeeRepository;

  @After
  public void tearDown() throws Exception{
    employeeRepository.deleteAll();
  }

  @Test
  public void Employee_등록() throws Exception{
    //given
    String name = "직원1";
    String identityNumber = "940527-1685016";
    String phoneNumber = "010-8262-3777";
    String address = "대구시 북구 동천동 동화골든빌 112-1507";
    EmployeeSaveRequestDto requestDto = EmployeeSaveRequestDto.builder()
            .name(name)
            .identityNumber(identityNumber)
            .phoneNumber(phoneNumber)
            .address(address)
            .build();

    String url = "http://localhost:" + port + "/api/v1/employee";

    //when
    ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Employee> all = employeeRepository.findAll();
    assertThat(all.get(0).getName()).isEqualTo(name);
    assertThat(all.get(0).getIdentityNumber()).isEqualTo(identityNumber);
    assertThat(all.get(0).getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(all.get(0).getAddress()).isEqualTo(address);
  }
}
