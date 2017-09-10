/*
 * @(#) BlockSnapshot.java 9월 09, 2017
 *
 * Copyright 2017 NAVER Corp. All rights Reserved.
 * NAVERCORP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.trash.pretty.prettytrash.model;

import java.util.List;

import lombok.Data;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
@Data
public class BlockSnapshot {
  private int index;
  private String nickName;
  private List<Block> blocks;
}
