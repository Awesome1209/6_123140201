@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package org.example.project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.example.project.data.Article
import org.example.project.generated.resources.Res
import org.example.project.generated.resources.news_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun ArticleDetailScreen(
    article: Article?,
    onBack: () -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val contentMaxWidth = if (maxWidth > 780.dp) 780.dp else maxWidth

        if (article == null) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    title = { Text("Article Detail") },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                        }
                    }
                )

                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Article not found",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = "Please return to the news list and try again.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            return@BoxWithConstraints
        }

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Article Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.news_placeholder),
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = contentMaxWidth)
                        .height(250.dp)
                        .clip(RoundedCornerShape(28.dp)),
                    contentScale = ContentScale.Crop
                )

                AssistChip(
                    onClick = {},
                    label = { Text(article.category) }
                )

                Text(
                    text = article.title,
                    modifier = Modifier.widthIn(max = contentMaxWidth),
                    style = MaterialTheme.typography.headlineMedium
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = contentMaxWidth)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Article #${article.id}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = article.content,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                        )
                    }
                }
            }
        }
    }
}
