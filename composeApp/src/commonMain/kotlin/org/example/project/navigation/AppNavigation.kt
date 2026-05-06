package org.example.project.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.ui.screens.AddNoteScreen
import org.example.project.ui.screens.ArticleDetailScreen
import org.example.project.ui.screens.EditNoteScreen
import org.example.project.ui.screens.FavoriteArticlesScreen
import org.example.project.ui.screens.NewsScreen
import org.example.project.ui.screens.NoteDetailScreen
import org.example.project.ui.screens.ProfileScreen
import org.example.project.viewmodel.NewsViewModel
import org.example.project.viewmodel.NotesViewModel
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(
    notesViewModel: NotesViewModel,
    newsViewModel: NewsViewModel,
    profileViewModel: ProfileViewModel
) {
    val navController = rememberNavController()
    val newsUiState by newsViewModel.uiState.collectAsState()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute == Screen.Notes.route ||
        currentRoute == Screen.Favorites.route ||
        currentRoute == Screen.Profile.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Notes.route) {
                NewsScreen(
                    uiState = newsUiState,
                    onSearchQueryChange = newsViewModel::onSearchQueryChange,
                    onSubmitSearch = newsViewModel::submitSearch,
                    onClearSearch = newsViewModel::clearSearch,
                    onRefresh = newsViewModel::refresh,
                    onRetry = newsViewModel::retry,
                    onArticleClick = { articleId ->
                        navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                    },
                    onToggleFavorite = newsViewModel::toggleFavorite
                )
            }

            composable(Screen.Favorites.route) {
                FavoriteArticlesScreen(
                    articles = newsUiState.favoriteArticles,
                    onArticleClick = { articleId ->
                        navController.navigate(Screen.ArticleDetail.createRoute(articleId))
                    },
                    onToggleFavorite = newsViewModel::toggleFavorite
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
                val article = newsUiState.articles.firstOrNull { it.id == articleId }
                ArticleDetailScreen(
                    article = article,
                    onBack = { navController.popBackStack() },
                    onToggleFavorite = newsViewModel::toggleFavorite
                )
            }

            // Legacy Notes routes are intentionally preserved for the next tasks.
            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    onBack = { navController.popBackStack() },
                    onSave = { title, content, category ->
                        notesViewModel.addNote(title, content, category)
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.NoteDetail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStack ->
                val noteId = backStack.arguments?.getInt("noteId") ?: -1
                val note = notesViewModel.getNoteById(noteId)
                NoteDetailScreen(
                    note = note,
                    onBack = { navController.popBackStack() },
                    onToggleFavorite = { id -> notesViewModel.toggleFavorite(id) },
                    onEdit = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
                )
            }

            composable(
                route = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStack ->
                val noteId = backStack.arguments?.getInt("noteId") ?: -1
                val note = notesViewModel.getNoteById(noteId)
                EditNoteScreen(
                    note = note,
                    onBack = { navController.popBackStack() },
                    onSave = { title, content, category ->
                        notesViewModel.updateNote(noteId, title, content, category)
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
