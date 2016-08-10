package org.openlmis.example.web;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlmis.example.Application;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class ConsulKVStorageControllerIntegrationTest {
  private static final String KEY = "message";
  private static final String VALUE = "Test message.";
  private static final String BASE_URL = System.getenv("BASE_URL") + "/config";
  private static final String CONSUL_URL = "http://consul:8500";
  private Map.Entry<String, String> KV = new AbstractMap.SimpleEntry(KEY, VALUE);

  @Test
  public void setValueTest() {
    setValue(false);

    Consul consul = Consul.builder().withUrl(CONSUL_URL).build();
    KeyValueClient kvClient = consul.keyValueClient();

    String res = kvClient.getValueAsString(KEY).get();
    Assert.assertEquals(VALUE, res);
  }

  @Test
  public void getValueTest() {
    Consul consul = Consul.builder().withUrl(CONSUL_URL).build();
    KeyValueClient kvClient = consul.keyValueClient();
    kvClient.putValue(KEY, VALUE);

    RestTemplate restTemplate = new RestTemplate();
    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BASE_URL)
            .queryParam("key", KEY).queryParam("recursive", false);
    ResponseEntity<List<String>> response = restTemplate.exchange(builder.toUriString(),
            HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() { });

    Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

    List<String> res = response.getBody();
    Assert.assertTrue(res.contains(VALUE));
  }

  private void setValue(boolean encryption) {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<?> response;
    if(encryption) {
      response = restTemplate.exchange(BASE_URL + "?encryption=true",
              HttpMethod.POST, new HttpEntity<Object>(KV), String.class);
    } else {
      response = restTemplate.exchange(BASE_URL + "?encryption=false",
              HttpMethod.POST, new HttpEntity<Object>(KV), String.class);
    }

    Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
  }
}
