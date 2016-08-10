package org.openlmis.example.web;

import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ConsulKVStorageController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceNameController.class);

  private static final String CONSUL_URL = "http://consul:8500";

  @RequestMapping(value = "/config", method = RequestMethod.GET)
  public ResponseEntity<?> getValue(@RequestParam(value = "key") String key,
                                    @RequestParam(value = "recursive") boolean recursive)
  {
    try {
      Consul consul = Consul.builder().withUrl(CONSUL_URL).build();
      KeyValueClient kvClient = consul.keyValueClient();
      List<String> values;
      if(recursive) {
        values = kvClient.getValuesAsString(key);
      } else {
        values = new ArrayList<>();
        values.add(kvClient.getValueAsString(key).get());
      }
      return new ResponseEntity<>(values, HttpStatus.OK);
    } catch (Exception ex) {
      LOGGER.debug("Error getting value of " + key + " from consul key-value storage.");
      return new ResponseEntity<>("Error getting value of " + key, HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(value = "/config", method = RequestMethod.POST)
  public ResponseEntity<?> setValue(@RequestBody Map.Entry<String, String> kv,
                                    @RequestParam(value = "encryption") boolean encryption)
  {
    try {
      Consul consul = Consul.builder().withUrl(CONSUL_URL).build();
      KeyValueClient kvClient = consul.keyValueClient();

      if(encryption) {
        File file = new File("tmp.txt");
        file.createNewFile();

        Runtime.getRuntime().exec("echo " + kv.getValue() + " > tmp.txt");
        Process p = Runtime.getRuntime().exec("crypt set -backend consul -endpoint consul:8500 " +
                kv.getKey() + " tmp.txt");

        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String s;
        if((s = stdError.readLine()) != null) {
          file.delete();
          LOGGER.debug(s);
          LOGGER.debug("Error setting value of " + kv.getKey() + " in consul key-value storage.");
          return new ResponseEntity<>("Error occured while setting value of " + kv.getKey(),
                  HttpStatus.BAD_REQUEST);
        }
        file.delete();
      } else {
        kvClient.putValue(kv.getKey(), kv.getValue());
      }

      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception ex) {
      LOGGER.debug("Error setting value of " + kv.getKey() + " in consul key-value storage.");
      return new ResponseEntity<>("Error occured while setting value of " + kv.getKey(),
              HttpStatus.BAD_REQUEST);
    }
  }
}
