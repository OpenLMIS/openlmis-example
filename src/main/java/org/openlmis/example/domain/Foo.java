package org.openlmis.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class Foo extends BaseEntity
{
    @Getter
    @Setter
    String bar;

    @Getter
    @Setter
    String name;
}
