package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.Route;
import com.example.backendfiveflowers.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByUser(UserInfo user);
}
