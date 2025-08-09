package com.financeservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Getter
@Setter
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    private Double amount;

    @NotBlank (message = "Category is required")
    private String category;

    private String description;

    @Column(name = "date")
    private LocalDate date ;


    @Column(name = "user_email_id")
    private String userEmail;



}
