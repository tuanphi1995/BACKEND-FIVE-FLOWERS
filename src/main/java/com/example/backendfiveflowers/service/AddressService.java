package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> findAll();

    Page<Address> findAll(Pageable pageable); // Thêm phương thức này
    Optional<Address> findById(Long id);
    Address save(Address address);
    void deleteById(Long id);
}
