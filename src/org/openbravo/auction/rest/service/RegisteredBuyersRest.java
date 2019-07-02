package org.openbravo.auction.rest.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class RegisteredBuyersRest extends ServerResource {

  @Get("xml")
  public String registeredBuyers() {
    ClassLoader classLoader = getClass().getClassLoader();
    Properties appProps = new Properties();

    StringBuilder sb = null;

    try {
      String resource = classLoader.getResource("config.properties").getPath();
      appProps.load(new FileInputStream(resource));

      String filepath = appProps.getProperty("registered_buyers_path_file")
          + "/registered_buyers.xml";

      BufferedReader br = new BufferedReader(new FileReader(filepath));

      String line;
      sb = new StringBuilder();

      while ((line = br.readLine()) != null) {
        sb.append(line.trim());
      }

      br.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return sb.toString();
  }

}
