package org.example.project.navigation

sealed class Screen(val route: String) {
    data object News : Screen("news")
    data object Profile : Screen("profile")
    data object ArticleDetail : Screen("article_detail/{articleId}") {
        fun createRoute(articleId: Int): String = "article_detail/$articleId"
    }
}
