@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.components.EmptyState
import org.example.project.data.Article
import org.example.project.ui.components.ArticleCard
import org.example.project.viewmodel.NewsUiState

@Composable
fun NewsScreen(
    uiState: NewsUiState,
    onSearchQueryChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    onClearSearch: () -> Unit,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    onArticleClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    var showSearch by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 92.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TopAppBar(
                title = {
                    Column {
                        Text("News Reader")
                        Text(
                            text = "Live headlines from NewsAPI",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { showSearch = !showSearch }) { Text("Search") }
                    TextButton(onClick = onRefresh, enabled = !uiState.isLoading && !uiState.isRefreshing) {
                        Text(if (uiState.isRefreshing) "Refreshing" else "Refresh")
                    }
                }
            )
        }

        item {
            NewsHeroCard(
                totalArticles = uiState.totalArticles,
                lastUpdatedLabel = uiState.lastUpdatedLabel,
                isRefreshing = uiState.isRefreshing
            )
        }

        if (showSearch) {
            item {
                SearchNewsCard(
                    query = uiState.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSubmitSearch = onSubmitSearch,
                    onClearSearch = onClearSearch
                )
            }
        }

        when {
            uiState.isLoading && uiState.articles.isEmpty() -> {
                item { LoadingNewsState() }
            }

            uiState.errorMessage != null && uiState.articles.isEmpty() -> {
                item {
                    ErrorNewsState(
                        message = uiState.errorMessage,
                        onRetry = onRetry
                    )
                }
            }

            uiState.articles.isEmpty() -> {
                item {
                    EmptyState(
                        title = "Belum ada berita",
                        subtitle = "Coba refresh atau gunakan kata kunci pencarian lain."
                    )
                }
            }

            else -> {
                if (uiState.errorMessage != null) {
                    item {
                        InlineErrorBanner(
                            message = uiState.errorMessage,
                            onRetry = onRetry
                        )
                    }
                }

                items(uiState.articles, key = { it.id }) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article.id) },
                        onToggleFavorite = { onToggleFavorite(article.id) },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteArticlesScreen(
    articles: List<Article>,
    onArticleClick: (Int) -> Unit,
    onToggleFavorite: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 92.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TopAppBar(
                title = {
                    Column {
                        Text("Favorites")
                        Text(
                            text = "Saved news articles",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }

        item {
            FavoritesNewsHeroCard(count = articles.size)
        }

        if (articles.isEmpty()) {
            item {
                EmptyState(
                    title = "Belum ada berita favorit",
                    subtitle = "Tap ikon bintang pada artikel untuk menyimpannya di sini."
                )
            }
        } else {
            items(articles, key = { it.id }) { article ->
                ArticleCard(
                    article = article,
                    onClick = { onArticleClick(article.id) },
                    onToggleFavorite = { onToggleFavorite(article.id) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchNewsCard(
    query: String,
    onQueryChange: (String) -> Unit,
    onSubmitSearch: () -> Unit,
    onClearSearch: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                label = { Text("Cari headline") },
                placeholder = { Text("Contoh: technology, business, sports") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onSubmitSearch,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Search")
                }
                TextButton(onClick = onClearSearch) {
                    Text("Clear")
                }
            }
        }
    }
}

@Composable
private fun LoadingNewsState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        CircularProgressIndicator()
        Text(
            text = "Mengambil berita terbaru...",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ErrorNewsState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Gagal memuat berita",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun InlineErrorBanner(
    message: String,
    onRetry: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.errorContainer
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = message,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.onErrorContainer,
                style = MaterialTheme.typography.bodySmall
            )
            TextButton(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun NewsHeroCard(
    totalArticles: Int,
    lastUpdatedLabel: String,
    isRefreshing: Boolean
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF74D3FF), Color(0xFF5875FF))
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(26.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Latest headlines",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Total articles: $totalArticles • $lastUpdatedLabel",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.95f)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.20f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (isRefreshing) "↻" else "N", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun FavoritesNewsHeroCard(count: Int) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFC1D5), Color(0xFFFF8CB8))
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(26.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Favorites collection",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Saved articles: $count",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.95f)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.20f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "★", color = Color.White)
                }
            }
        }
    }
}
