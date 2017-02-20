package org.openlmis.example.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.openlmis.example.web.BarValidation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
  In this example, a Bar isn't simply a Foo's inevitable counterpart. It's a place to get drinks.

  This class partly exists as a way to demonstrate the use of JSR 349's Bean Validation API
  through the use of annotations.
  For a list of both standard and hibernate-specific annotations respectively, see:

  http://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html_single/#section-builtin-constraints
  http://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html_single/#validator-defineconstraints-hv-constraints

  Custom constraints can easily be made, as nicely documented here:
      http://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html_single/#validator-customconstraints
  Nevertheless, restraint should be exercised in terms of what kind of validation is performed.
  For instance, a unique-constraint could be implemented via a custom annotation-based validation.
  It involves the state of the entire database, though, rather than just that of an
  individual bean. Such validation is thus more appropriately enforced at the DB level
  (and potentially via an alternate annotation).
 */
@Entity
@BarValidation
@NoArgsConstructor
public class Bar extends BaseEntity {
  @Getter
  @Setter
  @NotEmpty
  @Column(unique = true)
  String name;

  @Getter
  @Setter
  @Min(value = 2) //Must at least hold a bartender as well as patron
  @Max(value = 500) //Arbitrary business-rule for the sake of our example
      Integer capacity;


  private Set<ConstraintViolation<Bar>> getValidationViolations() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    return validator.validate(this);
  }

  public boolean isValid() {
    return getValidationViolations().isEmpty();
  }
}
