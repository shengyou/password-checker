package io.kraftsman.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Passwords: IntIdTable(name = "passwords") {
    val password = varchar("password", 255)
}
