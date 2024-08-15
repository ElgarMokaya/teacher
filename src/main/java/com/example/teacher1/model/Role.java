package com.example.teacher1.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Enumerated(EnumType.STRING)
    private String name;


    @OneToMany(mappedBy ="roles")
    private List<Teacher> teacher;
}
