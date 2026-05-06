package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.navigation.AppNavigation
import org.example.project.ui.theme.ProfileAppTheme
import org.example.project.viewmodel.NewsViewModel
import org.example.project.viewmodel.NotesViewModel
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun App() {
    val notesViewModel: NotesViewModel = viewModel { NotesViewModel() }
    val newsViewModel: NewsViewModel = viewModel { NewsViewModel() }
    val profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }
    val profileUiState by profileViewModel.uiState.collectAsState()

    ProfileAppTheme(darkTheme = profileUiState.isDarkMode) {
        AppNavigation(
            notesViewModel = notesViewModel,
            newsViewModel = newsViewModel,
            profileViewModel = profileViewModel
        )
    }
}
