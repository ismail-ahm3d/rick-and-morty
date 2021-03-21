package com.sam.rickandmorty.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.sam.rickandmorty.databinding.FragmentMainBinding
import com.sam.rickandmorty.ui.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var binding: FragmentMainBinding
//    private val binding get() = _binding!!

    private lateinit var mainAdapter: MainCharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        setupViews()
        viewModel.requestCharacters()
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }

        return view
    }

    private fun setupViews() {
        binding.mainCharactersRecyclerView.apply {
            mainAdapter = MainCharactersAdapter()
            adapter = mainAdapter

            val helper = LinearSnapHelper()
            helper.attachToRecyclerView(this)
        }

        // Button Clicks
        binding.apply {
            buttonFailure.setOnClickListener {
                viewModel.requestCharacters()
                lifecycleScope.launchWhenCreated {
                    collectDataAndFeedToRecycler()
                }
            }

            seeAllBtn.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToAllCharactersFragment()
                findNavController().navigate(action)
            }
        }
    }

    private val TAG = "MainFragment"

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.characters.collect { event ->
            when (event) {
                is CharactersViewModel.CharacterEvent.Success -> {
                    failureViewsVisibility(false)
                    progressBarVisibility(false)

                    val response = event.apiResponse
                    mainAdapter.characters = response.results
                }
                is CharactersViewModel.CharacterEvent.Failure -> {
                    Log.d(TAG, "collectDataAndFeedToRecycler: ${event.errorText}")
                    progressBarVisibility(false)

                    failureViewsVisibility(true)
                }
                is CharactersViewModel.CharacterEvent.Loading -> {
                    failureViewsVisibility(false)

                    progressBarVisibility(true)
                }
                else -> Unit
            }
        }
    }

    private fun failureViewsVisibility(isVisible: Boolean) {
        if (!isVisible) {
            binding.buttonFailure.visibility = View.GONE
            binding.textFailure.visibility = View.GONE
        } else {
            binding.buttonFailure.isVisible = isVisible
            binding.textFailure.isVisible = isVisible
        }
    }

    private fun progressBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding.
//    }
}