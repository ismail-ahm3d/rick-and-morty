package com.sam.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.sam.domain.ApiResponse
import com.sam.rickandmorty.databinding.FragmentMainBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.viewmodels.CharactersViewModel
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.util.Event
import com.sam.rickandmorty.util.handleFailure
import com.sam.rickandmorty.util.handleLoading
import com.sam.rickandmorty.util.handleSuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, CharactersViewModel>() {

    private lateinit var mainAdapter: MainCharactersAdapter

    private lateinit var resourceBinding: ResourceEventBinding

    override fun getViewModelClass(): Class<CharactersViewModel> = CharactersViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun setup() {
        setupViews()
        sendRequestAndGetData()
    }

    private fun sendRequestAndGetData() {
        viewModel.requestCharacters(true)
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }
    }

    private fun setupViews() {
        resourceBinding = binding.resourceEvent

        binding.mainCharactersRecyclerView.apply {
            mainAdapter = MainCharactersAdapter()
            adapter = mainAdapter

            val helper = LinearSnapHelper()
            helper.attachToRecyclerView(this)
        }

        // Button Clicks
        binding.seeAllBtn.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAllCharactersFragment()
            findNavController().navigate(action)
        }
        resourceBinding.buttonFailure.setOnClickListener {
            sendRequestAndGetData()
        }
    }

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.character.collect { event ->
            when (event) {
                is Event.Success -> {
                    handleSuccess(resourceBinding)

                    val response = event.data as ApiResponse
                    mainAdapter.characters = response.results.shuffled()
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

}