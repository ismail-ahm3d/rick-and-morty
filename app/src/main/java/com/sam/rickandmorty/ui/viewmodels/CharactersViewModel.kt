package com.sam.rickandmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.util.DispatcherProvider
import com.sam.data.util.Resource
import com.sam.domain.Character
import com.sam.rickandmorty.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: DefaultMainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private lateinit var response: Resource<List<Character>>

    private val _characters = MutableStateFlow<Event>(Event.Empty)
    val character: StateFlow<Event> = _characters

    var cachedCharacters: MutableList<Character> = mutableListOf()

    init {
        requestRandomCharacters()
    }

    private fun requestRandomCharacters() {
        viewModelScope.launch(dispatchers.io) {
            _characters.value = Event.Loading

            response =
                repository.getRandomCharacters()

            when (response) {
                is Resource.Error -> {
                    _characters.value =
                        Event.Failure("Something went wrong..\n${response.message}")
                }
                is Resource.Success -> {
                    val charactersResponse = response.data
                    if (charactersResponse != null) {
                        cachedCharacters.addAll(charactersResponse)
                        _characters.value = Event.Success(charactersResponse)
                    } else {
                        _characters.value = Event.Failure("UNKNOWN ERROR")
                    }
                }
            }
        }
    }

    override fun onCleared() {
        cachedCharacters.clear()
        super.onCleared()
    }

}