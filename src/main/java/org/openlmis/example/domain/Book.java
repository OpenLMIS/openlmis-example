package org.openlmis.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book extends BaseEntity {

  @Getter
  @Setter
  @NotEmpty
  String name;

  @Getter
  @Setter
  @NotEmpty
  @Column(unique = true)
  String isbn;

}
