package org.example.project.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.Note

class NotesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun cycleSortMode() {
        _uiState.update { current ->
            val next = when (current.sortMode) {
                SortMode.NEWEST -> SortMode.OLDEST
                SortMode.OLDEST -> SortMode.TITLE
                SortMode.TITLE -> SortMode.NEWEST
            }
            current.copy(sortMode = next)
        }
    }

    fun setFilter(filter: NotesFilter) {
        _uiState.update { it.copy(notesFilter = filter) }
    }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }

    fun addNote(title: String, content: String, category: String) {
        if (title.isBlank() || content.isBlank()) return

        _uiState.update { current ->
            val nextId = (current.notes.maxOfOrNull { it.id } ?: 0) + 1
            val newNote = Note(
                id = nextId,
                title = title.trim(),
                content = content.trim(),
                category = category.ifBlank { "General" }
            )
            current.copy(notes = listOf(newNote) + current.notes)
        }
    }

    fun updateNote(id: Int, title: String, content: String, category: String) {
        if (title.isBlank() || content.isBlank()) return

        _uiState.update { current ->
            current.copy(
                notes = current.notes.map { note ->
                    if (note.id == id) {
                        note.copy(
                            title = title.trim(),
                            content = content.trim(),
                            category = category.ifBlank { "General" }
                        )
                    } else {
                        note
                    }
                }
            )
        }
    }

    fun toggleFavorite(id: Int) {
        _uiState.update { current ->
            current.copy(
                notes = current.notes.map { note ->
                    if (note.id == id) {
                        note.copy(isFavorite = !note.isFavorite)
                    } else {
                        note
                    }
                }
            )
        }
    }

    fun getNoteById(id: Int): Note? {
        return _uiState.value.notes.find { it.id == id }
    }

    fun getVisibleNotes(): List<Note> {
        val state = _uiState.value
        return buildNotes(
            notes = state.notes,
            query = state.searchQuery,
            sortMode = state.sortMode,
            filter = state.notesFilter
        )
    }

    fun getFavoriteNotes(): List<Note> {
        val state = _uiState.value
        return buildNotes(
            notes = state.notes.filter { it.isFavorite },
            query = state.searchQuery,
            sortMode = state.sortMode,
            filter = NotesFilter.ALL
        )
    }

    private fun buildNotes(
        notes: List<Note>,
        query: String,
        sortMode: SortMode,
        filter: NotesFilter
    ): List<Note> {
        var result = notes

        if (filter == NotesFilter.ONLY_FAVORITES) {
            result = result.filter { it.isFavorite }
        }

        if (query.isNotBlank()) {
            val q = query.trim().lowercase()
            result = result.filter {
                it.title.lowercase().contains(q) ||
                    it.content.lowercase().contains(q) ||
                    it.category.lowercase().contains(q)
            }
        }

        result = when (sortMode) {
            SortMode.NEWEST -> result.sortedByDescending { it.id }
            SortMode.OLDEST -> result.sortedBy { it.id }
            SortMode.TITLE -> result.sortedBy { it.title.lowercase() }
        }

        return result
    }
}
