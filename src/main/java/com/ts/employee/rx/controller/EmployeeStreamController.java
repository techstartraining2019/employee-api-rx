package com.ts.employee.rx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ts.employee.rx.entities.EmployeeCapped;
import com.ts.employee.rx.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequestMapping("/stream")
public class EmployeeStreamController {

  @Autowired
  private EmployeeService employeeService;


  @GetMapping(value = "/employees", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<EmployeeCapped> getItemsStream() {
    return employeeService.findEmployeesBy();
  }


}
