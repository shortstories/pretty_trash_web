/*
 * @(#) SnapshotRepository.java 9월 09, 2017
 *
 * Copyright 2017 NAVER Corp. All rights Reserved.
 * NAVERCORP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.trash.pretty.prettytrash.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.trash.pretty.prettytrash.model.BlockSnapshot;
import com.trash.pretty.prettytrash.model.Snapshot;
import com.trash.pretty.prettytrash.utils.JsonUtils;

/**
 * @author OhChang Kwon(ohchang.kwon@navercorp.com)
 */
@Repository
public class SnapshotRepository {
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public SnapshotRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public int addSnapshot(Snapshot snapshot) {
    return jdbcTemplate.update("INSERT INTO contents_snapshots(contents_snapshots.index, contents_snapshots.contents) " +
        "VALUES (?, ?)", snapshot.getIndex(), snapshot.getText());
  }

  public Snapshot getLastSnapshot() {
    List<Snapshot> snapshots = jdbcTemplate.query(
        "SELECT basicTable.* " +
            "FROM pretty_trash.contents_snapshots AS basicTable " +
            "INNER JOIN (SELECT max(contents_snapshots.index) AS maxIndex FROM contents_snapshots) AS maxTable ON basicTable.index = maxTable.maxIndex",
        (rs, rowNum) -> {
          Snapshot snapshot = new Snapshot();
          snapshot.setIndex(rs.getInt("index"));
          snapshot.setText(rs.getString("contents"));

          return snapshot;
        }
    );

    if (snapshots.isEmpty() == false) {
      return snapshots.get(0);
    } else {
      return null;
    }
  }

  public int addBlockSnapshot(BlockSnapshot blockSnapshot) {
    return jdbcTemplate.update("INSERT INTO block_snapshots(index, name, block) " +
        "VALUES (?, ?, ?)", blockSnapshot.getIndex(), blockSnapshot.getNickName(), JsonUtils.toJson(blockSnapshot.getBlocks()));
  }

  public BlockSnapshot getLastBlockSnapshot(String nickName) {
    return jdbcTemplate.queryForObject(
        "SELECT max(snapshot.index), snapshot.name, snapshot.blocks " +
            "FROM block_snapshots AS snapshot " +
            "WHERE snapshot.name = ?",
        BlockSnapshot.class, nickName
    );
  }
}
