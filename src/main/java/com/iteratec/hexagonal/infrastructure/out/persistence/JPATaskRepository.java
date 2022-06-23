package com.iteratec.hexagonal.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JPATaskRepository extends JpaRepository<TaskEntity, UUID> {
}
