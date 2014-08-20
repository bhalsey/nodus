package io.nodus.util.annotation;

/**
 * Created by erwolff on 8/17/2014.
 */
public @interface Nullable {

  boolean always() default false;

  boolean anyPath() default true;

  String[] paths() default "";

  boolean jsonInclude() default true;
}
