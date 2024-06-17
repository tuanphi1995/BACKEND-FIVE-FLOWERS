package com.example.backendfiveflowers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long color_id;
    private String color_name;

    @OneToMany(mappedBy = "productColor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ProductColorMapping> productColorMappings;
}
