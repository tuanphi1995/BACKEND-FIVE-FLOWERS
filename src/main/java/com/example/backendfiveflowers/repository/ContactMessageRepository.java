package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}

