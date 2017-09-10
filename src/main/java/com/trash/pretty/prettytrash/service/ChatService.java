package com.trash.pretty.prettytrash.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trash.pretty.prettytrash.config.WebsocketProperties;
import com.trash.pretty.prettytrash.model.Snapshot;
import com.trash.pretty.prettytrash.repository.ChatRepository;
import com.trash.pretty.prettytrash.repository.SnapshotRepository;

@Service
public class ChatService {
  private final WebsocketProperties websocketProperties;
  private final ChatRepository chatRepository;
  private final SnapshotRepository snapshotRepository;

  @Autowired
  public ChatService(final WebsocketProperties websocketProperties,
                     final ChatRepository chatRepository,
                     final SnapshotRepository snapshotRepository) {
    this.websocketProperties = websocketProperties;
    this.chatRepository = chatRepository;
    this.snapshotRepository = snapshotRepository;
  }

  public String getWebsocketUrl() {
//    throw new IllegalStateException("Unsupported yet");
    return websocketProperties.getUrl();
  }

  public int getNextIndex() {
    return chatRepository.getNextIndex();
  }

  public Snapshot getLastTextSnapshot() {
    Snapshot snapshot = snapshotRepository.getLastSnapshot();

    if (snapshot == null) {
      snapshot = new Snapshot();
      snapshot.setText("");
      snapshot.setIndex(chatRepository.getNextIndex());
    }

    int currentMaxIndex = chatRepository.getMaxIndex();

    if (snapshot.getIndex() < currentMaxIndex) {
      StringBuilder textBuilder = new StringBuilder(snapshot.getText());
      chatRepository.getChatEvents(snapshot.getIndex() + 1, currentMaxIndex).forEach(chatEvent -> {
        if (chatEvent.getLength() < 0) {
          textBuilder.replace(chatEvent.getStartFrom(),
              chatEvent.getStartFrom() - chatEvent.getLength(), "");
        }
        if (StringUtils.isNotEmpty(chatEvent.getContents())) {
          textBuilder.insert(chatEvent.getStartFrom(), chatEvent.getContents());
        }
      });
      snapshot.setText(textBuilder.toString());
    }

    return snapshot;
  }
}
