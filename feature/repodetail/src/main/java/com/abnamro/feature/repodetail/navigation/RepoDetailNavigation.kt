package com.abnamro.feature.repodetail.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abnamro.feature.repodetail.ui.RepoDetailRoute

private const val REPO_DETAIL_ROUTE = "repo_detail_route/{id}"

private fun createRoute(id: Long) = "repo_detail_route/$id"

fun NavController.navigateToRepoDetail(id: Long) {
    navigate(createRoute(id))
}

fun NavGraphBuilder.repoDetailScreen(onBackClicked: () -> Unit) {
    composable(
        REPO_DETAIL_ROUTE,
        arguments = listOf(navArgument("id") { type = NavType.LongType })
    ) {
        RepoDetailRoute(onBackClicked = onBackClicked)
    }
}