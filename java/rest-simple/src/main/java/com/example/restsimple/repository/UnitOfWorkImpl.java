package com.example.restsimple.repository;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UnitOfWorkImpl implements UnitOfWork {

    @Override
    @Transactional
    public <T> T executeInTransaction(TransactionCode<T> code) {
        return code.apply();
    }
}

