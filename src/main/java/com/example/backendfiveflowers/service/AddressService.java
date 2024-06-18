package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> findAll();

    Optional<Address> findById(Long id);

    Address save(Address address);

    void deleteById(Long id);
}
