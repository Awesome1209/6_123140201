package org.example.project.data.repository

import kotlin.math.absoluteValue
import org.example.project.data.Article
import org.example.project.data.remote.NewsApi
import org.example.project.data.remote.dto.ArticleDto

class NewsRepository(
    private val api: NewsApi
) {
    suspend fun getTopHeadlines(query: String = ""): Result<List<Article>> {
        return runCatching {
            val response = api.getTopHeadlines(query = query)
            if (response.status != "ok") {
                error(response.message ?: "NewsAPI mengembalikan status ${response.status}.")
            }

            response.articles
                .mapIndexedNotNull { index, dto -> dto.toArticle(index) }
                .filterNot { it.title.equals("[Removed]", ignoreCase = true) }
        }
    }
}

private fun ArticleDto.toArticle(index: Int): Article? {
    val safeTitle = title?.trim().orEmpty()
    if (safeTitle.isBlank()) return null

    val safeDescription = description?.trim()
        ?: content?.trim()
        ?: "Deskripsi belum tersedia dari sumber berita."

    val safeContent = content?.trim()
        ?: description?.trim()
        ?: "Konten lengkap belum tersedia. Buka sumber asli untuk membaca artikel selengkapnya."

    return Article(
        id = createStableId(index),
        title = safeTitle,
        description = safeDescription,
        content = safeContent,
        imageUrl = imageUrl?.takeIf { it.isNotBlank() },
        sourceName = source?.name?.takeIf { it.isNotBlank() } ?: "Unknown Source",
        author = author?.takeIf { it.isNotBlank() },
        publishedAt = publishedAt?.takeIf { it.isNotBlank() } ?: "Unknown date",
        url = url?.takeIf { it.isNotBlank() },
        isFavorite = false
    )
}

private fun ArticleDto.createStableId(index: Int): Int {
    val raw = (url ?: "$title-$publishedAt-$index").hashCode()
    return if (raw == Int.MIN_VALUE) index + 1 else raw.absoluteValue
}
