package org.example.project.repository

import org.example.project.data.Article
import org.example.project.network.HttpClientFactory
import org.example.project.network.NewsApi

class NewsRepository(
    private val api: NewsApi = NewsApi(HttpClientFactory.create())
) {
    suspend fun getArticles(): Result<List<Article>> {
        return try {
            val response = api.getArticles()
            val result = response.articles
                .mapIndexed { index, dto ->
                    val safeTitle = dto.title?.takeIf { it.isNotBlank() } ?: "Untitled article"
                    val safeDescription = dto.description?.takeIf { it.isNotBlank() }
                        ?: dto.content?.takeIf { it.isNotBlank() }
                        ?: "No description available."

                    val mergedText = listOfNotNull(
                        dto.title,
                        dto.description,
                        dto.content,
                        dto.source?.name
                    ).joinToString(" ")

                    Article(
                        id = index + 1,
                        title = cleanText(safeTitle),
                        description = cleanText(safeDescription).take(160),
                        content = cleanText(dto.content ?: dto.description ?: safeDescription),
                        category = inferCategory(mergedText),
                        imageUrl = dto.urlToImage ?: ""
                    )
                }
                .filter { it.title.isNotBlank() }

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun inferCategory(text: String): String {
        val lower = text.lowercase()
        return when {
            lower.contains("technology") || lower.contains("ai") || lower.contains("software") || lower.contains("google") || lower.contains("apple") -> "Technology"
            lower.contains("education") || lower.contains("school") || lower.contains("university") || lower.contains("student") -> "Education"
            lower.contains("campus") || lower.contains("college") || lower.contains("lecture") -> "Campus"
            lower.contains("life") || lower.contains("style") || lower.contains("travel") || lower.contains("food") || lower.contains("fashion") -> "Lifestyle"
            else -> "General"
        }
    }

    private fun cleanText(value: String): String {
        return value
            .replace(Regex("""\[\+\d+\s+chars]"""), "")
            .replace(Regex("""\s+"""), " ")
            .trim()
    }
}
