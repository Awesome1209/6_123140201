package org.example.project.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.example.project.config.ApiConfig
import org.example.project.data.remote.dto.NewsResponseDto

class NewsApi(
    private val client: HttpClient
) {
    private val baseUrl = "https://newsapi.org/v2"

    suspend fun getTopHeadlines(
        country: String = "us",
        pageSize: Int = 20,
        query: String = ""
    ): NewsResponseDto {
        val apiKey = ApiConfig.newsApiKey.trim()
        require(apiKey.isNotBlank()) {
            "NEWS_API_KEY belum diatur. Tambahkan NEWS_API_KEY di local.properties lalu sync ulang project."
        }

        return client.get("$baseUrl/top-headlines") {
            parameter("country", country)
            parameter("pageSize", pageSize)
            if (query.isNotBlank()) {
                parameter("q", query.trim())
            }
            header("X-Api-Key", apiKey)
        }.body()
    }
}
