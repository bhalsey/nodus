package io.nodus.util.annotation;

import io.nodus.util.random.RandomGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by erwolff on 8/16/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRandom {

  Class<? extends RandomGenerator> generator();
}
