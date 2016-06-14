package org.openlmis.example.web;

import org.openlmis.example.domain.Bar;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarController {

  /*
     This endpoint illustrates how Spring MVC controllers can manually check the validity of a bean.
     In production, the bean's getValidationViolations() method would likely be called,
     and any results returned to the user.
   */
  @RequestMapping(path = "/api/bars/validate", method = RequestMethod.POST)
  public boolean getValid(@RequestBody Bar bar) {
    return bar.isValid();
  }

}
