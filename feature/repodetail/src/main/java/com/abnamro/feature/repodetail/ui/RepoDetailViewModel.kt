package com.abnamro.feature.repodetail.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.abnamro.base.mvibase.BaseMviViewModel
import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.usecase.GetRepoDetailsUsecase
import com.abnamro.feature.repodetail.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RepoDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRepoDetailsUsecase: GetRepoDetailsUsecase
) : BaseMviViewModel<RepoDetailIntent, RepoDetailViewState, RepoDetailSingleEvent>() {
    private val _viewState = MutableStateFlow(RepoDetailViewState(repoId = savedStateHandle["id"] ?: 0L))
    override val viewState = _viewState.asStateFlow()

    init {
        processIntent(RepoDetailIntent.LoadData)
    }

    override fun processIntent(intent: RepoDetailIntent) {
        when (intent) {
            RepoDetailIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            when (val result = getRepoDetailsUsecase(_viewState.value.repoId)) {
                is Result.Success -> {
                    _viewState.value = RepoDetailViewState(repo = result.data.toUIModel())
                }

                is Result.Error -> {
                    sendSingleEvent(
                        RepoDetailSingleEvent.ShowError(
                            result.exception?.message ?: "Unknown error"
                        )
                    )
                }
            }
        }
    }
}