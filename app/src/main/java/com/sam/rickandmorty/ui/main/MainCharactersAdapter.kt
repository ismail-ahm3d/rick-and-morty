package com.sam.rickandmorty.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sam.domain.Character
import com.sam.rickandmorty.databinding.MainCharacterItemBinding


class MainCharactersAdapter(
    private val listener: OnItemClickListener
) : ListAdapter<Character, MainCharactersAdapter.MainCharacterHolder>(diffCallback) {

    lateinit var context: Context

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCharacterHolder {

        context = parent.context

        return MainCharacterHolder(
            MainCharacterItemBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainCharacterHolder, position: Int) {
        val currentCharacter = getItem(position)
        holder.bind(currentCharacter)
    }

    interface OnItemClickListener {
        fun onItemClicked(character: Character, circularCharacterImage: ImageView)
    }

    inner class MainCharacterHolder(private val binding: MainCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.apply {

                binding.circularCharacterImage.transitionName = "main_${character.id}"

                root.setOnClickListener {
                    listener.onItemClicked(character, binding.circularCharacterImage)
                }

                characterName.text = character.name

                Glide
                    .with(context)
                    .load(character.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(circularCharacterImage)
            }
        }
    }
}