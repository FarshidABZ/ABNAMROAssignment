package com.abnamro.feature.repolist.ui

import androidx.compose.runtime.Stable
import com.abnamro.base.mvibase.MviIntent
import com.abnamro.base.mvibase.MviSingleEvent
import com.abnamro.base.mvibase.MviViewState
import com.abnamro.feature.repolist.model.RepoUiModel


internal sealed interface RepoListSingleEvent : MviSingleEvent {
    data class ShowError(val message: String) : RepoListSingleEvent
    data object NoEvent : RepoListSingleEvent
}

@Stable
internal sealed interface RepoListIntent : MviIntent {
    data object LoadData : RepoListIntent
}

@Stable
data class RepoListViewState(
    val repos: List<RepoUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val isLastPage: Boolean = false
) : MviViewState
