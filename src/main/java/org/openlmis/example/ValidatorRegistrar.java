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

package org.openlmis.example;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
    This class contains no business logic. It exists exclusively as a workaround for the bug
    documented at https://jira.spring.io/browse/DATAREST-524.
 */

@Configuration
public class ValidatorRegistrar extends RepositoryRestConfigurerAdapter {

  private static final List<String> EVENTS;

  static {
    List<String> events = new ArrayList<String>();
    events.add("beforeCreate");
    events.add("afterCreate");
    events.add("beforeSave");
    events.add("afterSave");
    events.add("beforeLinkSave");
    events.add("afterLinkSave");
    events.add("beforeDelete");
    events.add("afterDelete");
    EVENTS = Collections.unmodifiableList(events);
  }

  @Autowired
  ListableBeanFactory beanFactory;

  @Override
  public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener
                                                               validatingListener) {
    super.configureValidatingRepositoryEventListener(validatingListener);
    Map<String, Validator> validators = beanFactory.getBeansOfType(Validator.class);
    for (Map.Entry<String, Validator> entry : validators.entrySet()) {
      EVENTS.stream().filter(p -> entry.getKey().startsWith(p)).findFirst()
          .ifPresent(p -> validatingListener.addValidator(p, entry.getValue()));
    }
  }

}
