package com.nagyrobi144.dogify.android.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagyrobi144.dogify.repository.BreedsRepository
import com.nagyrobi144.dogify.usecase.FetchBreedsUseCase
import com.nagyrobi144.dogify.util.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    breedsRepository: BreedsRepository,
    private val fetchBreeds: FetchBreedsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    val breeds = breedsRepository.breeds.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    init {
        viewModelScope.launch {
            _state.value = when (val result = fetchBreeds()) {
                is Result.Success -> State.NORMAL
                is Result.Partial -> {
                    State.NORMAL
                }
                is Result.Error -> State.ERROR
            }
        }
    }

    enum class State {
        LOADING,
        NORMAL,
        ERROR,
        EMPTY
    }
}