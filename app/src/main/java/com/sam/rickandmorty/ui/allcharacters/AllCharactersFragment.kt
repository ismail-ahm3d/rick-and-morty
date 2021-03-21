package com.sam.rickandmorty.ui.allcharacters

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.sam.rickandmorty.databinding.FragmentAllCharactersBinding
import com.sam.rickandmorty.ui.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class AllCharactersFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()

    lateinit var allCharactersAdapter: AllCharactersAdapter

    private var _binding: FragmentAllCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
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
//                    failureViewsVisibility(false)
//                    progressBarVisibility(false)

                    val response = event.apiResponse
                    allCharactersAdapter.characters = response.results
                }
//                is CharactersViewModel.CharacterEvent.Failure -> {
//                    Log.d(TAG, "collectDataAndFeedToRecycler: ${event.errorText}")
//                    progressBarVisibility(false)
//
//                    failureViewsVisibility(true)
//                }
//                is CharactersViewModel.CharacterEvent.Loading -> {
//                    failureViewsVisibility(false)
//
//                    progressBarVisibility(true)
//                }
                else -> Unit
            }
        }
    }

    private fun setupViews() {
        binding.allCharactersRecycler.apply {
            allCharactersAdapter = AllCharactersAdapter()
            adapter = allCharactersAdapter
//
//            val helper = LinearSnapHelper()
//            helper.attachToRecyclerView(this)
        }
    }
}