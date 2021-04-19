package com.sam.rickandmorty.ui.allcharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.sam.rickandmorty.databinding.FragmentAllCharactersBinding
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.ui.viewmodels.AllCharactersViewModel
import com.sam.rickandmorty.ui.viewmodels.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AllCharactersFragment : BaseFragment<FragmentAllCharactersBinding, AllCharactersViewModel>() {

    private lateinit var allCharactersAdapter: AllCharactersAdapter

//    private lateinit var resourceBinding: ResourceEventBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllCharactersBinding
        get() = FragmentAllCharactersBinding::inflate

    override fun setup() {
        setupViews()
        sendRequestAndGetData()
    }

    override fun getViewModelClass(): Class<AllCharactersViewModel> =
        AllCharactersViewModel::class.java

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.characters.collectLatest {
            allCharactersAdapter.submitData(it)
        }
    }

    private fun sendRequestAndGetData() {
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }
    }

    private fun setupViews() {
        allCharactersAdapter = AllCharactersAdapter()

        binding.allCharactersRecycler.apply {
            adapter = allCharactersAdapter
        }
    }
}