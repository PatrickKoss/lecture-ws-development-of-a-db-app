package org.lecture

import java.sql.Timestamp
import java.time.Instant
import java.util.*


fun main() {
    try {
        val repo = AccountRepository()
        val newId = UUID.randomUUID().toString()
        val newAccount = Account(
            newId, "test2", "test2", "test2@test.com", Timestamp.from(Instant.now())
        )
        repo.create(newAccount)
        val accounts = repo.all()
        println("all accounts: $accounts")
        val account = repo.get(accounts[0].id)
        println("single account: $account")
        newAccount.username = "test3"
        repo.update(newAccount)
        val updatedAccount = repo.get(newId)
        println("new account after update: $updatedAccount")
        repo.delete(newId)
        val accountsAtEnd = repo.all()
        println("all accounts at the end: $accountsAtEnd")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
