package com.ts.employee.rx.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ts.employee.rx.entities.Employee;
import com.ts.employee.rx.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/api/employees")
  public Flux<Employee> getEmployees() {
    return employeeService.findAll();

  }

  @GetMapping("/api/employee/email/{emailId}")
  public Flux<Employee> findByEmail(@PathVariable(name = "emailId") String emailId) {
    return employeeService.findByEmail(emailId);
  }


  @PostMapping("/api/employee/create")
  public Mono<Employee> createEmployee(@RequestBody Employee emp) {
    return employeeService.createEmployee(emp);
  }


  @GetMapping("/api/employee/{id}")
  public Mono<ResponseEntity<Employee>> getEmployeeById(@PathVariable(name = "id") String id) {
    log.info("Start Method getEmployee()");
    log.info("ID::::::{}", id);
    return employeeService.getEmployeeById(id).map(savedEmp -> ResponseEntity.ok(savedEmp))
        .defaultIfEmpty(ResponseEntity.notFound().build());

  }

  @DeleteMapping("/api/employee/{id}")
  public Mono<ResponseEntity<Void>> deleteEmployee(@PathVariable(name = "id") String id) {
    return employeeService.getEmployeeById(id)
        .flatMap(existsEmp -> employeeService.deleteEmployee(existsEmp)
            .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

  }

  /*
   * @DeleteMapping("/api/employee/delete/{empId}") public Mono<Void>
   * deleteEmployeeById(@PathVariable(name = "empId") Long empId) { return
   * employeeService.deleteByEmpId(empId);
   * 
   * 
   * }
   */

  @PutMapping("/api/employee/update")
  public Mono<ResponseEntity<Employee>> updateEmployee(@Valid @RequestBody Employee emp) {
    return employeeService.getEmployeeById(emp.getId()).flatMap(existingEmp -> {
      existingEmp.setEmpId(emp.getEmpId());
      existingEmp.setEmail(emp.getEmail());
      existingEmp.setDepid(emp.getDepid());
      existingEmp.setName(emp.getName());
      existingEmp.setSalary(emp.getSalary());
      return employeeService.updateEmployee(existingEmp);
    }).map(updatedEmp -> new ResponseEntity<>(updatedEmp, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


}
