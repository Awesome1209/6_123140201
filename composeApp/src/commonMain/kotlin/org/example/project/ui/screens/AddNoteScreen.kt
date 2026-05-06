@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.example.project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.example.project.components.NoteForm

@Composable
fun AddNoteScreen(
    onBack: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("General") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Add Note") },
            navigationIcon = {
                TextButton(onClick = onBack) { Text("Back") }
            },
            actions = {
                TextButton(
                    onClick = { onSave(title, content, category) }
                ) {
                    Text("Save")
                }
            }
        )

        NoteForm(
            title = title,
            content = content,
            category = category,
            onTitleChange = { title = it },
            onContentChange = { content = it },
            onCategoryChange = { category = it },
            onSave = { onSave(title, content, category) },
            saveLabel = "Save Note"
        )
    }
}
