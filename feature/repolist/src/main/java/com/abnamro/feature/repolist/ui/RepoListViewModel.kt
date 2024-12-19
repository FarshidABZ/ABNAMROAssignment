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
        processIntent(RepoListIntent.LoadData)
    }

    override fun processIntent(intent: RepoListIntent) {
        when (intent) {
            RepoListIntent.LoadData -> loadData()
        }
    }

    private fun loadData() {
        if (_viewState.value.isLoading || _viewState.value.isLastPage) return

        viewModelScope.launch {
            _viewState.update { it.copy(isLoading = true) }

            getReposUseCase(perPage = 10).collect { result ->
                when (result) {
                    is Result.Success -> {
                        if(result.isLastPage == true || result.data.isEmpty()) {
                            _viewState.update { it.copy(isLastPage = true, isLoading = false) }
                        } else {
                            _viewState.update { currentState ->
                                currentState.copy(
                                    repos = result.data.map { it.toUIModel() },
                                    isLoading = false,
                                    isLastPage = false
                                )
                            }
                        }
                    }

                    is Result.Error -> {
                        _viewState.update { it.copy(isLoading = false) }
                        sendSingleEvent(
                            RepoListSingleEvent.ShowError(
                                result.exception?.message ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }
}