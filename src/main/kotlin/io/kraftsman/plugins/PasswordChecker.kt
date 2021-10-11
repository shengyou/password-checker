package io.kraftsman.plugins

import io.kraftsman.dtos.InspectResult
import io.kraftsman.dtos.Inspection
import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configurePasswordChecker() {
    install(ContentNegotiation) {
        json()
    }

    routing {

        post("/api/v1/inspection") {

            val inspection = call.receive<Inspection>()
            val result = InspectResult(false, inspection.password)

            call.respond(result)
        }
    }
}
