package com.lostandfound.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Long userId;

    @Temporal(TemporalType.DATE)
    private Date dateReported = new Date();

    public enum Status {
        LOST, FOUND
    }
}
