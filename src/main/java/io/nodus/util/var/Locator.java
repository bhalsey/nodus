package io.nodus.util.var;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

/**
 * Created by erwolff on 6/29/2014.
 */
public class Locator {

  private Stack<String> path;

  public Locator() {
    path = new Stack<>();
  }

  public String currentLoc() {
    return path.peek();
  }

  public String fullPath() {
    return StringUtils.join(path, ".");
  }

  public void resetPath() {
    path.clear();
  }

  public String backtrack() {
    return path.pop();
  }

  public <T> void add(T currentClass) {
    path.push(currentClass.getClass().getSimpleName().toLowerCase());
  }

  public int size() {
    return path.size();
  }
}
