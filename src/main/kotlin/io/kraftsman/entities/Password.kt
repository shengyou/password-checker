package io.kraftsman.entities

import io.kraftsman.tables.Passwords
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Password(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Password>(Passwords)

    var password by Passwords.password
}
