package com.example.quanlytaichinh_be.service;
import com.example.quanlytaichinh_be.model.Wallet;

import java.util.Optional;

public interface IGenericService<T>{
        Iterable<T> findAll();
        Optional<T> findById(Long id);
        Wallet save(T t);
        void delete(Long id);
    }

