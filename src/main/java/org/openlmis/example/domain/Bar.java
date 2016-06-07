package org.openlmis.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/*
    In this example, a Bar isn't simply a Foo's inevitable counterpart. It's a place to get drinks.

    This class partly exists as a way to demonstrate the use of JSR 349's Bean Validation API through
    the use of annotations. For a list of both standard and hibernate-specific annotations respectively, see:

    http://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html_single/#section-builtin-constraints
    http://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html_single/#validator-defineconstraints-hv-constraints

    Custom constraints can easily be made, as nicely documented here:
        http://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html_single/#validator-customconstraints
    Nevertheless, restraint should be exercised in terms of what kind of validation is performed. For instance, a unique-constraint
    could be implemented via a custom annotation-based validation. It involves the state of the entire database, though, rather than
    just that of an indivudual bean. Such validation is thus more approprietly enfirced at the DB level (and potentially via an alternate
    annotation.
 */
@Entity
@NoArgsConstructor
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    Long id;

    @Getter
    @Setter
    @NotEmpty
    @Column(unique=true)
    String name;

    @Getter
    @Setter
    @Min(value=2) //Must at least hold a bartender as well as patron
    @Max(value=500) //Arbitrary business-rule for the sake of our example
    Integer capacity;

}
