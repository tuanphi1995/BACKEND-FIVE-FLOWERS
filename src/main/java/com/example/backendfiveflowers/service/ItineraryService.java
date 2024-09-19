package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Day;
import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.entity.Itinerary;
import com.example.backendfiveflowers.entity.Trip;
import com.example.backendfiveflowers.repository.ItineraryRepository;
import com.example.backendfiveflowers.repository.TripRepository;
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

    // Sửa lịch trình (có mô tả, ngày, giờ, và chi phí) mà không thay đổi các phần khác
    public Itinerary updateItinerary(Long id, Itinerary updatedItinerary) {
        Itinerary existingItinerary = itineraryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + id));

        // Cập nhật description nếu có (giữ nguyên nếu null)
        if (updatedItinerary.getDescription() != null) {
            existingItinerary.setDescription(updatedItinerary.getDescription());
        }

        // Cập nhật danh sách days nếu có
        if (updatedItinerary.getDays() != null && !updatedItinerary.getDays().isEmpty()) {
            for (Day updatedDay : updatedItinerary.getDays()) {
                Day existingDay = existingItinerary.getDays().stream()
                        .filter(day -> day.getId().equals(updatedDay.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingDay != null) {
                    // Cập nhật ngày (giữ nguyên nếu null)
                    if (updatedDay.getDate() != null) {
                        existingDay.setDate(updatedDay.getDate());
                    }

                    // Cập nhật hours (giữ nguyên nếu null)
                    if (updatedDay.getHours() != null && !updatedDay.getHours().isEmpty()) {
                        for (Hour updatedHour : updatedDay.getHours()) {
                            Hour existingHour = existingDay.getHours().stream()
                                    .filter(hour -> hour.getId().equals(updatedHour.getId()))
                                    .findFirst()
                                    .orElse(null);

                            if (existingHour != null) {
                                // Cập nhật giờ (giữ nguyên nếu null)
                                if (updatedHour.getTime() != null) {
                                    existingHour.setTime(updatedHour.getTime());
                                }

                                // Cập nhật mô tả (giữ nguyên nếu null)
                                if (updatedHour.getDescriptions() != null && !updatedHour.getDescriptions().isEmpty()) {
                                    existingHour.setDescriptions(updatedHour.getDescriptions());
                                }

                                // Cập nhật chi phí (giữ nguyên nếu null)
                                if (updatedHour.getExpenses() != null && !updatedHour.getExpenses().isEmpty()) {
                                    existingHour.setExpenses(updatedHour.getExpenses());
                                }
                            }
                        }
                    }
                }
            }
        }

        return itineraryRepository.save(existingItinerary); // Lưu lại sau khi cập nhật
    }
}
