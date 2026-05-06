package org.example.project.viewmodel

import org.example.project.data.Article

data class NewsUiState(
    val articles: List<Article> = emptyList(),
    val favoriteArticleIds: Set<Int> = emptySet(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val lastUpdatedLabel: String = "Belum dimuat"
) {
    val totalArticles: Int get() = articles.size
    val favoriteArticles: List<Article>
        get() = articles.filter { favoriteArticleIds.contains(it.id) }
}
