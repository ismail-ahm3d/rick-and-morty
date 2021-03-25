package com.sam.rickandmorty.ui.allcharacters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sam.rickandmorty.databinding.FragmentAllCharactersBinding
import com.sam.rickandmorty.ui.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AllCharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()

    lateinit var allCharactersAdapter: AllCharactersAdapter

    private lateinit var binding: FragmentAllCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        val view = binding.root

        setupViews()
        viewModel.requestCharacters()
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }

        return view
    }

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.characters.collect { event ->
            when (event) {
                is CharactersViewModel.CharacterEvent.Success -> {
//                    handleSuccess(event)
                }
                is CharactersViewModel.CharacterEvent.Failure -> {
//                    handleFailure()
                }
                is CharactersViewModel.CharacterEvent.Loading -> {
//                    handleLoading()
                }
                else -> Unit
            }
        }
    }

//    private fun handleSuccess(event: CharactersViewModel.CharacterEvent.Success) {
//        failureViewsVisibility(binding.textFailure, binding.buttonFailure, false)
//        binding.progressBar.isVisible = false
//
//        val response = event.apiResponse
//        allCharactersAdapter.characters = response.results
//    }
//
//    private fun handleLoading() {
//        failureViewsVisibility(binding.textFailure, binding.buttonFailure, false)
//
//        binding.progressBar.isVisible = true
//    }
//
//    private fun handleFailure() {
//        binding.progressBar.isVisible = false
//
//        failureViewsVisibility(binding.textFailure, binding.buttonFailure, true)
//    }

    private fun setupViews() {
        binding.allCharactersRecycler.apply {
            allCharactersAdapter = AllCharactersAdapter()
            adapter = allCharactersAdapter
        }

        binding.buttonFailure.setOnClickListener {
            viewModel.requestCharacters()
            lifecycleScope.launchWhenCreated {
                collectDataAndFeedToRecycler()
            }
        }
    }
}