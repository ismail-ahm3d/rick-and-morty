package com.sam.rickandmorty.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.util.DispatcherProvider
import com.sam.data.util.Resource
import com.sam.domain.ApiResponse
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

    private lateinit var response: Resource<ApiResponse>

    sealed class CharacterEvent {
        class Success(val apiResponse: ApiResponse) : CharacterEvent()
        class Failure(val errorText: String) : CharacterEvent()
        object Loading : CharacterEvent()
        object Empty : CharacterEvent()
    }

    private val _characters = MutableStateFlow<CharacterEvent>(CharacterEvent.Empty)
    val characters: StateFlow<CharacterEvent> = _characters

    fun requestCharacters(isRandom: Boolean = false) {
        viewModelScope.launch(dispatchers.io) {
            _characters.value = CharacterEvent.Loading

            response = if (isRandom)
                repository.getRandomCharacters()
            else
                repository.getAllCharacters()

            when (response) {
                is Resource.Error -> {
                    _characters.value =
                        CharacterEvent.Failure("Something went wrong..! ${response.message}")
                }
                is Resource.Success -> {
                    val charactersResponse = response.data
                    if (charactersResponse != null)
                        _characters.value = CharacterEvent.Success(charactersResponse)
                    else
                        _characters.value = CharacterEvent.Failure("UNKNOWN ERROR")
                }
            }
        }
    }
}