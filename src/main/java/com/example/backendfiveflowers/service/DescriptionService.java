package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Description;
import com.example.backendfiveflowers.entity.Hour;
import com.example.backendfiveflowers.repository.DescriptionRepository;
import com.example.backendfiveflowers.repository.HourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DescriptionService {

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private HourRepository hourRepository;

    // Thêm hoặc cập nhật mô tả (Description) cho một giờ
    public Description saveOrUpdateDescription(Long hourId, Description description) {
        Hour hour = hourRepository.findById(hourId)
                .orElseThrow(() -> new RuntimeException("Hour not found with id: " + hourId));
        description.setHour(hour);  // Gán giờ cho mô tả
        return descriptionRepository.save(description);
    }

    // Sửa mô tả (Description) độc lập
    public Description updateDescription(Long descriptionId, Description updatedDescription) {
        Description existingDescription = descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new RuntimeException("Description not found with id: " + descriptionId));

        if (updatedDescription.getContent() != null) {
            existingDescription.setContent(updatedDescription.getContent());
        }

        return descriptionRepository.save(existingDescription);
    }

    // Lấy mô tả (Description) theo ID
    public Description getDescriptionById(Long descriptionId) {
        return descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new RuntimeException("Description not found with id: " + descriptionId));
    }

    // Xóa mô tả (Description) theo ID
    public void deleteDescriptionById(Long descriptionId) {
        Description description = descriptionRepository.findById(descriptionId)
                .orElseThrow(() -> new RuntimeException("Description not found with id: " + descriptionId));
        descriptionRepository.delete(description);
    }
}
