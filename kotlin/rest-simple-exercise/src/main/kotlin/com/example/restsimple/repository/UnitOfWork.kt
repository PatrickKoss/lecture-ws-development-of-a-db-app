package com.example.restsimple.repository

import com.example.restsimple.model.Student
import org.springframework.stereotype.Service

@Service
interface UnitOfWork {
    fun <T> executeInTransaction(code: () -> T): T?
}

