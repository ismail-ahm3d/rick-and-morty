package com.sam.rickandmorty.ui.allcharacters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sam.domain.Character
import com.sam.domain.Episode
import com.sam.domain.network.NetworkCharacter
import com.sam.domain.network.asDomainModel
import com.sam.rickandmorty.R
import com.sam.rickandmorty.databinding.AllCharacterItemBinding
import com.sam.rickandmorty.databinding.MainCharacterItemBinding
import com.sam.rickandmorty.ui.main.MainCharactersAdapter
import com.sam.rickandmorty.util.checkAndSetStatusIcon

class AllCharactersAdapter(
    private val listener: OnCharacterItemClickListener
) :
    PagingDataAdapter<NetworkCharacter, AllCharactersAdapter.AllCharactersHolder>(
        CHARACTER_COMPARATOR
    ) {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCharactersHolder {

        context = parent.context

        return AllCharactersHolder(
            AllCharacterItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AllCharactersHolder, position: Int) {
        val currentCharacter = getItem(position)
        if (currentCharacter != null)
            holder.bind(currentCharacter)
    }

    interface OnCharacterItemClickListener {
        fun onClick(character: Character)
    }

    inner class AllCharactersHolder(private val binding: AllCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: NetworkCharacter) {
            binding.apply {
                characterName.text = character.name
                checkAndSetStatusIcon(binding.statusIcon, character.status, context)
                statusAndSpecies.text = "${character.status} - ${character.species}"
                location.text = character.location.name
                gender.text = character.gender

                Glide
                    .with(context)
                    .load(character.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.characterImage)

                root.setOnClickListener {
                    listener.onClick(character.asDomainModel())
                }
            }
        }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<NetworkCharacter>() {
            override fun areItemsTheSame(
                oldItem: NetworkCharacter,
                newItem: NetworkCharacter
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: NetworkCharacter,
                newItem: NetworkCharacter
            ): Boolean =
                oldItem == newItem

        }
    }
}