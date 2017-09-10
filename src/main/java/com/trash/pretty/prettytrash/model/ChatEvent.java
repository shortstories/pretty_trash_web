package com.trash.pretty.prettytrash.model;

import lombok.Data;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
@Data
public class ChatEvent {
  private int index;
  private String nickName;
  private int startFrom;
  private String contents;
  private int length;
}