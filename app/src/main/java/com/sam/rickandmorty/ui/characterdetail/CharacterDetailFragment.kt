package com.sam.rickandmorty.ui.characterdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.sam.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.ui.viewmodels.SingleCharacterViewModel
import com.sam.rickandmorty.util.Event
import com.sam.rickandmorty.util.checkAndSetStatusIcon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailsBinding, SingleCharacterViewModel>() {

    private val args: CharacterDetailFragmentArgs by navArgs()

    lateinit var resourceEventBinding: ResourceEventBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharacterDetailsBinding
        get() = FragmentCharacterDetailsBinding::inflate

    override fun getViewModelClass(): Class<SingleCharacterViewModel> =
        SingleCharacterViewModel::class.java

    override fun setup() {
        val isFromMainFragment = args.isFromMain

        if (isFromMainFragment) {
            /**
             * If the navigation is from main fragment,
             * @sharedElementEnterTransition will be enabled
             */
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)
            postponeEnterTransition(150, TimeUnit.MILLISECONDS)
        }

        val character = args.character

        binding.apply {
            characterImage.transitionName = "main_${character.id}"

//            characterName.text = character.name
//            checkAndSetStatusIcon(statusIcon, character.status, requireContext())
//            characterStatus.text = character.status
//            characterGender.text = character.gender
//            characterSpecies.text = character.species

            Glide
                .with(requireContext())
                .load(character.image)
                .into(characterImage)

//            Glide
//                .with(requireContext())
//                .load(character.image)
//                .transition(DrawableTransitionOptions.withCrossFade(1000))
//                .into(characterBackgroundImage)
        }

        requestAndSetData(character.id)
    }

    private fun requestAndSetData(id: Int) {
        lifecycleScope.launchWhenCreated {
            viewModel.getSingleCharacter(id)
            viewModel.character.collect { event ->
                when (event) {
                    is Event.Loading -> {
                    }
                    is Event.Success -> {
                    }
                    is Event.Failure -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }
}