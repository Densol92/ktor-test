package com.example.utils

import io.ktor.features.origin
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.request.ApplicationRequest

fun ApplicationRequest.serverUrl(): URLBuilder {
    val builder = URLBuilder().apply {
        protocol = URLProtocol.byName[origin.scheme] ?: URLProtocol(origin.scheme, 0)
        host = origin.host.substringBefore(":")
        port = origin.port
    }
    return builder
}


