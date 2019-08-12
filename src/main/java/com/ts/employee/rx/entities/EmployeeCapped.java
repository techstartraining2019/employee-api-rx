package com.ts.employee.rx.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCapped {

  @Id
  private String id;
  private Long empId;
  private String name;
  private Integer salary;
  private String depid;
  private String email;

}
