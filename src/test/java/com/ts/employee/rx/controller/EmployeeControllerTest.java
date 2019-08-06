package com.ts.employee.rx.controller;

import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.ts.employee.rx.entities.Employee;
import com.ts.employee.rx.service.EmployeeService;
import reactor.core.publisher.Mono;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {


  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private EmployeeService employeeService;


  @Test
  public void testGetEmployees() {
    webTestClient.get().uri("/api/employees").accept(MediaType.APPLICATION_JSON_UTF8).exchange()
        .expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Employee.class);

  }

  @Test
  public void testFindByEmail() {
    // fail("Not yet implemented");
  }

  @Test
  public void testCreateEmployee() {
    Employee employee = new Employee("xyz", 1l, "test", 2000, "123", "test@gmail.com");

    webTestClient.post().uri("/api/employee/create").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(employee), Employee.class)
        .exchange().expectStatus().isOk().expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8).expectBody().jsonPath("$.id").isNotEmpty()
        .jsonPath("$.empId").isEqualTo(1).jsonPath("$.salary").isEqualTo(2000);
  }

  @Test
  public void testDeleteEmployee() {
    Employee employee = employeeService
        .createEmployee(new Employee("xyz", 1l, "test", 2000, "123", "test@gmail.com")).block();
    webTestClient.delete()
        .uri("/api/employee/{id}", Collections.singletonMap("id", employee.getId())).exchange()
        .expectStatus().isOk();
  }

  @Test
  public void testUpdateEmployee() {
    employeeService.createEmployee(new Employee("xyz", 1l, "test", 2000, "123", "test@gmail.com"))
        .block();

    Employee newEmployeeData = new Employee("xyz", 2l, "test2", 3000, "123", "test@gmail.com");
    webTestClient.put().uri("/api/employee/update").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(newEmployeeData), Employee.class)
        .exchange().expectStatus().isOk().expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8).expectBody().jsonPath("$.name")
        .isEqualTo("test2").jsonPath("$.salary").isEqualTo("3000");

  }

}
