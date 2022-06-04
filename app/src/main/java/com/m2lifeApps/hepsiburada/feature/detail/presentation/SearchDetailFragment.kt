package com.m2lifeApps.hepsiburada.feature.detail.presentation

import android.content.Intent
import android.os.Bundle
import androidx.core.net.toUri
import com.google.gson.Gson
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.hepsiburada.R
import com.m2lifeApps.hepsiburada.core.common.DateHelper
import com.m2lifeApps.hepsiburada.core.common.PageName
import com.m2lifeApps.hepsiburada.core.extensions.observe
import com.m2lifeApps.hepsiburada.core.extensions.toCurrency
import com.m2lifeApps.hepsiburada.core.loadImage
import com.m2lifeApps.hepsiburada.core.platform.BaseFragment
import com.m2lifeApps.hepsiburada.databinding.FragmentSearchDetailBinding
import com.m2lifeApps.hepsiburada.feature.detail.domain.SearchDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.viewholder_search.*

@AndroidEntryPoint
class SearchDetailFragment :
    BaseFragment<FragmentSearchDetailBinding, SearchDetailViewModel>(
        layoutId = R.layout.fragment_search_detail,
        viewModelClass = SearchDetailViewModel::class.java
    ) {
    override fun getScreenKey(): String {
        return PageName.characterDetail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detail =
            Gson().fromJson(arguments?.getString("detail"), ITuneResponse.Result::class.java)
        viewModel.setDetail(detail)
    }

    override fun onDataBinding() {
        binding.viewModel = viewModel

        val mHeight = requireActivity().windowManager.defaultDisplay.height
        binding.verticalSpan.layoutParams.height = (mHeight * 0.3).toInt()

        observe(viewModel.iTuneDetail) { entity ->

            binding.apply {
                item = entity
                moviePrice.text = entity.trackPrice.toCurrency(entity.currency)
                movieReleaseDateTextview.text = DateHelper.formatServerTime(entity.releaseDate)
                movieImage.loadImage(entity.artworkUrl100.replace("100x100","600x600"))

                playVideo.setOnClickListener {
                    if (entity.previewUrl.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.setDataAndType(entity.previewUrl.toUri(), "video/*")
                        startActivity(intent)
                    }
                }

                movieTitle.paint
            }
        }
    }
}
