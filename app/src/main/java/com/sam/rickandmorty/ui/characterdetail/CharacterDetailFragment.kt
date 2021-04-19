package com.sam.rickandmorty.ui.characterdetail

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sam.domain.Character
import com.sam.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.ui.viewmodels.SingleCharacterViewModel
import com.sam.rickandmorty.util.Event
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailsBinding, SingleCharacterViewModel>() {

    private val args: CharacterDetailFragmentArgs by navArgs()

    lateinit var resourceEventBinding: ResourceEventBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharacterDetailsBinding
        get() = FragmentCharacterDetailsBinding::inflate

    override fun getViewModelClass(): Class<SingleCharacterViewModel> =
        SingleCharacterViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun setup() {
        val character = args.character

        binding.detailImage.transitionName = "main_${character.id}"

//        setSharedElementEnterTransition(ChangeBounds())

        Glide
            .with(this@CharacterDetailFragment)
            .load(character.image)
            .apply(RequestOptions.circleCropTransform())
//            .transform(GranularRoundedCorners(0f, 32f, 32f, 0f))
            .into(binding.detailImage)
//        viewModel.requestCharacter(id)
//        lifecycleScope.launchWhenCreated {
//            viewModel.character.collect { event ->
//                when (event) {
//                    is Event.Success -> {
//                        val character = event.data as Character
//                        Glide
//                            .with(this@CharacterDetailFragment)
//                            .load(character.image)
//                            .into(binding.detailImage)
//                    }
//                }
//            }
//        }
    }
}