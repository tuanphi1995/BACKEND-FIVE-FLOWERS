package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Payments;
import com.example.backendfiveflowers.repository.PaymentsRepository;
import com.example.backendfiveflowers.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentsServiceImpl implements PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Override
    public List<Payments> findAll() {
        return paymentsRepository.findAll();
    }

    @Override
    public Page<Payments> findAll(Pageable pageable) {
        return paymentsRepository.findAll(pageable);
    }

    @Override
    public Optional<Payments> findById(Long id) {
        return paymentsRepository.findById(id);
    }

    @Override
    public Payments save(Payments payments) {
        return paymentsRepository.save(payments);
    }

    @Override
    public void deleteById(Long id) {
        paymentsRepository.deleteById(id);
    }
}
