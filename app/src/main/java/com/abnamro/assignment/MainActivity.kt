package com.abnamro.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abnamro.core.designsystem.theme.ABNAmroAssignmentTheme
import com.abnamro.feature.repolist.navigation.REPO_LIST_ROUTE
import com.abnamro.feature.repolist.navigation.repoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ABNAmroAssignmentTheme {
                val navController = rememberNavController()
                ABNAmroApp(navController)
            }
        }
    }
}

@Composable
fun ABNAmroApp(
    navController: NavHostController,
    startDestination: String = REPO_LIST_ROUTE,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        repoListScreen(onRepoClicked = { })
    }
}