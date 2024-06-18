package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
