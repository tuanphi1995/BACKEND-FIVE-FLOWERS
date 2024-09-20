package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.*;
import com.example.backendfiveflowers.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ItineraryRepository iTineraryRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private HourRepository hourRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    public List<Trip> addTrips(List<Trip> trips) {
        trips.forEach(trip -> {
            if (trip.getUser() == null || trip.getUser().getId() == null) {
                throw new RuntimeException("User information is missing");
            }

            // Tìm người dùng trong cơ sở dữ liệu
            UserInfo user = userInfoRepository.findById(trip.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            trip.setUser(user); // Gán user cho trip

            // Kiểm tra và xử lý các thực thể con
            if (trip.getItineraries() != null) {
                trip.getItineraries().forEach(itinerary -> {
                    itinerary.setTrip(trip);
                    if (itinerary.getDays() != null) {
                        itinerary.getDays().forEach(day -> {
                            day.setItinerary(itinerary);
                            if (day.getHours() != null) {
                                day.getHours().forEach(hour -> {
                                    hour.setDay(day);

                                    if (hour.getExpenses() != null) {
                                        hour.getExpenses().forEach(expense -> expense.setHour(hour));
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });

        return tripRepository.saveAll(trips);
    }

    // Sửa thông tin danh sách các chuyến đi và các thực thể liên quan
    public List<Trip> updateTripsWithDetails(List<Long> tripIds, List<Trip> updatedTrips) {
        if (tripIds.size() != updatedTrips.size()) {
            throw new IllegalArgumentException("The number of trip IDs and updated trips do not match.");
        }

        for (int i = 0; i < tripIds.size(); i++) {
            Long tripId = tripIds.get(i);
            Trip updatedTrip = updatedTrips.get(i);
            Trip existingTrip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

            // Cập nhật các trường của Trip nếu có, sử dụng giá trị mặc định nếu cần
            existingTrip.setTripName(updatedTrip.getTripName() != null ? updatedTrip.getTripName() : "No data");
            existingTrip.setStartLocation(updatedTrip.getStartLocation() != null ? updatedTrip.getStartLocation() : "No data");
            existingTrip.setEndLocation(updatedTrip.getEndLocation() != null ? updatedTrip.getEndLocation() : "No data");
            existingTrip.setTotalBudget(updatedTrip.getTotalBudget() != null ? updatedTrip.getTotalBudget() : null); // Cho phép null
            existingTrip.setDistance(updatedTrip.getDistance() != null ? updatedTrip.getDistance() : "No data");

            // Xử lý cập nhật các thực thể con (Itinerary, Day, Hour, Description, Expense)
            if (updatedTrip.getItineraries() != null) {
                existingTrip.getItineraries().clear(); // Xóa các lịch trình cũ
                updatedTrip.getItineraries().forEach(itinerary -> {
                    itinerary.setTrip(existingTrip); // Gán trip cho itinerary mới

                    if (itinerary.getDays() != null) {
                        itinerary.getDays().forEach(day -> {
                            day.setItinerary(itinerary); // Gán itinerary cho day

                            if (day.getHours() != null) {
                                day.getHours().forEach(hour -> {
                                    hour.setDay(day); // Gán day cho hour



                                    if (hour.getExpenses() != null) {
                                        hour.getExpenses().forEach(expense -> {
                                            expense.setHour(hour); // Gán hour cho expense
                                        });
                                    }
                                });
                            }
                        });
                    }

                    existingTrip.getItineraries().add(itinerary);
                });
            }

            tripRepository.save(existingTrip);
        }

        return updatedTrips;
    }

    // Xóa danh sách các chuyến đi và các thực thể liên quan
    public void deleteTripsByIds(List<Long> tripIds) {
        tripIds.forEach(tripId -> {
            Trip trip = tripRepository.findById(tripId)
                    .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));
            tripRepository.delete(trip); // Xóa Trip và các thực thể con nhờ CascadeType.ALL
        });
    }

    public void deleteDaysByIds(List<Long> dayIds) {
        dayIds.forEach(dayId -> {
            Day day = dayRepository.findById(dayId)
                    .orElseThrow(() -> new RuntimeException("Day not found with id: " + dayId));
            dayRepository.delete(day); // Xóa Day và các thực thể con nhờ CascadeType.ALL
        });
    }

    public Trip getTripById(Long tripId) {
        return tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));
    }
    public void deleteItinerariesByIds(List<Long> itineraryIds) {
        itineraryIds.forEach(itineraryId -> {
            Itinerary itinerary = iTineraryRepository.findById(itineraryId)
                    .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));
            iTineraryRepository.delete(itinerary); // Xóa Itinerary và các thực thể con nhờ CascadeType.ALL
        });
    }
    public List<Trip> getTripsByUserId(Integer userId) {
        return tripRepository.findByUser_Id(userId);
    }

    public void deleteHoursByIds(List<Long> hourIds) {
        hourIds.forEach(hourId -> {
            Hour hour = hourRepository.findById(hourId)
                    .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));
            hourRepository.delete(hour); // Xóa Hour và các thực thể con nhờ CascadeType.ALL
        });
    }


    public void deleteExpensesByIds(List<Long> expenseIds) {
        expenseIds.forEach(expenseId -> {
            Expense expense = expenseRepository.findById(expenseId)
                    .orElseThrow(() -> new RuntimeException("Expense not found with id: " + expenseId));
            expenseRepository.delete(expense);
        });
    }
    public Trip addItineraryToTrip(Long tripId, List<Itinerary> itineraries) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

        for (Itinerary itinerary : itineraries) {
            itinerary.setTrip(trip); // Gán Trip cho từng Itinerary
            for (Day day : itinerary.getDays()) {
                day.setItinerary(itinerary); // Gán Itinerary cho từng Day
                for (Hour hour : day.getHours()) {
                    hour.setDay(day); // Gán Day cho từng Hour
                    for (Expense expense : hour.getExpenses()) {
                        expense.setHour(hour); // Gán Hour cho từng Expense
                    }

                }
            }
            itineraryRepository.save(itinerary); // Lưu Itinerary cùng các phần tử liên quan
        }

        trip.getItineraries().addAll(itineraries); // Thêm Itinerary vào Trip
        return tripRepository.save(trip); // Lưu lại Trip sau khi thêm Itinerary
    }
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
    public Trip updateTripWithDetails(Long tripId, Trip updatedTrip) {
        Trip existingTrip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

        // Cập nhật các trường tương ứng
        existingTrip.setTripName(updatedTrip.getTripName());
        existingTrip.setStartLocation(updatedTrip.getStartLocation());
        existingTrip.setEndLocation(updatedTrip.getEndLocation());
        existingTrip.setTotalBudget(updatedTrip.getTotalBudget());

        // Đảm bảo cập nhật các trường cần thiết
        existingTrip.setDistance(updatedTrip.getDistance());
        existingTrip.setStartDate(updatedTrip.getStartDate());
        existingTrip.setEndDate(updatedTrip.getEndDate());

        // Xử lý các thực thể con nếu cần
        if (updatedTrip.getItineraries() != null) {
            existingTrip.getItineraries().clear();
            existingTrip.getItineraries().addAll(updatedTrip.getItineraries());
        }

        return tripRepository.save(existingTrip);
    }


}
