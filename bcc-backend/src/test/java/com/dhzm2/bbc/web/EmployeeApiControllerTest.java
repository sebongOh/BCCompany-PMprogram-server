package com.dhzm2.bbc.web;

import com.dhzm2.bbc.domain.employee.Employee;
import com.dhzm2.bbc.domain.employee.EmployeeRepository;
import com.dhzm2.bbc.web.dto.EmployeeResponseDto;
import com.dhzm2.bbc.web.dto.EmployeeSaveRequestDto;
import com.dhzm2.bbc.web.dto.EmployeeUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
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
    String identityNumber = "123456-1234567";
    String phoneNumber = "010-1234-5678";
    String address = "대구시 북구 동천동";
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

  @Test
  public void Employee_조회() throws Exception{
    // given
    String name = "직원1";
    String identityNumber = "123456-1234567";
    String phoneNumber = "010-1234-5678";
    String address = "대구시 북구 동천동";
    EmployeeSaveRequestDto requestDto = EmployeeSaveRequestDto.builder()
            .name(name)
            .identityNumber(identityNumber)
            .phoneNumber(phoneNumber)
            .address(address)
            .build();
    Employee saveEmploy = employeeRepository.save(requestDto.toEntity());
    Long selectId = saveEmploy.getId();
    String url = "http://localhost:" + port + "/api/v1/employee/" + selectId;

    // when
    ResponseEntity<EmployeeResponseDto> responseDtoResponseEntity = restTemplate.getForEntity(url, EmployeeResponseDto.class);

    // then
    assertThat(responseDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    Employee employee = employeeRepository.findById(selectId).get();
    assertThat(employee.getName()).isEqualTo(name);
    assertThat(employee.getIdentityNumber()).isEqualTo(identityNumber);
    assertThat(employee.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(employee.getAddress()).isEqualTo(address);
  }

  @Test
  public void Employee_수정() throws Exception{
    // given
    // save
    String name = "직원1";
    String identityNumber = "123456-1234567";
    String phoneNumber = "010-1234-5678";
    String address = "대구시 북구 동천동";
    EmployeeSaveRequestDto saveRequestDto = EmployeeSaveRequestDto.builder()
            .name(name)
            .identityNumber(identityNumber)
            .phoneNumber(phoneNumber)
            .address(address)
            .build();

    Employee saveEmployee = employeeRepository.save(saveRequestDto.toEntity());

    // update
    Long updateId = saveEmployee.getId();
    String expectedName = "직원2";
    String expectedIdentityNumber = "012345-0123456";
    String expectedPhoneNumber = "010-0123-4567";
    String expectedAddress = "대구시 북구 동천동 2";

    EmployeeUpdateRequestDto updateRequestDto = EmployeeUpdateRequestDto.builder()
            .name(expectedName)
            .identityNumber(expectedIdentityNumber)
            .phoneNumber(expectedPhoneNumber)
            .address(expectedAddress)
            .build();

    String url = "http://localhost:" + port + "/api/v1/employee/" + updateId;

    HttpEntity<EmployeeUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(updateRequestDto,new HttpHeaders());

    // when
    ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);

    // then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Employee> employeeList = employeeRepository.findAll();
    assertThat(employeeList.get(0).getName()).isEqualTo(expectedName);
    assertThat(employeeList.get(0).getIdentityNumber()).isEqualTo(expectedIdentityNumber);
    assertThat(employeeList.get(0).getPhoneNumber()).isEqualTo(expectedPhoneNumber);
    assertThat(employeeList.get(0).getAddress()).isEqualTo(expectedAddress);
  }

  @Test
  public void Employee_삭제() throws Exception{
    //given
    // save
    String name = "직원1";
    String identityNumber = "123456-1234567";
    String phoneNumber = "010-1234-5678";
    String address = "대구시 북구 동천동";
    EmployeeSaveRequestDto saveRequestDto = EmployeeSaveRequestDto.builder()
            .name(name)
            .identityNumber(identityNumber)
            .phoneNumber(phoneNumber)
            .address(address)
            .build();

    Employee saveEmployee = employeeRepository.save(saveRequestDto.toEntity());
    Long deleteId = saveEmployee.getId();

    String url = "http://localhost:" + port + "/api/v1/employee/" + deleteId;
    HttpEntity<Long> requestEntity = new HttpEntity<>(new HttpHeaders());

    // when
    ResponseEntity<Long> responseEntity = restTemplate.exchange(url,HttpMethod.DELETE,requestEntity,Long.class);

    //then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isGreaterThan(0L);

    List<Employee> employeeList = employeeRepository.findAll();
    assertThat(employeeList.isEmpty()).isTrue();
  }
}
