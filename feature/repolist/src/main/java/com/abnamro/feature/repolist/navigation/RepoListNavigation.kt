package com.abnamro.feature.repolist.navigation


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abnamro.feature.repolist.ui.RepoListRoute

const val REPO_LIST_ROUTE = "repo_list_route"

fun NavController.navigateToRepoList() {
    navigate(REPO_LIST_ROUTE)
}

fun NavGraphBuilder.repoListScreen(onRepoClicked: (Long) -> Unit) {
    composable(REPO_LIST_ROUTE) {
        RepoListRoute(onRepoClicked = onRepoClicked)
    }
}