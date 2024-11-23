package com.lostandfound.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // LOST or FOUND

    @Column(nullable = false)
    private LocalDate dateReported;

    @Column(nullable = true)
    private String locationFound;  // Location where the item was found

    @Column(nullable = true)
    private String locationLost ; // Location where the item was lost

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who reported the item

    public enum Status {
        LOST, FOUND
    }
}