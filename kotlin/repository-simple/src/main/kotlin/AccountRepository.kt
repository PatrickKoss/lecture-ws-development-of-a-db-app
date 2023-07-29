package org.lecture

import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.sql.Timestamp

class AccountRepository : AbstractRepository {
    private val con: Connection = DriverManager.getConnection("jdbc:sqlite:accounts.db")
    private val stmt: Statement = con.createStatement()

    init {
        stmt.execute(
            """CREATE TABLE IF NOT EXISTS accounts (
            user_id TEXT PRIMARY KEY,
            username TEXT NOT NULL,
            password TEXT NOT NULL,
            email TEXT NOT NULL,
            created_on TEXT NOT NULL
            )"""
        )
    }

    override fun all(): List<Account> {
        val res = stmt.executeQuery("SELECT user_id, username, password, email, created_on FROM accounts")
        val accounts = mutableListOf<Account>()
        while (res.next()) {
            val timestamp = res.getLong("created_on")
            val timestampObj = Timestamp(timestamp)
            accounts.add(
                Account(
                    res.getString("user_id"),
                    res.getString("username"),
                    res.getString("password"),
                    res.getString("email"),
                    timestampObj
                )
            )
        }
        return accounts
    }

    override fun get(id: String): Account? {
        val res = stmt.executeQuery("SELECT user_id, username, password, email, created_on FROM accounts WHERE user_id = '$id'")
        return if (res.next()) {
            val timestamp = res.getLong("created_on")
            val timestampObj = Timestamp(timestamp)
            Account(
                res.getString("user_id"),
                res.getString("username"),
                res.getString("password"),
                res.getString("email"),
                timestampObj
            )
        } else {
            null
        }
    }

    override fun create(account: Account) {
        val sql = "INSERT INTO accounts (user_id, username, password, email, created_on) VALUES (?, ?, ?, ?, ?)"
        val pstmt = con.prepareStatement(sql)
        pstmt.setString(1, account.id)
        pstmt.setString(2, account.username)
        pstmt.setString(3, account.password)
        pstmt.setString(4, account.email)
        pstmt.setTimestamp(5, account.createdOn)
        pstmt.executeUpdate()
    }

    override fun update(account: Account) {
        val sql = "UPDATE accounts SET username=?, password=?, email=?, created_on=? WHERE user_id=?"
        val pstmt = con.prepareStatement(sql)
        pstmt.setString(1, account.username)
        pstmt.setString(2, account.password)
        pstmt.setString(3, account.email)
        pstmt.setTimestamp(4, account.createdOn)
        pstmt.setString(5, account.id)
        pstmt.executeUpdate()
    }

    override fun delete(id: String) {
        stmt.execute("DELETE FROM accounts WHERE user_id = '$id'")
    }
}