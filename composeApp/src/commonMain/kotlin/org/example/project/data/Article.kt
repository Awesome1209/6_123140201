package org.example.project.data

data class Article(
    val id: Int,
    val title: String,
    val description: String,
    val content: String,
    val imageUrl: String?,
    val sourceName: String,
    val author: String?,
    val publishedAt: String,
    val url: String?,
    val isFavorite: Boolean = false
)
