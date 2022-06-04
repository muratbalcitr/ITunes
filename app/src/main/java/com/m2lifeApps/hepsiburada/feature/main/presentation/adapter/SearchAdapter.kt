package com.m2lifeApps.hepsiburada.feature.main.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.hepsiburada.core.common.DateHelper
import com.m2lifeApps.hepsiburada.core.loadImage
import com.m2lifeApps.hepsiburada.core.platform.BaseListAdapter
import com.m2lifeApps.hepsiburada.core.platform.BaseViewHolder
import com.m2lifeApps.hepsiburada.databinding.ViewholderSearchBinding
import com.m2lifeApps.hepsiburada.feature.main.domain.SearchViewModel

class SearchAdapter(
    val viewModel: SearchViewModel,
    var list: List<ITuneResponse.Result>
) : BaseListAdapter<ITuneResponse.Result>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return SearchAdapterViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchAdapterViewHolder -> {
                list.get(position).let { holder.bind(viewModel, it) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class SearchAdapterViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
    BaseViewHolder<ViewholderSearchBinding>(
        binding = ViewholderSearchBinding.inflate(inflater, parent, false)
    ) {
    fun bind(
        viewModels: SearchViewModel,
        items: ITuneResponse.Result
    ) {
        binding.item = items
        binding.viewModel = viewModels
        binding.imageView.loadImage(items.artworkUrl100)
        binding.releaseDateTextView.text =DateHelper.formatServerTime(items.releaseDate)
        binding.executePendingBindings()
    }
}
