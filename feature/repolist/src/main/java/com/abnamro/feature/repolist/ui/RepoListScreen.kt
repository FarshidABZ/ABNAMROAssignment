package com.abnamro.feature.repolist.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abnamro.core.designsystem.component.PaginatedLazyColumn
import com.abnamro.core.designsystem.component.RepoAvatar
import com.abnamro.core.designsystem.component.TopBar
import com.abnamro.core.designsystem.component.VisibilityChip
import com.abnamro.core.designsystem.theme.ABNAmroAssignmentTheme
import com.abnamro.core.domain.model.VisibilityState
import com.abnamro.feature.repolist.R
import com.abnamro.feature.repolist.model.RepoUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


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
        PaginatedLazyColumn(
            items = viewState.repos,
            loadMoreItems = { onAction(RepoListIntent.LoadData) },
            listState = listState,
            isLoading = viewState.isLoading,
            itemContent = { repo ->
                RepoItem(repo = repo, onRepoClicked = onRepoClicked)
            }
        )
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
        PaginatedLazyColumn(
            items = repoList,
            isLoading = false,
            listState = rememberLazyListState(),
            loadMoreItems = {},
            itemContent = { repo ->
                RepoItem(repo = repo, onRepoClicked = { })
            }
        )
    }
}


@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun RepoListDarkModePreview() {
    RepoListPreview()
}