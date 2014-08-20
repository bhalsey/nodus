package io.nodus.util.provider;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by erwolff on 8/14/2014.
 */
public class EmailProvider {

  private static final Logger log = LoggerFactory.getLogger(NameProvider.class);
  private static final String path = "src/main/resources/templates/email_hosts.txt";

  private static List<String> emailHosts = new ArrayList<>();

  public static List<String> getEmailHosts() {
    if (CollectionUtils.isEmpty(emailHosts)) {
      buildEmailHostsList();
    }
    return emailHosts;
  }

  private static void buildEmailHostsList() {
    try {
      emailHosts = Files.readAllLines(FileSystems.getDefault().getPath(path), Charset.defaultCharset());
    } catch (IOException e) {
      log.error("Unable to read names.txt file");
    }
  }
}
