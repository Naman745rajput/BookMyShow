package com.naman.bms.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "theaters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(nullable = false)
    private String city;

    private Integer totalScreens;

    @OneToMany(mappedBy = "theater", cascade = CascadeType.ALL)
    private List<Screen> screens;
}
