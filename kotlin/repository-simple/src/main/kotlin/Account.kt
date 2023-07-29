package org.lecture

import java.sql.Timestamp

data class Account(
    val id: String,
    var username: String,
    var password: String,
    var email: String,
    val createdOn: Timestamp
)