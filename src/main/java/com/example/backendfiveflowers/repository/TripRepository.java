package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByUserId(Integer userId); // Lưu ý là `User_Id`

}


