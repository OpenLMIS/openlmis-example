package org.openlmis.example.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@NoArgsConstructor
@XmlRootElement(name = "extensions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Extensions {

  @Getter
  @Setter
  @XmlElement(name = "extension")
  private List<Extension> extensions;
}
