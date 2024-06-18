package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Payments;

import java.util.List;
import java.util.Optional;

public interface PaymentsService {
    List<Payments> findAll();

    Optional<Payments> findById(Long id);

    Payments save(Payments payments);

    void deleteById(Long id);
}
