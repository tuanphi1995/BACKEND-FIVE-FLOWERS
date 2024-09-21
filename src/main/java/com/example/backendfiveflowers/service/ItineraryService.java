package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.*;
import com.example.backendfiveflowers.repository.DayRepository;
import com.example.backendfiveflowers.repository.HourRepository;
import com.example.backendfiveflowers.repository.ItineraryRepository;
import com.example.backendfiveflowers.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryService {

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private HourRepository hourRepository;

    public List<Itinerary> getAllItineraries() {
        return itineraryRepository.findAll();
    }




    public Itinerary saveItinerary(Itinerary itinerary) {
        // Kiểm tra nếu danh sách days hoặc hours có null thì bỏ qua hoặc khởi tạo danh sách rỗng
        if (itinerary.getDays() != null) {
            for (Day day : itinerary.getDays()) {
                if (day.getHours() != null) {
                    for (Hour hour : day.getHours()) {
                        if (hour.getTime() == null) {
                            // Nếu hour null, có thể bỏ qua hoặc set mặc định giá trị cho hour
                            hour.setTime(LocalTime.now()); // Đặt giờ mặc định nếu cần
                        }
                    }
                }
            }
        }
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

        // Cập nhật description nếu có
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
                    // Cập nhật ngày
                    if (updatedDay.getDate() != null) {
                        existingDay.setDate(updatedDay.getDate());
                    }

                    // Cập nhật hours
                    if (updatedDay.getHours() != null && !updatedDay.getHours().isEmpty()) {
                        existingDay.getHours().clear();
                        existingDay.getHours().addAll(updatedDay.getHours());
                    }
                } else {
                    // Thêm ngày mới nếu không tồn tại
                    existingItinerary.getDays().add(updatedDay);
                }
            }
        }

        return itineraryRepository.save(existingItinerary); // Lưu lại sau khi cập nhật
    }
    public Itinerary addDayToItinerary(Long itineraryId, Day newDay) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));

        // Nếu danh sách days của itinerary là null, khởi tạo danh sách mới
        if (itinerary.getDays() == null) {
            itinerary.setDays(new ArrayList<>());
        }

        // Thêm day vào danh sách
        itinerary.getDays().add(newDay);
        newDay.setItinerary(itinerary);

        return itineraryRepository.save(itinerary);
    }
    public Day addHourToDay(Long itineraryId, Long dayId, Hour newHour) {
        Itinerary itinerary = itineraryRepository.findById(itineraryId)
                .orElseThrow(() -> new RuntimeException("Itinerary not found with id: " + itineraryId));
        Day day = dayRepository.findById(dayId)
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + dayId));

        // Set quan hệ giữa hour và day
        newHour.setDay(day);
        day.getHours().add(newHour); // Thêm giờ mới vào danh sách hours của ngày

        return dayRepository.save(day); // Lưu và trả về ngày đã cập nhật
    }



}
