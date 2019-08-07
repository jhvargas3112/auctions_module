package org.openbravo.auction.rest.server.resources;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class GetAuctionWinners extends ServerResource {
  @Get("xml")
  public String getAuctionBuyers() {
    ClassLoader classLoader = getClass().getClassLoader();
    Properties appProps = new Properties();

    StringBuilder sb = null;

    try {
      String resource = classLoader.getResource("config.properties").getPath();
      appProps.load(new FileInputStream(resource));

      String filepath = appProps.getProperty("auction_winners_path_file") + "/auction_winners.xml";

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
