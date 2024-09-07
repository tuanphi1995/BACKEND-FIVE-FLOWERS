package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Route;
import com.example.backendfiveflowers.entity.UserInfo;
import com.example.backendfiveflowers.repository.RouteRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<Route> getAllRoutesForUser(UserInfo user) {
        return routeRepository.findByUser(user);
    }

    public Route saveRoute(Route route, UserInfo user) {
        route.setUser(user);
        return routeRepository.save(route);
    }
}
