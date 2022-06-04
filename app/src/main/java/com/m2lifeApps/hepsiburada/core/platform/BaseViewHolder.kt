package com.m2lifeApps.hepsiburada.core.platform

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T : ViewDataBinding>(
    val binding: T
) : RecyclerView.ViewHolder(binding.root)
