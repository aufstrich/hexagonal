package com.iteratec.hexagonal.infrastructure.out.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
class JPATaskRepositoryTest {

    @Autowired
    JPATaskRepository jpaTaskRepository;

    @Test
    void saveAndFind() {
        TaskEntity taskEntity = TaskEntity.builder()
                .id(UUID.fromString("aaab2deb-03ba-41c4-8ff5-0eb5842ed7cd"))
                .description("write unit tests")
                .state(TaskStateEntity.IN_PROGRESS)
                .build();

        jpaTaskRepository.save(taskEntity);

        Optional<TaskEntity> foundTask = jpaTaskRepository.findById(taskEntity.getId());

        Assertions.assertThat(foundTask).contains(taskEntity);
    }

}
