package com.ts.employee.rx.service;

import com.ts.employee.rx.entities.Employee;
import com.ts.employee.rx.entities.EmployeeCapped;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface EmployeeService {

  public Flux<Employee> findAll();

  public Mono<Employee> getEmployeeById(String id);

  Flux<Employee> findByEmail(String email);

  public Mono<Employee> createEmployee(Employee emp);

  public Mono<Void> deleteById(String id);

  public Mono<Void> deleteEmployee(Employee emp);

  public Mono<Employee> updateEmployee(Employee emp);

  public Flux<EmployeeCapped> findEmployeesBy();
}
