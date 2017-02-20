/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.example.web;

import org.openlmis.example.repository.ReadOnlyFooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@PreAuthorize("hasAuthority('USER') and #oauth2.hasScope('read')")
@RestController
public class FooController extends BaseController {
  @Autowired
  private ReadOnlyFooRepository readOnlyFooRepository;

  /*
      This endpoint illustrates one possible (but not ideal) way of exposing the count() member 
      of the ReadOnlyFooRepository. Although that interface explicitly defines a variety of 
      members (such as "count"), and although Spring Data dutifully generates implementation 
      methods for all of them, Spring Rest seems very selective in terms of which ones its 
      actually exposes as endpoints. The implication seems to be that, if they're desired, we 
      have to manually expose many of the members of the ReadOnlyFooRepository. As is, several of
       the members currently defined in it have no effect on our REST API.

      Although it works, the hybrid Spring Data Rest / Spring MVC approach represented by the 
      getCount() method below is awkward. Without additional work to incorporate HATEOAS into 
      MVC, the hybrid approach fragmenting our API into an inconsistent mix of hypermedia and 
      non-hypermedia responses. It also breaks the hypermedia of the Spring Data Rest responses. 
      Specifically, because they don't include the links exposed manually via MVC 
      ("/api/foos/count," in this case), they present incomplete and therefore misleading 
      "_links" objects. These drawbacks, along with the fact that we're decidedly uncommitted to 
      HATEOAS, may lead us to eventually drop Spring Data Rest. In that case, we'd consistently 
      rely on Spring MVC.
   */
  @RequestMapping(path = "/foos/count", method = RequestMethod.GET)
  public long getCount() {
    return readOnlyFooRepository.count();
  }

}
