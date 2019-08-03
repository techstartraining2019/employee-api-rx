package com.ts.employee.rx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class EmployeeApiRxApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmployeeApiRxApplication.class, args);
  }

}
