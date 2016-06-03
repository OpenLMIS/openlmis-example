package org.openlmis.example.web;

import org.openlmis.example.repository.ReadOnlyFooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationController
{
    @Autowired
    private ReadOnlyFooRepository readOnlyFooRepository;

    /*
        This endpoint illustrates one possible (but not ideal) way of exposing the count() member
        of the ReadOnlyFooRepository. Although that interface explicitly defines a variety of members (such as "count"),
        and although Spring Data dutifully generates implementation methods for all of them, Spring Rest seems
        very selective in terms of which ones it actually exposes as endpoints. The implication seems to be that,
        if they're desired, we have to manually expose many of the members of the ReadOnlyFooRepository. As is, several of the
        members currently defined in it have no affect on our REST API.

        Although it works, the hybrid Spring Data Rest / Spring MVC approach represented by the getCount() method below is awkward.
        As we've discussed, it fragments our API into an inconsistent mix of hypermedia and non-hypermedia responses. It also breaks
        the hypermedia of the Spring Data Rest responses. Specifically, because they don't include the links exposed manually via MVC
        ("/api/foos/count," in this case), they present incomplete and therefore misleading  "_links" objects.

        Due to the above limitations, additional research into Spring Data Rest may be useful. The new findById() method added to
        ReadOnlyFooRepository represents a very incomplete foray into what additionally might be done.
     */
    @RequestMapping("/api/foos/count")
    public long getCount()
    {
        return readOnlyFooRepository.count();
    }

}
