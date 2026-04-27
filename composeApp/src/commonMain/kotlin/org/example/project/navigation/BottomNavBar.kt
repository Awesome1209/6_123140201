package org.example.project.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

private data class BottomItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
)

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomItem(
            route = Screen.News.route,
            label = "News",
            icon = { Icon(Icons.Outlined.Home, contentDescription = "News") }
        ),
        BottomItem(
            route = Screen.Profile.route,
            label = "Profile",
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Profile") }
        )
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface {
        NavigationBar(
            modifier = Modifier.navigationBarsPadding()
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = item.icon,
                    label = { Text(item.label) },
                    colors = NavigationBarItemDefaults.colors()
                )
            }
        }
    }
}
