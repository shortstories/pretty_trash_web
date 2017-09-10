package com.trash.pretty.prettytrash.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.trash.pretty.prettytrash.model.ChatEvent;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class ChatRepositoryTest {
  private static final Logger logger = LoggerFactory.getLogger(ChatRepositoryTest.class);

  @Autowired
  private ChatRepository chatRepository;

  @Test
  public void shouldGetChatEvent() {
    // Given
    ChatEvent testCase = new ChatEvent();
    testCase.setContents("test contents");
    testCase.setNickName("test nick name");
    testCase.setStartFrom(0);
    int rowNum = chatRepository.addChatEvent(testCase);
    logger.debug("created {} rows", rowNum);

    // When
    List<ChatEvent> eventList = chatRepository.getChatEvents(0, chatRepository.getMaxIndex());

    // Then
    assertThat(eventList)
        .isNotNull()
        .isNotEmpty();

    logger.debug("result: {}", eventList);
  }
}