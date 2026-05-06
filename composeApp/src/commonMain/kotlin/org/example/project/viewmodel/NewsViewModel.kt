package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.Article
import org.example.project.data.remote.HttpClientFactory
import org.example.project.data.remote.NewsApi
import org.example.project.data.repository.NewsRepository

class NewsViewModel(
    private val repository: NewsRepository = NewsRepository(
        NewsApi(HttpClientFactory.create())
    )
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        loadTopHeadlines()
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun submitSearch() {
        loadTopHeadlines(forceRefresh = true)
    }

    fun clearSearch() {
        _uiState.update { it.copy(searchQuery = "") }
        loadTopHeadlines(forceRefresh = true)
    }

    fun refresh() {
        loadTopHeadlines(forceRefresh = true)
    }

    fun retry() {
        loadTopHeadlines(forceRefresh = false)
    }

    fun toggleFavorite(articleId: Int) {
        _uiState.update { current ->
            val nextFavorites = if (current.favoriteArticleIds.contains(articleId)) {
                current.favoriteArticleIds - articleId
            } else {
                current.favoriteArticleIds + articleId
            }

            current.copy(
                favoriteArticleIds = nextFavorites,
                articles = current.articles.map { article ->
                    article.copy(isFavorite = nextFavorites.contains(article.id))
                }
            )
        }
    }

    fun getArticleById(articleId: Int): Article? {
        return _uiState.value.articles.firstOrNull { it.id == articleId }
    }

    private fun loadTopHeadlines(forceRefresh: Boolean = false) {
        val query = _uiState.value.searchQuery.trim()

        viewModelScope.launch {
            _uiState.update { current ->
                current.copy(
                    isLoading = current.articles.isEmpty() && !forceRefresh,
                    isRefreshing = forceRefresh,
                    errorMessage = null
                )
            }

            repository.getTopHeadlines(query = query)
                .onSuccess { articles ->
                    _uiState.update { current ->
                        val favorites = current.favoriteArticleIds
                        current.copy(
                            articles = articles.map { article ->
                                article.copy(isFavorite = favorites.contains(article.id))
                            },
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = null,
                            lastUpdatedLabel = "Baru saja diperbarui"
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = error.message ?: "Terjadi kesalahan saat mengambil berita."
                        )
                    }
                }
        }
    }
}
