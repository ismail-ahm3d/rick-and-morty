package com.sam.rickandmorty.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.sam.rickandmorty.databinding.FragmentMainBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.viewmodel.CharactersViewModel
import com.sam.rickandmorty.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, CharactersViewModel>() {

    private lateinit var mainAdapter: MainCharactersAdapter

    override fun getViewModelClass(): Class<CharactersViewModel> = CharactersViewModel::class.java

    override fun setup() {
        setupViews()
    }

    override fun getResourceEventBinding(): ResourceEventBinding =
        FragmentMainBinding.inflate(layoutInflater).resourceEvent

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    //    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentMainBinding.inflate(inflater, container, false)
////        resourceBinding = binding.resourceEvent
//
//        setupViews()
//        sendRequestAndGetData()
//
//        return binding.root
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sendRequestAndGetData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun sendRequestAndGetData() {
        viewModel.requestCharacters(true)
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }
    }

    private fun setupViews() {
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
    }

    private suspend fun collectDataAndFeedToRecycler() {
//        viewModel.characters.collect { event ->
//            when (event) {
//                is CharactersViewModel.CharacterEvent.Success -> {
//                    handleSuccess()
//
//                    val response = event.apiResponse
//                    mainAdapter.characters = response.results.shuffled()
//                }
//                is CharactersViewModel.CharacterEvent.Failure -> {
//                    handleFailure()
//                }
//                is CharactersViewModel.CharacterEvent.Loading -> {
//                    handleLoading()
//                }
//                else -> Unit
//            }
//        }
    }
}