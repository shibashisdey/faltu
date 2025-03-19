package com.example.pro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student {
    @Id
    @NotBlank(message = "Admin number is required")
    private String adminNo;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Standard is required")
    private String standard;

    @NotBlank(message = "Section is required")
    @Pattern(regexp = "[A-Z]", message = "Section should be a single uppercase letter")
    private String section;
}