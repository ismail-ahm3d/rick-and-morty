package com.sam.rickandmorty.ui.main

import android.transition.ChangeBounds
import android.transition.ChangeClipBounds
import android.transition.ChangeTransform
import android.transition.Transition
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.sam.domain.ApiResponse
import com.sam.domain.Character
import com.sam.rickandmorty.R
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, CharactersViewModel>(),
    MainCharactersAdapter.OnItemClickListener {

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
        viewModel.requestCharacters()
        lifecycleScope.launch {
            collectDataAndFeedToRecycler()
        }
    }

    private fun setupViews() {
        resourceBinding = binding.resourceEvent

        binding.mainCharactersRecyclerView.apply {
            mainAdapter = MainCharactersAdapter(this@MainFragment)
            adapter = mainAdapter

            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

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

    override fun onItemClicked(character: Character, navExtra: FragmentNavigator.Extras) {
//        val action = MainFragmentDirections.actionMainFragmentToCharacterDetailFragment()
//        val extraMain = FragmentNavigatorExtras(characterImageView to "main")
        val mainBundle = bundleOf("character" to character)

        findNavController().navigate(
            R.id.action_mainFragment_to_characterDetailFragment,
            mainBundle,
            null,
            navExtra
        )
    }

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.character.collect { event ->
            when (event) {
                is Event.Success -> {
                    handleSuccess(resourceBinding)

                    val response = event.data as ApiResponse
                    mainAdapter.characters = response.results
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