package com.sam.rickandmorty.ui.allcharacters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sam.domain.Character
import com.sam.domain.Episode
import com.sam.rickandmorty.R
import com.sam.rickandmorty.databinding.AllCharacterItemBinding
import com.sam.rickandmorty.databinding.MainCharacterItemBinding

class AllCharactersAdapter : RecyclerView.Adapter<AllCharactersAdapter.AllCharactersHolder>() {

    lateinit var context: Context

    private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var characters: List<Character>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun getItemCount(): Int = characters.size

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
        val currentCharacter = characters[position]
        holder.bind(currentCharacter)
    }

    private val TAG = "AllCharactersAdapter"

    inner class AllCharactersHolder(private val binding: AllCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.apply {
                characterName.text = character.name
                checkAndSetStatusIcon(binding, character.status)
                statusAndSpecies.text = "${character.status} - ${character.species}"
                location.text = character.location.name
//                firstEpisodeName.text = character.firstEpisode.name
//                firstEpisodeName.isSelected = true

                Glide
                    .with(context)
                    .load(character.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.characterImage)
            }
        }
    }

    private fun checkAndSetStatusIcon(binding: AllCharacterItemBinding, status: String) {
        binding.apply {
            when (status) {
                "Alive" -> {
                    statusIcon.backgroundTintList =
                        context.applicationContext.getColorStateList(R.color.alive)
                }
                "Dead" -> {
                    statusIcon.backgroundTintList =
                        context.applicationContext.getColorStateList(R.color.dead)
                }
                "unknown" -> {
                    statusIcon.backgroundTintList =
                        context.applicationContext.getColorStateList(R.color.unknown)
                }
            }
        }
    }
}