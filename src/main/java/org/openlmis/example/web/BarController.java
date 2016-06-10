package org.openlmis.example.web;

import org.openlmis.example.domain.Bar;
import org.openlmis.example.repository.ReadOnlyFooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


@RestController
public class BarController
{

    /*
       This endpoint illustrates how Spring MVC controllers can manually check the validity of a bean.
       In production, the bean's getValidationViolations() method would likely be called, and any results
       returned to the user.
     */
    @RequestMapping(path = "/api/bars/validate", method = RequestMethod.POST)
    public boolean getValid(@RequestBody Bar bar)
    {
        return bar.isValid();
    }

}
