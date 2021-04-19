package com.sam.rickandmorty.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.sam.rickandmorty.databinding.ResourceEventBinding
import com.sam.rickandmorty.util.failureViewsVisibility

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected val binding: VB
        get() = _binding as VB

    protected lateinit var viewModel: VM

    abstract fun setup()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)

        viewModel = ViewModelProvider(this).get(getViewModelClass())

        setup()

        return binding.root
    }

    abstract fun getViewModelClass(): Class<VM>

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

