package com.sam.rickandmorty.ui.main

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DrawableTransformation
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sam.domain.Character
import com.sam.rickandmorty.R
import com.sam.rickandmorty.databinding.MainCharacterItemBinding

class MainCharactersAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<MainCharactersAdapter.MainCharacterHolder>() {

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
        val currentCharacter = characters[position]
        holder.bind(currentCharacter)
    }

    interface OnItemClickListener {
        fun onItemClicked(character: Character, navExtra: FragmentNavigator.Extras)
    }

    inner class MainCharacterHolder(private val binding: MainCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            binding.apply {

                binding.circularCharacterImage.transitionName = "main_${character.id}"

                val navExtra = FragmentNavigator.Extras.Builder()
                    .addSharedElement(
                        binding.circularCharacterImage,
                        binding.circularCharacterImage.transitionName
                    )
                    .build()

                root.setOnClickListener {
                    listener.onItemClicked(character, navExtra)
                }

                characterName.text = character.name
                characterStatus.text = character.status
                checkAndSetStatusIcon(binding, character.status)

                Glide
                    .with(context)
                    .load(character.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(characterImage)

                Glide
                    .with(context)
                    .load(character.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(circularCharacterImage)
            }
        }
    }

    private fun checkAndSetStatusIcon(binding: MainCharacterItemBinding, status: String) {
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