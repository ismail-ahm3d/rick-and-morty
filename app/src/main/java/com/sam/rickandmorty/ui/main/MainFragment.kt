package com.sam.rickandmorty.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sam.rickandmorty.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainAdapter: MainCharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
        }

        binding.buttonFailure.setOnClickListener {
            viewModel.requestCharacters()
            lifecycleScope.launchWhenCreated {
                collectDataAndFeedToRecycler()
            }
        }
    }
    private val TAG = "MainFragment"

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.characters.collect { event ->
            when (event) {
                is MainViewModel.CharacterEvent.Success -> {
                    failureViewsVisibility(false)
                    progressBarVisibility(false)

                    val characters = event.resultList
                    mainAdapter.characters = characters
                }
                is MainViewModel.CharacterEvent.Failure -> {
                    Log.d(TAG, "collectDataAndFeedToRecycler: ${event.errorText}")
                    progressBarVisibility(false)

                    failureViewsVisibility(true)
                }
                is MainViewModel.CharacterEvent.Loading -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}