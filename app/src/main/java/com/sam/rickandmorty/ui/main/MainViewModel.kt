package com.sam.rickandmorty.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.util.DispatcherProvider
import com.sam.data.util.Resource
import com.sam.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: DefaultMainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class CharacterEvent {
        class Success(val resultList: List<Character>) : CharacterEvent()
        class Failure(val errorText: String) : CharacterEvent()
        object Loading : CharacterEvent()
        object Empty : CharacterEvent()
    }

    private val _characters = MutableStateFlow<CharacterEvent>(CharacterEvent.Empty)
    val characters: StateFlow<CharacterEvent> = _characters

    fun requestCharacters() {
        viewModelScope.launch(dispatchers.io) {
            _characters.value = CharacterEvent.Loading
            when (val response = repository.getAllCharacters()) {
                is Resource.Error -> {
                    _characters.value =
                        CharacterEvent.Failure("Something went wrong..! ${response.message}")
                }
                is Resource.Success -> {
                    val characters = response.data?.results
                    if (characters != null)
                        _characters.value = CharacterEvent.Success(characters)
                    else
                        _characters.value = CharacterEvent.Failure("UNKNOWN ERROR")
                }
            }
        }
    }
}