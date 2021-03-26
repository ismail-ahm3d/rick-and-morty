package com.sam.rickandmorty.ui.allcharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.sam.domain.ApiResponse
import com.sam.rickandmorty.databinding.FragmentAllCharactersBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.ui.viewmodels.CharactersViewModel
import com.sam.rickandmorty.util.Event
import com.sam.rickandmorty.util.handleFailure
import com.sam.rickandmorty.util.handleLoading
import com.sam.rickandmorty.util.handleSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AllCharactersFragment : BaseFragment<FragmentAllCharactersBinding, CharactersViewModel>() {

    private lateinit var allCharactersAdapter: AllCharactersAdapter

    private lateinit var resourceBinding: ResourceEventBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllCharactersBinding
        get() = FragmentAllCharactersBinding::inflate

    override fun setup() {
        setupViews()
        sendRequestAndGetData()
    }

    override fun getViewModelClass(): Class<CharactersViewModel> = CharactersViewModel::class.java

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.character.collect { event ->
            when (event) {
                is Event.Success -> {
                    handleSuccess(resourceBinding)

                    val response = event.data as ApiResponse
                    allCharactersAdapter.characters = response.results
                }
                is Event.Failure -> {
                    handleFailure(resourceBinding)
                }
                is Event.Loading -> {
                    handleLoading(resourceBinding)
                }
                else -> Unit
            }
        }
    }

    private fun sendRequestAndGetData() {
        viewModel.requestCharacters()
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }
    }

    private fun setupViews() {
        resourceBinding = binding.resourceEvent

        binding.allCharactersRecycler.apply {
            allCharactersAdapter = AllCharactersAdapter()
            adapter = allCharactersAdapter
        }

        resourceBinding.buttonFailure.setOnClickListener {
            sendRequestAndGetData()
        }
    }
}