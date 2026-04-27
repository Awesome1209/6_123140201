@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.example.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.Note

@Composable
fun NoteDetailScreen(
    note: Note?,
    onBack: () -> Unit,
    onToggleFavorite: (Int) -> Unit,
    onEdit: (Int) -> Unit
) {
    if (note == null) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
            Text(
                text = "Note not found",
                modifier = Modifier.padding(16.dp)
            )
        }
        return
    }

    var showMenu by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Note Detail") },
            navigationIcon = {
                TextButton(onClick = onBack) { Text("Back") }
            },
            actions = {
                TextButton(onClick = { onToggleFavorite(note.id) }) {
                    Text(if (note.isFavorite) "Unfavorite" else "Favorite")
                }
                TextButton(onClick = { onEdit(note.id) }) {
                    Text("Edit")
                }
                TextButton(onClick = { showMenu = true }) {
                    Text("More")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Close menu") },
                        onClick = { showMenu = false }
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(note.title)
                    Text("Category: ${note.category}")
                    Text(if (note.isFavorite) "Status: Favorite" else "Status: Normal")
                }
            }

            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(note.content)
                }
            }
        }
    }
}
