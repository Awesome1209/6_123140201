@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import org.example.project.components.NoteCard
import org.example.project.data.Note

@Composable
fun NotesScreen(
    notes: List<Note>,
    searchQuery: String,
    currentFilterLabel: String,
    totalNotes: Int,
    onSearchQueryChange: (String) -> Unit,
    onCycleSort: () -> Unit,
    onShowAll: () -> Unit,
    onShowOnlyFavorites: () -> Unit,
    onNoteClick: (Int) -> Unit
) {
    var showSearch by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 92.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            TopAppBar(
                title = {
                    Column {
                        Text("Notes")
                        Text(
                            text = "Capture your ideas",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { showSearch = !showSearch }) { Text("Search") }
                    TextButton(onClick = onCycleSort) { Text("Sort") }
                    TextButton(onClick = { showMenu = true }) { Text("More") }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Show All") },
                            onClick = {
                                onShowAll()
                                showMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Only Favorites") },
                            onClick = {
                                onShowOnlyFavorites()
                                showMenu = false
                            }
                        )
                    }
                }
            )
        }

        item {
            NotesHeroCard(
                totalNotes = totalNotes,
                currentFilterLabel = currentFilterLabel
            )
        }

        if (showSearch) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    label = { Text("Search notes") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        if (notes.isEmpty()) {
            item {
                EmptyState(
                    title = "No notes found",
                    subtitle = "Tap + to add your first note."
                )
            }
        } else {
            items(notes) { note ->
                NoteCard(
                    note = note,
                    onClick = { onNoteClick(note.id) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun NotesHeroCard(
    totalNotes: Int,
    currentFilterLabel: String
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF74D3FF), Color(0xFF5875FF))
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(26.dp),
        tonalElevation = 0.dp,
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Capture your best ideas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Total notes: $totalNotes • Filter: $currentFilterLabel",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.95f)
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.20f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✦", color = Color.White)
                }
            }
        }
    }
}
