package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Address;
import com.example.backendfiveflowers.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser(UserInfo user);
}
