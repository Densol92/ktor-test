package com.example.services

import com.example.models.UploadUrls
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import java.io.File
import java.util.*

class PictureDownloader(private val client: HttpClient) {
    suspend fun download(urls: UploadUrls): List<String> {
        return urls.urls.map {
            val img = client.get<ByteArray>(it)
            val fullPath = "pics/${UUID.randomUUID()}"
            val previewPath = "${fullPath}_preview"
            val imgFile = File(fullPath)
            imgFile.writeBytes(img)
            createPreview(imgFile.absolutePath, "${imgFile.absolutePath}_preview")
            listOf(fullPath, previewPath)
        }.flatten()
    }
}
