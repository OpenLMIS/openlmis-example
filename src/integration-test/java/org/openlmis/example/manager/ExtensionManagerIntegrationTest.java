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

package org.openlmis.example.manager;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlmis.example.Application;
import org.openlmis.example.exception.ExtensionException;
import org.openlmis.example.extension.ExtensionManager;
import org.openlmis.example.extension.point.DefaultOrderQuantity;
import org.openlmis.example.extension.point.OrderQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
@SuppressWarnings("PMD.UnusedLocalVariable")
public class ExtensionManagerIntegrationTest {

  private static final String invalidPointId = "InvalidExtensionPoint";
  private static final String invalidExtensionId = "InvalidExtension";
  private static final String extensionId = "DefaultOrderQuantity";

  private HashMap<String, String> extensions;

  @Autowired
  private ExtensionManager extensionManager;

  /**
   * Prepare the test environment - add extensions for test purposes.
   */
  @Before
  public void setUp() {
    extensions = new HashMap<>();
    extensions.put(OrderQuantity.POINT_ID, extensionId);
    extensions.put(invalidPointId, invalidExtensionId);
    extensionManager.setExtensions(extensions);
  }

  @Test
  public void testShouldReturnExtensionByPointIdWhenValidId() {
    OrderQuantity orderQuantity = (OrderQuantity) extensionManager
        .getExtensionByPointId(OrderQuantity.POINT_ID);
    Assert.assertEquals(orderQuantity.getClass(), DefaultOrderQuantity.class);
  }

  @Test(expected = ExtensionException.class)
  public void testShouldNotReturnExtensionByPointIdWhenInvalidId() {
    OrderQuantity orderQuantity = (OrderQuantity) extensionManager
        .getExtensionByPointId(invalidPointId);
  }
}