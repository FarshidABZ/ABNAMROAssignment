package com.abnamro.feature.repolist.ui

import androidx.lifecycle.viewModelScope
import com.abnamro.base.mvibase.BaseMviViewModel
import com.abnamro.core.base.model.Result
import com.abnamro.core.domain.usecase.GetReposUsecase
import com.abnamro.feature.repolist.model.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RepoListViewModel @Inject constructor(
    private val getReposUseCase: GetReposUsecase
) : BaseMviViewModel<RepoListIntent, RepoListViewState, RepoListSingleEvent>() {
    private val _viewState = MutableStateFlow(RepoListViewState())
    override val viewState = _viewState.asStateFlow()

    init {
        processIntent(RepoListIntent.LoadInitial)
    }

    override fun processIntent(intent: RepoListIntent) {
        when (intent) {
            RepoListIntent.LoadInitial -> loadData(1)
            RepoListIntent.LoadNextPage -> loadData(_viewState.value.currentPage + 1)
        }
    }

    private fun loadData(page: Int) {
        if (_viewState.value.isLoading) return
        if (_viewState.value.isLastPage) return

        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }
            val result = getReposUseCase(page, perPage = 10)

            result.collect { res ->
                when (res) {
                    is Result.Success -> {
                        _viewState.update { currentState ->
                            val newRepos = res.data.map { it.toUIModel() }
                            currentState.copy(
                                repos = currentState.repos + newRepos,
                                isLoading = false,
                                currentPage = page,
                                isLastPage = newRepos.isEmpty()
                            )
                        }
                    }

                    is Result.Error -> {
                        _viewState.update { it.copy(isLoading = false) }
                        sendSingleEvent(
                            RepoListSingleEvent.ShowError(
                                res.exception?.message ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }
}