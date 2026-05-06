package org.example.project.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    val status: String,
    val totalResults: Int = 0,
    val articles: List<ArticleDto> = emptyList(),
    val code: String? = null,
    val message: String? = null
)

@Serializable
data class ArticleDto(
    val source: SourceDto? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    @SerialName("urlToImage") val imageUrl: String? = null,
    val publishedAt: String? = null,
    val content: String? = null
)

@Serializable
data class SourceDto(
    val id: String? = null,
    val name: String? = null
)
