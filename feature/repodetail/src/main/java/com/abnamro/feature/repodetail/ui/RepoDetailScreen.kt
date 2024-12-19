package com.abnamro.feature.repodetail.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.abnamro.core.designsystem.component.RepoAvatar
import com.abnamro.core.designsystem.component.TopBar
import com.abnamro.core.designsystem.component.VisibilityChip
import com.abnamro.core.designsystem.component.WebButton
import com.abnamro.core.designsystem.theme.ABNAmroAssignmentTheme
import com.abnamro.core.domain.model.VisibilityState
import com.abnamro.feature.repodetail.R
import com.abnamro.feature.repodetail.model.RepoDetailUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.abnamro.base.R as BaseR


@Composable
internal fun RepoDetailRoute(
    onBackClicked: () -> Unit,
    viewModel: RepoDetailViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    val singleEvent by viewModel.singleEvent.collectAsStateWithLifecycle(RepoDetailSingleEvent.NoEvent)

    RepoDetailScreen(
        viewState = viewState,
        singleEvent = singleEvent,
        onAction = viewModel::processIntent,
        onBackClicked = onBackClicked
    )
}

@Composable
private fun RepoDetailScreen(
    viewState: RepoDetailViewState,
    singleEvent: RepoDetailSingleEvent,
    onAction: (RepoDetailIntent) -> Unit,
    onBackClicked: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    HandleSingleEvent(singleEvent, snackbarHostState, coroutineScope)

    ABNAmroAssignmentTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopBar(
                    viewState.repo?.name ?: stringResource(R.string.repo_details),
                    onNavigationClicked = onBackClicked
                )
            }
        ) { paddingValues ->
            RepositoryDetailContent(
                paddingValues = paddingValues,
                viewState = viewState,
            )
        }
    }
}

@Composable
private fun HandleSingleEvent(
    singleEvent: RepoDetailSingleEvent,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {
    LaunchedEffect(singleEvent) {
        if (singleEvent is RepoDetailSingleEvent.ShowError) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(singleEvent.message)
            }
        }
    }
}

@Composable
private fun RepositoryDetailContent(
    paddingValues: PaddingValues,
    viewState: RepoDetailViewState,
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        viewState.repo?.let {
            RepoDetail(repo = viewState.repo)
        }
    }
}

@Composable
private fun RepoDetail(
    repo: RepoDetailUiModel,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        val (avatar, title, ownerName, description, visibilityChip, privateChip, extLinkRef) = createRefs()

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
                    id = BaseR.string.visibility,
                    stringResource(BaseR.string._public_)
                )
            else stringResource(
                id = BaseR.string.visibility,
                stringResource(BaseR.string._private_)
            ),
            modifier = Modifier.constrainAs(visibilityChip) {
                top.linkTo(description.bottom, margin = 8.dp)
                start.linkTo(parent.start)
            }
        )

        VisibilityChip(
            text = if (repo.isPrivate) stringResource(
                id = BaseR.string.isPrivate,
                stringResource(BaseR.string.yes)
            )
            else stringResource(
                id = BaseR.string.isPrivate,
                stringResource(BaseR.string.no)
            ),
            modifier = Modifier.constrainAs(privateChip) {
                top.linkTo(visibilityChip.top)
                start.linkTo(visibilityChip.end, margin = 8.dp)
            }
        )

        WebButton(
            modifier = Modifier.constrainAs(extLinkRef) {
                top.linkTo(visibilityChip.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            externalURL = repo.htmlUrl.orEmpty(),
            text = stringResource(id = R.string.view_on_github)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RepoDetailPreview() {
    val repo = RepoDetailUiModel(
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

    ABNAmroAssignmentTheme {
        RepoDetail(repo)
    }
}


@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun RepoDetailDarkModePreview() {
    RepoDetailPreview()
}