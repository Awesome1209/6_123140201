package org.example.project.viewmodel

import org.example.project.data.Profile

data class ProfileUiState(
    val profile: Profile = Profile(),
    val editName: String = profile.name,
    val editBio: String = profile.bio,
    val isEditing: Boolean = false,
    val isDarkMode: Boolean = false,
    val statusMessage: String? = null
)
