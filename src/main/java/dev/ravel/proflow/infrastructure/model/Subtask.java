package dev.ravel.proflow.infrastructure.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Subtasks")
@Data
public class Subtask {

    @Id
    @Column
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne
    private Task task;
}
