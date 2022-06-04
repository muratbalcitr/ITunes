package com.m2lifeApps.hepsiburada.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.m2lifeApps.hepsiburada.core.extensions.observeEvent

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int,
    private val viewModelClass: Class<VM>
) : Fragment() {

    val viewModel by lazy {
        ViewModelProvider(this).get(viewModelClass)
    }
    lateinit var binding: DB

    abstract fun getScreenKey(): String
    abstract fun onDataBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        onDataBinding()
        getScreenKey()
        observeEvent(viewModel.baseEvent, ::onViewEvent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    private fun onViewEvent(event: BaseViewEvent) {
        when (event) {
        }
    }
}
