package org.openlmis.example.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@NoArgsConstructor
@XmlRootElement(name = "extension")
@XmlAccessorType(XmlAccessType.FIELD)
public class Extension {

  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String point;

  @Getter
  @Setter
  private String className;
}
