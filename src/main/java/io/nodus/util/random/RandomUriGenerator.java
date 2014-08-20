package io.nodus.util.random;

import io.nodus.util.config.NodusConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;

/**
 * Created by erwolff on 7/3/14.
 */
public class RandomUriGenerator implements RandomGenerator<URI> {
  private static final Logger log = LoggerFactory.getLogger(RandomUriGenerator.class);

  private Random random;
  private int maxStringLen;
  private int maxArrayElems;
  private String uriHost;
  private Integer uriPort;
  private List<?> selections;

  public RandomUriGenerator(NodusConfig config, List<?> selections) {
    random = RandomSeeder.getRandom();
    maxStringLen = config.getMaxStringLen();
    maxArrayElems = config.getMaxArrayElems();
    uriHost = config.getUriHost();
    uriPort = config.getUriPort();
    this.selections = selections;
  }

  @Override
  public URI random() {
    if (CollectionUtils.isNotEmpty(selections)) {
      return randomUriFromSelection();
    }
    return randomUri();
  }

  @Override
  public URI[] randomArray() {
    RandomIntGenerator gen = new RandomIntGenerator(maxArrayElems);
    int n = gen.random();
    URI[] array = new URI[n];
    for (int i = 0; i < n; i++) {
      if (CollectionUtils.isNotEmpty(selections)) {
        array[i] = randomUriFromSelection();
      } else {
        array[i] = randomUri();
      }
    }
    return array;
  }

  private URI randomUri() {
    URI uri = null;
    try {
      URIBuilder uriBuilder = new URIBuilder(uriHost);
      uriBuilder.setPort(uriPort);
      String randomPath = "/" + RandomStringUtils.random(maxStringLen, 0, 0, true, false, null, random);
      uriBuilder.setPath(randomPath);
      uri = uriBuilder.build();
    } catch (URISyntaxException e) {
      log.error("Unable to generate valid random uri");
    }
    return uri;
  }

  private URI randomUriFromSelection() {
    RandomIntGenerator gen = new RandomIntGenerator(selections.size());
    return (URI) selections.get(gen.random());
  }
}
