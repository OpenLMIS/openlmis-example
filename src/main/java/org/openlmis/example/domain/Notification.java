package org.openlmis.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  @Setter
  Long id;

  @Getter
  @Setter
  @Column(nullable = false, columnDefinition = "text")
  String recipient;

  @Getter
  @Setter
  @Column(columnDefinition = "text")
  String subject;

  @Getter
  @Setter
  @Column(nullable = false, columnDefinition = "text")
  String message;

  @Getter
  @Setter
  @Column(columnDefinition = "boolean DEFAULT false")
  Boolean sent;
}
