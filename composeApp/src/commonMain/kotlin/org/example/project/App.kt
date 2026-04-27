package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.navigation.AppNavigation
import org.example.project.ui.theme.ProfileAppTheme
import org.example.project.viewmodel.NewsViewModel
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun App() {
    val newsViewModel: NewsViewModel = viewModel { NewsViewModel() }
    val profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }
    val profileUiState by profileViewModel.uiState.collectAsState()

    ProfileAppTheme(darkTheme = profileUiState.isDarkMode) {
        AppNavigation(
            newsViewModel = newsViewModel,
            profileViewModel = profileViewModel
        )
    }
}
