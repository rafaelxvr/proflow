package dev.ravel.proflow.infrastructure.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="Clients")
@Data
public class Client {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String documentId;

    @Column
    private String email;

}
