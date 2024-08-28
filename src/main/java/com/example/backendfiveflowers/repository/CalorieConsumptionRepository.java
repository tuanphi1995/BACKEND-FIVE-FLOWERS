package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.CalorieConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalorieConsumptionRepository extends JpaRepository<CalorieConsumption, Integer> {
   @Query("select c from CalorieConsumption c where c.userInfo.id = :userId")
    List<CalorieConsumption> findCalorieConsumptionsByUserInfo(@Param("userId") int userId);
}
