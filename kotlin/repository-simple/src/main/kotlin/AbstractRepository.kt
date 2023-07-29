package org.lecture

interface AbstractRepository {
    fun all(): List<Account>
    fun get(id: String): Account?
    fun create(account: Account)
    fun update(account: Account)
    fun delete(id: String)
}