package com.nagyrobi144.dogify.android.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagyrobi144.dogify.repository.BreedsRepository
import com.nagyrobi144.dogify.repository.model.Breed
import com.nagyrobi144.dogify.usecase.FetchBreedsUseCase
import com.nagyrobi144.dogify.usecase.GetBreedsUseCase
import com.nagyrobi144.dogify.usecase.ToggleFavouriteStateUseCase
import com.nagyrobi144.dogify.util.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    breedsRepository: BreedsRepository,
    private val getBreeds: GetBreedsUseCase,
    private val fetchBreeds: FetchBreedsUseCase,
    private val onToggleFavouriteState: ToggleFavouriteStateUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events

    val breeds = breedsRepository.breeds.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    init {
        loadData()
    }

    private fun loadData(isForceRefresh: Boolean = false) {
        val getData: suspend () -> Result<List<Breed>> =
            { if (isForceRefresh) fetchBreeds.invoke() else getBreeds.invoke() }

        if (isForceRefresh) {
            _isRefreshing.value = true
        } else {
            _state.value = State.LOADING
        }

        viewModelScope.launch {
            _state.value = when (getData()) {
                is Result.Success -> State.NORMAL
                is Result.Partial -> {
                    _events.emit(Event.Error)
                    State.NORMAL
                }
                is Result.Error -> State.ERROR
            }
            _isRefreshing.value = false
        }
    }

    fun refresh() {
        loadData(true)
    }

    fun onFavouriteTapped(breed: Breed) {
        viewModelScope.launch {
            when (onToggleFavouriteState(breed)) {
                is Result.Success -> {
                    // Just ignore
                }
                else -> {
                    _events.emit(Event.Error)
                }
            }
        }
    }

    enum class State {
        LOADING,
        NORMAL,
        ERROR,
        EMPTY
    }

    enum class Event {
        Error
    }
}