package org.example.project.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.ui.screens.ArticleDetailScreen
import org.example.project.ui.screens.NewsListScreen
import org.example.project.ui.screens.ProfileScreen
import org.example.project.viewmodel.NewsViewModel
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(
    newsViewModel: NewsViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    val uiState by newsViewModel.uiState.collectAsState()
    val filterState by newsViewModel.filterState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute == Screen.News.route || currentRoute == Screen.Profile.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.News.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.News.route) {
                NewsListScreen(
                    uiState = uiState,
                    visibleArticles = newsViewModel.getFilteredArticles(),
                    categories = newsViewModel.categories,
                    searchQuery = filterState.searchQuery,
                    selectedCategory = filterState.selectedCategory,
                    isRefreshing = filterState.isRefreshing,
                    onSearchQueryChange = newsViewModel::onSearchQueryChange,
                    onClearSearch = { newsViewModel.onSearchQueryChange("") },
                    onCategorySelected = newsViewModel::onCategorySelected,
                    onResetFilter = { newsViewModel.onCategorySelected("All") },
                    onRetry = newsViewModel::loadArticles,
                    onRefresh = newsViewModel::refresh,
                    onArticleClick = { articleId ->
                        navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(profileViewModel = profileViewModel)
            }

            composable(
                route = Screen.ArticleDetail.route,
                arguments = listOf(navArgument("articleId") { type = NavType.IntType })
            ) { backStack ->
                val articleId = backStack.arguments?.getInt("articleId") ?: -1
                ArticleDetailScreen(
                    article = newsViewModel.getArticleById(articleId),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
