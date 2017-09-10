package com.trash.pretty.prettytrash.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.trash.pretty.prettytrash.model.Snapshot;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
public class SnapshotRepositoryTest {
  private static final Logger logger = LoggerFactory.getLogger(SnapshotRepositoryTest.class);

  @Autowired
  private SnapshotRepository repository;

  @Autowired
  private ChatRepository chatRepository;

  @Test
  public void getLastSnapshot() throws Exception {
    // Given
//    Snapshot testCase = new Snapshot();
//    testCase.setIndex(100);
//    testCase.setText("small");
//    repository.addSnapshot(testCase);
//    testCase.setIndex(200);
//    testCase.setText("should be this");
//    repository.addSnapshot(testCase);

    // When
    Snapshot result = repository.getLastSnapshot();

    assertThat(result)
        .isNotNull();
//        .isEqualToComparingFieldByField(testCase);

    logger.debug("result: {}", result);
  }

  @Test
  public void getBlockSnapshot() throws Exception {
  }

}