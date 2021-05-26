package com.sam.rickandmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.util.DispatcherProvider
import com.sam.data.util.Resource
import com.sam.domain.Character
import com.sam.domain.network.NetworkCharacter
import com.sam.rickandmorty.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SingleCharacterViewModel @Inject constructor(
    private val repository: DefaultMainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private lateinit var characterResource: Resource<NetworkCharacter>

    private val _character = MutableStateFlow<Event>(Event.Empty)
    val character: StateFlow<Event> = _character

    suspend fun getSingleCharacter(id: Int) {
        withContext(dispatchers.io) {
            _character.value = Event.Loading

            characterResource = repository.getCharacterById(id)

            when (characterResource) {
                /**
                 * Success Event
                 */
                is Resource.Success -> {

                    val character = characterResource.data
                    if (character != null)
                        _character.value = Event.Success(character)
                    else
                        _character.value = Event.Failure("UNKNOWN ERROR")
                }
                /**
                 * Error Event
                 */
                is Resource.Error -> {
                    _character.value =
                        Event.Failure("Something went wrong..\n$characterResource.message")
                }
            }
        }
    }
}