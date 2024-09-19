package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.*;
import com.example.backendfiveflowers.repository.TripRepository;
import com.example.backendfiveflowers.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    // Lưu chuyến đi và tất cả các thông tin liên quan
    public Trip addTripWithDetails(Trip trip) {
        if (trip.getUser() == null || trip.getUser().getId() == null) {
            throw new RuntimeException("User information is missing in the trip");
        }

        // Tìm thông tin người dùng từ database
        UserInfo user = userInfoRepository.findById(trip.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        trip.setUser(user); // Gán user cho trip

        // Xử lý các thực thể con như Itinerary, Day, Hour, Description, Expense
        trip.getItineraries().forEach(itinerary -> {
            itinerary.setTrip(trip); // Gán trip cho itinerary

            itinerary.getDays().forEach(day -> {
                day.setItinerary(itinerary); // Gán itinerary cho day

                day.getHours().forEach(hour -> {
                    hour.setDay(day); // Gán day cho hour

                    hour.getDescriptions().forEach(description -> {
                        description.setHour(hour); // Gán hour cho description
                    });

                    hour.getExpenses().forEach(expense -> {
                        expense.setHour(hour); // Gán hour cho expense
                    });
                });
            });
        });

        return tripRepository.save(trip); // Lưu toàn bộ trip và các thực thể liên quan
    }

    // Sửa thông tin chuyến đi và các thực thể liên quan
    public Trip updateTripWithDetails(Long tripId, Trip updatedTrip) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // Cập nhật các trường của Trip nếu có
        if (updatedTrip.getTripName() != null) {
            existingTrip.setTripName(updatedTrip.getTripName());
        }

        if (updatedTrip.getStartDate() != null) {
            existingTrip.setStartDate(updatedTrip.getStartDate());
        }

        if (updatedTrip.getEndDate() != null) {
            existingTrip.setEndDate(updatedTrip.getEndDate());
        }

        if (updatedTrip.getStartLocation() != null) {
            existingTrip.setStartLocation(updatedTrip.getStartLocation());
        }

        if (updatedTrip.getEndLocation() != null) {
            existingTrip.setEndLocation(updatedTrip.getEndLocation());
        }

        if (updatedTrip.getTotalBudget() != null) {
            existingTrip.setTotalBudget(updatedTrip.getTotalBudget());
        }

        // Cập nhật trường khoảng cách nếu có
        if (updatedTrip.getDistance() != null) {
            existingTrip.setDistance(updatedTrip.getDistance());
        }

        // Xử lý cập nhật các thực thể con (Itinerary, Day, Hour, Description, Expense)
        if (updatedTrip.getItineraries() != null) {
            existingTrip.getItineraries().clear(); // Xóa các lịch trình cũ
            updatedTrip.getItineraries().forEach(itinerary -> {
                itinerary.setTrip(existingTrip); // Gán trip cho itinerary mới

                itinerary.getDays().forEach(day -> {
                    day.setItinerary(itinerary); // Gán itinerary cho day

                    day.getHours().forEach(hour -> {
                        hour.setDay(day); // Gán day cho hour

                        hour.getDescriptions().forEach(description -> {
                            description.setHour(hour); // Gán hour cho description
                        });

                        hour.getExpenses().forEach(expense -> {
                            expense.setHour(hour); // Gán hour cho expense
                        });
                    });
                });

                existingTrip.getItineraries().add(itinerary);
            });
        }

        return tripRepository.save(existingTrip); // Lưu lại sau khi cập nhật
    }

    // Xóa chuyến đi và các thực thể liên quan
    public void deleteTripById(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));
        tripRepository.delete(trip); // Xóa chuyến đi
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));
    }
    public List<Trip> getTripsByUserId(Integer userId) {
        return tripRepository.findByUser_Id(userId);
    }
}
