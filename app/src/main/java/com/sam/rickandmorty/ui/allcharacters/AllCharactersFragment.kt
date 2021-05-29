package com.sam.rickandmorty.ui.allcharacters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.sam.domain.Character
import com.sam.rickandmorty.databinding.FragmentAllCharactersBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.ui.main.MainFragmentDirections
import com.sam.rickandmorty.ui.viewmodels.AllCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class AllCharactersFragment : BaseFragment<FragmentAllCharactersBinding, AllCharactersViewModel>(),
    AllCharactersAdapter.OnCharacterItemClickListener {

    private lateinit var allCharactersAdapter: AllCharactersAdapter

    private lateinit var resourceBinding: ResourceEventBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAllCharactersBinding
        get() = FragmentAllCharactersBinding::inflate

    override fun setup() {
        setupViews()
        sendRequestAndGetData()
    }

    override fun getViewModelClass(): Class<AllCharactersViewModel> =
        AllCharactersViewModel::class.java

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.characters.collectLatest { pagingData ->
            allCharactersAdapter.submitData(pagingData)
        }
    }

    private fun sendRequestAndGetData() {
        lifecycleScope.launchWhenCreated {
            collectDataAndFeedToRecycler()
        }
    }

    private fun setupViews() {
        resourceBinding = binding.resourceEvent
        allCharactersAdapter = AllCharactersAdapter(this)
        setListenerForAdapter()

        binding.allCharactersRecycler.apply {
            adapter = allCharactersAdapter
        }
    }

    private fun setListenerForAdapter() {
        allCharactersAdapter.addLoadStateListener { loadState ->
            resourceBinding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                buttonFailure.isVisible = loadState.source.refresh is LoadState.Error
                textFailure.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    allCharactersAdapter.itemCount < 1
                ) {
                    binding.allCharactersRecycler.isVisible = false
                    textEmpty.isVisible = true
                } else {
                    textEmpty.isVisible = false
                }

                buttonFailure.setOnClickListener {
                    allCharactersAdapter.retry()
                }
            }
        }
    }

    override fun onClick(character: Character) {
        val direction: NavDirections =
            AllCharactersFragmentDirections
                .actionAllCharactersFragmentToCharacterDetailFragment(
                    character,
                    isFromMain = false
                )

        findNavController().navigate(direction)
    }
}