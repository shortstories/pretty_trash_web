package com.trash.pretty.prettytrash.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.trash.pretty.prettytrash.model.ChatEvent;

@Repository
public class ChatRepository {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public ChatRepository(final DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public int addChatEvent(final ChatEvent event) {
    return jdbcTemplate.update("INSERT INTO chat_logs(name, startFrom, contents) " +
        "VALUES (?, ?, ?)", event.getNickName(), event.getStartFrom(), event.getContents());
  }

  public int getMaxIndex() {
    Integer result = jdbcTemplate.queryForObject("SELECT max(chat_logs.index) FROM chat_logs", Integer.class);
    if (result == null) {
      return 0;
    } else {
      return result;
    }
  }

  public List<ChatEvent> getChatEvents(int startIndex, int endIndex) {
    return jdbcTemplate.query(
        "SELECT * " +
            "FROM chat_logs " +
            "WHERE chat_logs.index BETWEEN ? AND ? " +
            "ORDER BY chat_logs.index ASC",
        (rs, rowNum) -> {
          ChatEvent chatEvent = new ChatEvent();
          chatEvent.setIndex(rs.getInt("index"));
          chatEvent.setStartFrom(rs.getInt("startFrom"));
          chatEvent.setNickName(rs.getString("name"));
          chatEvent.setContents(rs.getString("contents"));
          chatEvent.setLength(rs.getInt("length"));
          return chatEvent;
        },
        startIndex, endIndex);
  }

  public int getNextIndex() {
    return jdbcTemplate.queryForObject(
        "SELECT `auto_increment` " +
        "FROM INFORMATION_SCHEMA.TABLES " +
        "WHERE table_name = 'chat_logs'",
        Integer.class
    );
  }
}
