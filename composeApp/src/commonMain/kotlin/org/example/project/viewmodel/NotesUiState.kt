package org.example.project.viewmodel

import org.example.project.data.Note
import org.example.project.data.Profile

enum class SortMode {
    NEWEST, OLDEST, TITLE
}

enum class NotesFilter {
    ALL, ONLY_FAVORITES
}

data class NotesUiState(
    val notes: List<Note> = sampleNotes(),
    val searchQuery: String = "",
    val sortMode: SortMode = SortMode.NEWEST,
    val notesFilter: NotesFilter = NotesFilter.ALL,
    val isDarkMode: Boolean = false,
    val profile: Profile = Profile()
)

private fun sampleNotes(): List<Note> = listOf(
    Note(
        id = 1,
        title = "Belajar Compose",
        content = "Pahami composable, state, dan layout dasar untuk tugas mobile.",
        isFavorite = true,
        category = "Kuliah"
    ),
    Note(
        id = 2,
        title = "Checklist Tugas 5",
        content = "Bottom nav, detail note, add note, edit note, dan back navigation harus jalan.",
        isFavorite = false,
        category = "Tugas"
    ),
    Note(
        id = 3,
        title = "Ide Aplikasi",
        content = "Buat notes app dengan UI clean, modern, dan responsive.",
        isFavorite = true,
        category = "Ide"
    )
)
