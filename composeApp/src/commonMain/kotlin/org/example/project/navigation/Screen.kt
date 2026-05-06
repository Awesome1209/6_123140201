package org.example.project.navigation

sealed class Screen(val route: String) {
    data object Notes : Screen("notes")
    data object Favorites : Screen("favorites")
    data object Profile : Screen("profile")
    data object AddNote : Screen("add_note")

    data object ArticleDetail : Screen("article_detail/{articleId}") {
        fun createRoute(articleId: Int): String = "article_detail/$articleId"
    }

    data object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int): String = "note_detail/$noteId"
    }

    data object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Int): String = "edit_note/$noteId"
    }
}
