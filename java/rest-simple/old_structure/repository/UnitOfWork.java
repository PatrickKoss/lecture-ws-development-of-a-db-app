package com.example.restsimple.repository;

import org.springframework.stereotype.Service;

@Service
public interface UnitOfWork {

    <T> T executeInTransaction(TransactionCode<T> code);

    interface TransactionCode<T> {
        T apply();
    }
}
