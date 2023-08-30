package dev.ravel.proflow.infrastructure.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Projects")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    private Client client;

    @Column
    private String status;

}
