/*
 * @(#) JsonUtils.java 9월 09, 2017
 *
 * Copyright 2017 NAVER Corp. All rights Reserved.
 * NAVERCORP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.trash.pretty.prettytrash.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
public class JsonUtils {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  public static String toJson(Object object) {
    try {
      return MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Cannot parse Object to JSON: [object: " + object.toString() + "]");
    }
  }

  public static <T> T toObject(String json, Class<T> clazz) {
    try {
      return MAPPER.readValue(json, clazz);
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot parse JSON to Class: [json: " + json + ", class: " + clazz.getName() + "]");
    }
  }
}
