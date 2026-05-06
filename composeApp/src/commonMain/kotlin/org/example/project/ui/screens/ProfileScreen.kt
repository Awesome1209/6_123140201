package org.example.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.ui.components.EditBannerCard
import org.example.project.ui.components.ProfileEditorCard
import org.example.project.ui.components.ProfileHeaderCard
import org.example.project.ui.components.ProfileSettingsCard
import org.example.project.ui.components.ProfileTopBar
import org.example.project.viewmodel.ProfileUiState
import org.example.project.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }
) {
    val uiState by profileViewModel.uiState.collectAsState()

    ProfileScreenContent(
        uiState = uiState,
        onEditClick = profileViewModel::startEditing,
        onNameChange = profileViewModel::onEditNameChange,
        onBioChange = profileViewModel::onEditBioChange,
        onSaveClick = profileViewModel::saveProfile,
        onCancelClick = profileViewModel::cancelEditing,
        onDarkModeToggle = profileViewModel::setDarkMode
    )
}

@Composable
fun ProfileScreenContent(
    uiState: ProfileUiState,
    onEditClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDarkModeToggle: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()

    Surface(modifier = Modifier.fillMaxSize()) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isWideLayout = maxWidth >= 860.dp
            val outerPadding = if (maxWidth >= 720.dp) 28.dp else 16.dp
            val spacing = if (isWideLayout) 20.dp else 16.dp
            val columnWidth = (maxWidth - spacing) / 2

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(outerPadding),
                verticalArrangement = Arrangement.spacedBy(spacing)
            ) {
                ProfileTopBar(onEditClick = onEditClick)

                if (isWideLayout) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(spacing)
                    ) {
                        Column(
                            modifier = Modifier.width(columnWidth),
                            verticalArrangement = Arrangement.spacedBy(spacing)
                        ) {
                            ProfileHeaderCard(profile = uiState.profile)
                            EditBannerCard(
                                isEditing = uiState.isEditing,
                                onEditClick = onEditClick
                            )
                        }

                        Column(
                            modifier = Modifier.width(columnWidth),
                            verticalArrangement = Arrangement.spacedBy(spacing)
                        ) {
                            ProfileSettingsCard(
                                profile = uiState.profile,
                                isDarkMode = uiState.isDarkMode,
                                onDarkModeToggle = onDarkModeToggle
                            )
                            if (uiState.isEditing) {
                                ProfileEditorCard(
                                    nameValue = uiState.editName,
                                    bioValue = uiState.editBio,
                                    onNameChange = onNameChange,
                                    onBioChange = onBioChange,
                                    onSaveClick = onSaveClick,
                                    onCancelClick = onCancelClick,
                                    statusMessage = uiState.statusMessage
                                )
                            }
                        }
                    }
                } else {
                    ProfileHeaderCard(profile = uiState.profile)
                    EditBannerCard(
                        isEditing = uiState.isEditing,
                        onEditClick = onEditClick
                    )
                    ProfileSettingsCard(
                        profile = uiState.profile,
                        isDarkMode = uiState.isDarkMode,
                        onDarkModeToggle = onDarkModeToggle
                    )
                    if (uiState.isEditing) {
                        ProfileEditorCard(
                            nameValue = uiState.editName,
                            bioValue = uiState.editBio,
                            onNameChange = onNameChange,
                            onBioChange = onBioChange,
                            onSaveClick = onSaveClick,
                            onCancelClick = onCancelClick,
                            statusMessage = uiState.statusMessage
                        )
                    }
                }
            }
        }
    }
}

