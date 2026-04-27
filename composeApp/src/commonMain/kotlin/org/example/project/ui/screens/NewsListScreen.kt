@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.material.ExperimentalMaterialApi::class)

package org.example.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.components.ArticleCard
import org.example.project.components.CategoryChip
import org.example.project.components.ErrorView
import org.example.project.components.LoadingView
import org.example.project.data.Article
import org.example.project.data.UiState

@Composable
fun NewsListScreen(
    uiState: UiState<List<Article>>,
    visibleArticles: List<Article>,
    categories: List<String>,
    searchQuery: String,
    selectedCategory: String,
    isRefreshing: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onResetFilter: () -> Unit,
    onRetry: () -> Unit,
    onRefresh: () -> Unit,
    onArticleClick: (Int) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val contentMaxWidth = if (maxWidth > 760.dp) 760.dp else maxWidth

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            when (uiState) {
                is UiState.Loading -> LoadingView()
                is UiState.Error -> ErrorView(
                    message = uiState.message,
                    onRetry = onRetry
                )
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = contentMaxWidth)
                            ) {
                                TopAppBar(
                                    title = {
                                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                            Text(
                                                text = "Awi201News",
                                                style = MaterialTheme.typography.headlineMedium
                                            )
                                            Text(
                                                text = "Today's brief",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    },
                                    actions = {
                                        TextButton(onClick = onClearSearch) {
                                            Icon(Icons.Outlined.Search, contentDescription = "Clear Search")
                                        }
                                        TextButton(onClick = onResetFilter) {
                                            Icon(Icons.Outlined.FilterList, contentDescription = "Reset Filter")
                                        }
                                    }
                                )
                            }
                        }

                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = contentMaxWidth)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Latest stories for you",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                    Text(
                                        text = "Stay updated with fresh articles, browse by category, and pull down anytime to refresh.",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = onSearchQueryChange,
                                label = { Text("Search category or title") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = contentMaxWidth)
                            )
                        }

                        item {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = contentMaxWidth),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(categories) { category ->
                                    CategoryChip(
                                        label = category,
                                        isSelected = category == selectedCategory,
                                        onClick = { onCategorySelected(category) }
                                    )
                                }
                            }
                        }

                        item {
                            Text(
                                text = "${visibleArticles.size} article(s) found",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .widthIn(max = contentMaxWidth),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        if (visibleArticles.isEmpty()) {
                            item {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .widthIn(max = contentMaxWidth)
                                ) {
                                    Column(
                                        modifier = Modifier.padding(24.dp),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Text(
                                            text = "No articles match this filter",
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            text = "Try another category or search keyword.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        } else {
                            items(visibleArticles) { article ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .widthIn(max = contentMaxWidth)
                                ) {
                                    ArticleCard(
                                        article = article,
                                        onClick = { onArticleClick(article.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
