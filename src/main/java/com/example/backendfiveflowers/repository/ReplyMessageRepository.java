package com.example.backendfiveflowers.repository;

import com.example.backendfiveflowers.entity.ReplyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyMessageRepository extends JpaRepository<ReplyMessage, Long> {
}
