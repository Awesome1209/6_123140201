package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.example.project.data.NewsResponseDto

class NewsApi(
    private val client: HttpClient
) {
    private val baseUrl = "https://newsapi.org/v2"
    private val apiKey = "e2eedac5f24a42bbbcc03baf0f15f00c"

    suspend fun getArticles(): NewsResponseDto {
        return client.get("${baseUrl}/top-headlines") {
            parameter("country", "us")
            parameter("pageSize", 40)
            parameter("apiKey", apiKey)
        }.body()
    }
}
