package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.CalorieConsumption;
import com.example.backendfiveflowers.repository.CalorieConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class CalorieConsumptionService {

    @Autowired
    private CalorieConsumptionRepository calorieConsumptionRepository;

    public ResponseEntity<List<CalorieConsumption>> getAll(){
        return ResponseEntity.ok(calorieConsumptionRepository.findAll());
    }
    public ResponseEntity<List<CalorieConsumption>> getAllByUserId(int id){
        List<CalorieConsumption> calorieConsumption = calorieConsumptionRepository.findCalorieConsumptionsByUserInfo(id);
        if (calorieConsumption.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(calorieConsumption);
    }


    public ResponseEntity<CalorieConsumption> save(CalorieConsumption calorieConsumption) {
        calorieConsumption.calculateCalories();
       CalorieConsumption calorieConsumption1 =  calorieConsumptionRepository.save(calorieConsumption);
       URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/id").buildAndExpand(calorieConsumption1.getCaloId()).toUri();
       return ResponseEntity.created(location).body(calorieConsumption1);
    }

}
