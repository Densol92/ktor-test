package com.example

import com.example.models.UploadUrls
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testUpload() {
        val body = UploadUrls(
            listOf(
                "https://picsum.photos/id/1/200/200",
                "https://picsum.photos/id/2/200/200",
                "https://picsum.photos/id/3/200/200",
                "https://picsum.photos/id/4/200/200"
            )
        )

        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/upload") {
                setBody(jacksonObjectMapper().writeValueAsString(body))
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            }
                .apply {
                    assertEquals(HttpStatusCode.OK, response.status())
//                    assertEquals("HELLO WORLD!", response.content)
                }
        }
    }
}
