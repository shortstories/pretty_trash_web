/*
 * @(#) PersistenceConfig.java 9월 09, 2017
 *
 * Copyright 2017 NAVER Corp. All rights Reserved.
 * NAVERCORP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.trash.pretty.prettytrash.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
@Configuration
public class PersistenceConfig {
  @Bean
  public DataSource mysqlDataSource() {
    MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setURL("jdbc:mysql://220.230.114.15/pretty_trash?useSSL=false");
    dataSource.setUser("user");
    dataSource.setPassword("useruser");
    return dataSource;
  }
}
