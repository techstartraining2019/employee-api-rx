package com.ts.employee.rx.initialize;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import com.ts.employee.rx.entities.EmployeeCapped;
import com.ts.employee.rx.repository.EmployeeCappedRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
// @Profile("!test")
@Slf4j
public class EmployeeDataInitializer implements CommandLineRunner {

  @Autowired
  private EmployeeCappedRepository employeeCappedRepository;

  @Autowired
  MongoOperations mongoOperations;


  @Override
  public void run(String... args) throws Exception {
    createCappedCollection();
    dataSetUpforCappedCollection();

  }

  private void createCappedCollection() {
    mongoOperations.dropCollection(EmployeeCapped.class);
    mongoOperations.createCollection(EmployeeCapped.class,
        CollectionOptions.empty().maxDocuments(20).size(50000).capped());
  }

  public void dataSetUpforCappedCollection() {
    Flux<EmployeeCapped> empCappedFlux = Flux.interval(Duration.ofSeconds(1)).map(
        i -> new EmployeeCapped(null, 67l, "Ram" + i, 20000, "234" + i, "test" + i + "@gmail.com"));

    employeeCappedRepository.insert(empCappedFlux).subscribe((empCapped -> {
      log.info("Inserted Employee is " + empCapped);
    }));

  }



}
