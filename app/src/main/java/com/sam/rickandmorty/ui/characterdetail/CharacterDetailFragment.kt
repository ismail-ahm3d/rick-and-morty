package com.sam.rickandmorty.ui.characterdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sam.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.ui.BaseFragment
import com.sam.rickandmorty.ui.viewmodels.SingleCharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<FragmentCharacterDetailsBinding, SingleCharacterViewModel>() {

    lateinit var resourceEventBinding: ResourceEventBinding

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCharacterDetailsBinding
        get() = FragmentCharacterDetailsBinding::inflate

    override fun getViewModelClass(): Class<SingleCharacterViewModel> =
        SingleCharacterViewModel::class.java

    override fun setup() {
        binding
    }
}