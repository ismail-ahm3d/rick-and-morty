package com.sam.rickandmorty.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.util.DispatcherProvider
import com.sam.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class AllCharactersViewModel @Inject constructor(
    repository: DefaultMainRepository,
) : ViewModel() {

    val characters =
        repository.getAllCharacters().cachedIn(viewModelScope)
}