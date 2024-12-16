package com.abnamro.feature.repolist.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abnamro.core.designsystem.component.RepoAvatar
import com.abnamro.core.designsystem.component.TopBar
import com.abnamro.core.designsystem.component.VisibilityChip
import com.abnamro.core.designsystem.theme.ABNAmroAssignmentTheme
import com.abnamro.core.domain.model.VisibilityState
import com.abnamro.feature.repolist.R
import com.abnamro.feature.repolist.model.RepoUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


private const val PAGINATION_THRESHOLD = 8

@Composable
internal fun RepoListRoute(
    onRepoClicked: (Long) -> Unit,
    viewModel: RepoListViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val singleEvent by viewModel.singleEvent.collectAsStateWithLifecycle(RepoListSingleEvent.NoEvent)
    val listState = rememberLazyListState()

    RepoListScreen(
        viewState = viewState,
        singleEvent = singleEvent,
        listState = listState,
        onAction = viewModel::processIntent,
        onRepoClicked = onRepoClicked
    )
}

@Composable
private fun RepoListScreen(
    viewState: RepoListViewState,
    singleEvent: RepoListSingleEvent,
    listState: LazyListState,
    onAction: (RepoListIntent) -> Unit,
    onRepoClicked: (Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    HandleSingleEvent(singleEvent, snackbarHostState, coroutineScope)

    ABNAmroAssignmentTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = { TopBar(stringResource(R.string.repo_list)) }
        ) { paddingValues ->
            RepositoryListContent(
                paddingValues = paddingValues,
                viewState = viewState,
                listState = listState,
                onAction = onAction,
                onRepoClicked = onRepoClicked
            )
        }
    }
}

@Composable
private fun HandleSingleEvent(
    singleEvent: RepoListSingleEvent,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    LaunchedEffect(singleEvent) {
        if (singleEvent is RepoListSingleEvent.ShowError) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(singleEvent.message)
            }
        }
    }
}

@Composable
private fun RepositoryListContent(
    paddingValues: PaddingValues,
    viewState: RepoListViewState,
    listState: LazyListState,
    onAction: (RepoListIntent) -> Unit,
    onRepoClicked: (Long) -> Unit
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        RepoList(
            repos = viewState.repos,
            isLoading = viewState.isLoading,
            listState = listState,
            onRepoClicked = onRepoClicked
        )

        HandlePagination(
            listState = listState,
            isLoading = viewState.isLoading,
            onLoadNextPage = { onAction(RepoListIntent.LoadNextPage) }
        )
    }
}

@Composable
private fun HandlePagination(
    listState: LazyListState,
    isLoading: Boolean,
    onLoadNextPage: () -> Unit
) {
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect {
                val itemCount = listState.layoutInfo.totalItemsCount
                if (itemCount > PAGINATION_THRESHOLD &&
                    it >= itemCount - PAGINATION_THRESHOLD &&
                    !isLoading
                ) {
                    onLoadNextPage()
                }
            }
    }
}

@Composable
private fun RepoList(
    repos: List<RepoUiModel>,
    isLoading: Boolean,
    listState: LazyListState,
    onRepoClicked: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(repos) { repo ->
            RepoItem(repo = repo, onRepoClicked = onRepoClicked)
        }
        if (isLoading) {
            item { CircularProgressIndicator() }
        }
    }
}

@Composable
private fun RepoItem(
    repo: RepoUiModel,
    onRepoClicked: (Long) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onRepoClicked(repo.id) }
            .padding(16.dp)
    ) {
        val (avatar, title, ownerName, description, visibilityChip, privateChip) = createRefs()

        RepoAvatar(
            imageUrl = repo.ownerAvatarUrl,
            modifier = Modifier.constrainAs(avatar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
        )

        RepoTitle(
            title = repo.name,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top)
                start.linkTo(avatar.end, margin = 16.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        RepoOwnerName(
            ownerName = repo.ownerName,
            modifier = Modifier.constrainAs(ownerName) {
                top.linkTo(title.bottom, margin = 2.dp)
                start.linkTo(avatar.end, margin = 16.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        RepoDescription(
            description = repo.description,
            modifier = Modifier.constrainAs(description) {
                top.linkTo(avatar.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        VisibilityChip(
            text = if (repo.visibility == VisibilityState.PUBLIC)
                stringResource(
                    id = R.string.visibility,
                    stringResource(R.string._public_)
                )
            else stringResource(
                id = R.string.visibility,
                stringResource(R.string._private_)
            ),
            modifier = Modifier.constrainAs(visibilityChip) {
                top.linkTo(description.bottom, margin = 8.dp)
                start.linkTo(parent.start)
            }
        )

        VisibilityChip(
            text = if (repo.isPrivate) stringResource(
                id = R.string.isPrivate,
                stringResource(R.string.yes)
            )
            else stringResource(
                id = R.string.isPrivate,
                stringResource(R.string.no)
            ),
            modifier = Modifier.constrainAs(privateChip) {
                top.linkTo(visibilityChip.top)
                start.linkTo(visibilityChip.end, margin = 8.dp)
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RepoListPreview() {
    val repo = RepoUiModel(
        1,
        "Repo Name",
        "Owner",
        "Description",
        "avatarUrl",
        "Owner Name",
        VisibilityState.PUBLIC,
        false,
        "htmlUrl"
    )

    val repoList = List(10) { repo }

    ABNAmroAssignmentTheme {
        RepoList(repoList, false, rememberLazyListState()) { }
    }
}


@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun RepoListDarkModePreview() {
    RepoListPreview()
}