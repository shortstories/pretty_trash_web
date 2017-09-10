/*
 * @(#) ChatApiController.java 9월 09, 2017
 *
 * Copyright 2017 NAVER Corp. All rights Reserved.
 * NAVERCORP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.trash.pretty.prettytrash.controller.api;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trash.pretty.prettytrash.model.Snapshot;
import com.trash.pretty.prettytrash.service.ChatService;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
@RestController
@RequestMapping("/api/v1/chats")
public class ChatApiController {
  private final ChatService chatService;

  @Autowired
  public ChatApiController(final ChatService chatService) {
    this.chatService = chatService;
  }

  @GetMapping("/url")
  public Pair<String, String> getWebsocketUrl() {
    return Pair.of("websocketUrl", chatService.getWebsocketUrl());
  }

  @GetMapping("/snapshots/last")
  public Snapshot getLastSnapshot() {
    return chatService.getLastTextSnapshot();
  }

  @GetMapping("/index/next")
  public Pair<String, Integer> getNextIndex() {
    return Pair.of("nextIndex", chatService.getNextIndex());
  }
}
