package com.abnamro.feature.repodetail.ui

import androidx.compose.runtime.Stable
import com.abnamro.base.mvibase.MviIntent
import com.abnamro.base.mvibase.MviSingleEvent
import com.abnamro.base.mvibase.MviViewState
import com.abnamro.feature.repodetail.model.RepoDetailUiModel


internal sealed interface RepoDetailSingleEvent : MviSingleEvent {
    data class ShowError(val message: String) : RepoDetailSingleEvent
    data object NoEvent : RepoDetailSingleEvent
}

@Stable
internal sealed interface RepoDetailIntent : MviIntent {
    data object LoadData : RepoDetailIntent
}

@Stable
data class RepoDetailViewState(
    val repoId: Long = 0,
    val repo: RepoDetailUiModel? = null,
) : MviViewState
