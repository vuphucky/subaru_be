package vn.codegym.moneymanagement.service;
import java.util.Optional;

public interface IGenericService<T>{
        Iterable<T> findAll();
        Optional<T> findById(Long id);
        void save(T t);
        void delete(Long id);
    }

