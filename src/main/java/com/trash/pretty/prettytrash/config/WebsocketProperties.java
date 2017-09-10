/*
 * @(#) WebsocketProperties.java 9월 09, 2017
 *
 * Copyright 2017 NAVER Corp. All rights Reserved.
 * NAVERCORP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.trash.pretty.prettytrash.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
@Configuration
@ConfigurationProperties(prefix = "websocket")
@Data
public class WebsocketProperties {
  private String url;
}
