package com.lostandfound.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class LostItem extends Item {
    @Column(nullable = false)
    private String locationLost;
}
