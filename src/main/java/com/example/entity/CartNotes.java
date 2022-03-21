package com.example.entity;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Table(name = "cart_notes")
public class CartNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String notes;

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
