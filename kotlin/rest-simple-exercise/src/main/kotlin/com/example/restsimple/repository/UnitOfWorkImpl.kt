package com.example.restsimple.repository

import com.example.restsimple.model.Student
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UnitOfWorkImpl : UnitOfWork {
    @Transactional
    override fun <T> executeInTransaction(code: () -> T): T? {
        return code.invoke()
    }
}
