package io.nodus.util.provider;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erwolff on 8/14/2014.
 */
public class NameProvider {

  private static final Logger log = LoggerFactory.getLogger(NameProvider.class);
  private static final String path = "src/main/resources/templates/names.txt";

  private static List<String> firstNames = new ArrayList<>();
  private static List<String> lastNames = new ArrayList<>();

  public static List<String> getFirstNames() {
    if (CollectionUtils.isEmpty(firstNames)) {
      buildNameLists();
    }
    return firstNames;
  }

  public static List<String> getLastNames() {
    if (CollectionUtils.isEmpty(firstNames)) {
      buildNameLists();
    }
    return firstNames;
  }


  private static void buildNameLists() {
    try {
      List<String> fullNames = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
      parseNames(fullNames);
    } catch (IOException e) {
      log.error("Unable to read names.txt file");
    }
  }

  private static void parseNames(List<String> fullNames) {
    for (String fullName : fullNames) {
      String[] names = fullName.split(",");
      firstNames.add(names[0]);
      lastNames.add(names[1]);
    }
  }
}
