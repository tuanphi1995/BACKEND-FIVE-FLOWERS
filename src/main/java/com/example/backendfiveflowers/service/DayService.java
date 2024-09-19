package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Day;
import com.example.backendfiveflowers.entity.Itinerary;
import com.example.backendfiveflowers.repository.DayRepository;
import com.example.backendfiveflowers.repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DayService {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    // Thêm hoặc cập nhật ngày (Day) cho một lịch trình
    public Day saveOrUpdateDay(Long itineraryId, Day day) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));
        day.setItinerary(itinerary);  // Gán lịch trình cho ngày
        return dayRepository.save(day);
    }

    // Sửa ngày (Day) độc lập
    public Day updateDay(Long dayId, Day updatedDay) {
        Day existingDay = dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + dayId));

        if (updatedDay.getDate() != null) {
            existingDay.setDate(updatedDay.getDate());
        }

        return dayRepository.save(existingDay);
    }

    // Lấy ngày (Day) theo ID
    public Day getDayById(Long dayId) {
        return dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + dayId));
    }

    // Xóa ngày (Day) theo ID
    public void deleteDayById(Long id) {
        Day day = dayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + id));
        dayRepository.delete(day);
    }

    // Xóa nhiều ngày theo danh sách ID
    public void deleteDaysByIds(List<Long> dayIds) {
        dayIds.forEach(this::deleteDayById);
    }

    // Lấy tất cả các ngày trong lịch trình
    public List<Day> getAllDays() {
        return dayRepository.findAll();
    }
}
