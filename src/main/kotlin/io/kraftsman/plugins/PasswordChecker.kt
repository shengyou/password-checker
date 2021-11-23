package io.kraftsman.plugins

import io.kraftsman.dtos.HackedPassword
import io.kraftsman.dtos.InspectRequest
import io.kraftsman.dtos.InspectResponse
import io.kraftsman.entities.Password
import io.kraftsman.tables.Passwords
import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configurePasswordChecker() {
    install(ContentNegotiation) {
        json()
    }

    Database.connect(
        url = "jdbc:h2:mem:password-checker;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver"
    )

    transaction {
        SchemaUtils.create(Passwords)
    }

    routing {

        get("/api/v1/passwords") {
            val passwords: List<String> = transaction {
                Password.all().map {
                    it.password
                }
            }

            call.respond(mapOf("hacked-password" to passwords))
        }

        post("/api/v1/passwords/hacked") {
            val hackedPassword = call.receive<HackedPassword>()
            transaction {
                val foundPasswords = Password.find {
                    Passwords.password eq hackedPassword.password
                }.sortedBy { it.id }

                if (foundPasswords.isEmpty()) {
                    Password.new {
                        password = hackedPassword.password
                    }
                }
            }

            call.respondText(
                text = "{\n  \"message\": \"Hacked password stored.\"\n}",
                contentType = ContentType.Application.Json,
                status = HttpStatusCode.Created,
            )
        }

        post("/api/v1/passwords/inspection") {
            val request = call.receive<InspectRequest>()
            val result = transaction {
                val hackedPasswords = Password.find {
                    Passwords.password eq request.password
                }.toList()
                return@transaction hackedPasswords.isNotEmpty()
            }

            call.respond(InspectResponse(
                result = result,
                message = if (result) "Password Hacked" else "Password Safe"
            ))
        }
    }
}
