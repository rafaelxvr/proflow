package dev.ravel.proflow.infrastructure.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private String creatorName;

    @Column
    private String sectorName;

    @Column
    private String epicName;

    @Column
    private Date startDate;

    @Column
    private Date dueDate;

    @Column
    private String status;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Subtask subtask;
}
