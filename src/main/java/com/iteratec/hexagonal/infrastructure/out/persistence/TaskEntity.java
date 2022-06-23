package com.iteratec.hexagonal.infrastructure.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {
    @Id
    @Column(columnDefinition = "uuid")
    UUID id;
    String description;
    @Enumerated(EnumType.STRING)
    TaskStateEntity state;
}

