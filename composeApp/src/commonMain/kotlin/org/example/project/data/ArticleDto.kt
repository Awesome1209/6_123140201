package org.example.project.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    val status: String,
    val totalResults: Int = 0,
    val articles: List<NewsArticleDto> = emptyList()
)

@Serializable
data class NewsArticleDto(
    val source: NewsSourceDto? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)

@Serializable
data class NewsSourceDto(
    val id: String? = null,
    val name: String? = null
)
