package com.sam.rickandmorty.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
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
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, CharactersViewModel>(),
    MainCharactersAdapter.OnItemClickListener {

    private lateinit var mainAdapter: MainCharactersAdapter

    private lateinit var resourceBinding: ResourceEventBinding

    private lateinit var cachedList: List<Character>

    override fun getViewModelClass(): Class<CharactersViewModel> = CharactersViewModel::class.java

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    override fun setup() {
        setupViews()
        sendRequestAndGetData()
    }

    private fun sendRequestAndGetData() {
        if (viewModel.cachedCharacters.size > 0) {
            handleSuccess(resourceBinding)

            mainAdapter.submitList(viewModel.cachedCharacters)
        } else {
            lifecycleScope.launchWhenCreated {
                collectDataAndFeedToRecycler()
            }
        }
    }

    private fun setupViews() {
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        resourceBinding = binding.resourceEvent

        binding.mainCharactersRecyclerView.apply {
            mainAdapter = MainCharactersAdapter(this@MainFragment)
            adapter = mainAdapter
            mainAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

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

    override fun onItemClicked(
        character: Character,
        circularCharacterImage: ImageView
    ) {
        val direction: NavDirections =
            MainFragmentDirections.actionMainFragmentToCharacterDetailFragment(character)

        val extras = FragmentNavigatorExtras(
            circularCharacterImage to "main_${character.id}",
        )

        findNavController().navigate(direction, extras)
    }

    private suspend fun collectDataAndFeedToRecycler() {
        viewModel.character.collect { event ->
            when (event) {
                is Event.Success -> {
                    handleSuccess(resourceBinding)

                    val response = event.data as List<Character>
                    mainAdapter.submitList(response)
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