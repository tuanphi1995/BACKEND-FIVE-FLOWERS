package com.example.backendfiveflowers.service.impl;

import com.example.backendfiveflowers.entity.Blogs;
import com.example.backendfiveflowers.repository.BlogsRepository;
import com.example.backendfiveflowers.service.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogsServiceImpl implements BlogsService {

    @Autowired
    private BlogsRepository blogsRepository;

    @Override
    public List<Blogs> findAll() {
        return blogsRepository.findAll();
    }

    @Override
    public Page<Blogs> findAll(Pageable pageable) {
        return blogsRepository.findAll(pageable);
    }

    @Override
    public Optional<Blogs> findById(Long id) {
        return blogsRepository.findById(id);
    }

    @Override
    public Blogs save(Blogs blogs) {
        return blogsRepository.save(blogs);
    }

    @Override
    public void deleteById(Long id) {
        blogsRepository.deleteById(id);
    }
}
