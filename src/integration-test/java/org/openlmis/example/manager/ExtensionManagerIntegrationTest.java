package org.openlmis.example.manager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlmis.example.Application;
import org.openlmis.example.exception.ExtensionException;
import org.openlmis.example.extension.ExtensionManager;
import org.openlmis.example.extension.point.DefaultOrderQuantity;
import org.openlmis.example.extension.point.OrderQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
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