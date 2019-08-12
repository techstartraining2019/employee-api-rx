package com.ts.employee.rx.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import com.ts.employee.rx.entities.EmployeeCapped;
import reactor.core.publisher.Flux;

public interface EmployeeCappedRepository extends ReactiveMongoRepository<EmployeeCapped, String> {


  @Tailable
  Flux<EmployeeCapped> findEmployeesBy();
}
