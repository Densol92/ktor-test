package com.example

import com.example.models.UploadUrls
import com.example.services.PictureDownloader
import com.example.utils.serverUrl
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.locations.Locations
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient() {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(Locations) {
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        static("pics") {
            files("pics")
        }

        post("/upload") {
            print("oh")
            val urls = call.receive<UploadUrls>()
            val res = PictureDownloader(client).download(urls)

            val nr = res.map {
                val url = call.request.serverUrl()
                url.encodedPath = it
                url.buildString()
            }.toList()
            call.respond(nr)
        }

    }
}


