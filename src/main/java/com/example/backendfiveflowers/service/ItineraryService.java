package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Itinerary;
import com.example.backendfiveflowers.repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItineraryService {
    @Autowired
    private ItineraryRepository itineraryRepository;

    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }

    public Itinerary saveItinerary(Itinerary itinerary) {
        return itineraryRepository.save(itinerary);
    }

    // Xóa lịch trình theo ID
    public void deleteItineraryById(Long itineraryId) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));
        itineraryRepository.delete(itinerary); // Xóa lịch trình
    }

    public Itinerary getItineraryById(Long id) {
        return itineraryRepository.findById(id).orElse(null);
    }
}

