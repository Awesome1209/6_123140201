package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.Article
import org.example.project.data.UiState
import org.example.project.repository.NewsRepository

data class NewsFilterState(
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val isRefreshing: Boolean = false
)

class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    private val _filterState = MutableStateFlow(NewsFilterState())
    val filterState: StateFlow<NewsFilterState> = _filterState.asStateFlow()

    val categories = listOf("All", "Technology", "Education", "Campus", "Lifestyle", "General")

    init {
        loadArticles()
    }

    fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getArticles()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Unable to load articles."
                    )
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _filterState.update { it.copy(isRefreshing = true) }
            repository.getArticles()
                .onSuccess { articles ->
                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Unable to refresh articles."
                    )
                }
            _filterState.update { it.copy(isRefreshing = false) }
        }
    }

    fun onSearchQueryChange(query: String) {
        _filterState.update { it.copy(searchQuery = query) }
    }

    fun onCategorySelected(category: String) {
        _filterState.update { it.copy(selectedCategory = category) }
    }

    fun getFilteredArticles(): List<Article> {
        val state = _uiState.value
        if (state !is UiState.Success) return emptyList()

        val query = _filterState.value.searchQuery.trim().lowercase()
        val category = _filterState.value.selectedCategory

        return state.data.filter { article ->
            val categoryMatches = category == "All" || article.category == category
            val queryMatches = query.isBlank() ||
                article.category.lowercase().contains(query) ||
                article.title.lowercase().contains(query) ||
                article.description.lowercase().contains(query) ||
                article.content.lowercase().contains(query)

            categoryMatches && queryMatches
        }
    }

    fun getArticleById(id: Int): Article? {
        val current = _uiState.value
        return if (current is UiState.Success) {
            current.data.find { it.id == id }
        } else {
            null
        }
    }
}
