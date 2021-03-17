package com.sam.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.sam.data.repository.DefaultMainRepository
import com.sam.data.util.Resource
import com.sam.rickandmorty.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: DefaultMainRepository

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated {
            val res = repository.getAllCharacters()

            Log.d(TAG, "onCreate: ${res.data}")
            when (res){
                is Resource.Error -> {
                    binding.textView.text = "${res.message}"
                }
                is Resource.Success -> {

                    val firstCharacterName = res.data?.results?.get(10)?.name
                    binding.textView.text = firstCharacterName
                }
            }
        }
    }
    private val TAG = "MainActivity"
}