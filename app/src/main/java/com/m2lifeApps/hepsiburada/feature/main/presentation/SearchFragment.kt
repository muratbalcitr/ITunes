package com.m2lifeApps.hepsiburada.feature.main.presentation

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.m2lifeApps.data.remote.response.ITuneResponse
import com.m2lifeApps.hepsiburada.R
import com.m2lifeApps.hepsiburada.core.common.FilterType
import com.m2lifeApps.hepsiburada.core.common.PageName
import com.m2lifeApps.hepsiburada.core.extensions.observe
import com.m2lifeApps.hepsiburada.core.extensions.observeEvent
import com.m2lifeApps.hepsiburada.core.platform.BaseFragment
import com.m2lifeApps.hepsiburada.databinding.FragmentSearchBinding
import com.m2lifeApps.hepsiburada.feature.main.domain.SearchViewEvent
import com.m2lifeApps.hepsiburada.feature.main.domain.SearchViewModel
import com.m2lifeApps.hepsiburada.feature.main.presentation.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(
    layoutId = R.layout.fragment_search,
    viewModelClass = SearchViewModel::class.java
) {
    lateinit var searchAdapter: SearchAdapter
    var selectedMediaType = "all"

    /*
    * track = movies
    * audiobook
    * */
    override fun getScreenKey(): String {
        return PageName.characterList
    }

    var limit = 20
    override fun onDataBinding() {
        binding.viewModel = viewModel
        viewModel.searchMovies(
            "star",
            limit = limit,
            media = selectedMediaType
        )
        observeEvent(viewModel.event, ::onViewEvent)
        observe(viewModel.iTuneResponse) {
            initAdapter(it.results)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String): Boolean {
                return if (p0.length > 3) {
                    viewModel.term.postValue(p0)
                    viewModel.searchMovies(
                        p0,
                        limit = limit,
                        media = selectedMediaType
                    )
                    true
                } else false
            }
        })
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (viewModel.iTuneResponse.value != null)
                    filterTabItem(tab)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (viewModel.iTuneResponse.value != null)
                    filterTabItem(tab)
            }
        })
    }

    private var loading = true
    var pastVisiblesItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var lastItemPosition: Int = 0
    fun initAdapter(results: List<ITuneResponse.Result>) {
        searchAdapter = SearchAdapter(viewModel, results)
        binding.recyclerView.apply {
            adapter = searchAdapter

            val manager = GridLayoutManager(requireContext(), 2)
            layoutManager = manager
            if (results.isNotEmpty()) {
                scrollToPosition(lastItemPosition)
                smoothScrollToPosition(lastItemPosition)
            }
            searchAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.ALLOW

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) { // check for scroll down
                        visibleItemCount = recyclerView.layoutManager?.childCount!!
                        totalItemCount = recyclerView.layoutManager?.itemCount!!
                        pastVisiblesItems =
                            (recyclerView.layoutManager as GridLayoutManager?)?.findFirstVisibleItemPosition()!!

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            lastItemPosition =
                                (recyclerView.layoutManager as GridLayoutManager?)?.findLastVisibleItemPosition()!!

                            limit += 20
                            viewModel.setMoreLoading(true)
                            viewModel.searchMovies(
                                viewModel.term.value ?: "star",
                                limit = limit,
                                offset = 0,
                                media = selectedMediaType
                            )
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    Timber.e("test$newState")
                }
            })
        }
    }

    fun filterTabItem(tab: TabLayout.Tab?) {
        when (tab?.position) {
            0 -> {
                // All
                filterResult(FilterType.ALL)
            }
            1 -> {
                // Music
                filterResult(FilterType.MUSIC)
            }
            2 -> {
                // EBook
                filterResult(FilterType.EBOOK)
            }
            3 -> {
                // Movie
                filterResult(FilterType.MOVIE)
            }
            4 -> {
                // Podcast
                filterResult(FilterType.PODCAST)
            }
        }
    }

    fun filterResult(filterType: FilterType) {
        viewModel.viewModelScope.launch {
            val list =
                if (filterType.type == FilterType.ALL.type)
                    viewModel.searchResponsePagedLiveData.value
                else
                    viewModel.searchResponsePagedLiveData.value?.filter {
                        it.wrapperType == filterType.type
                    }
            selectedMediaType = filterType.type
            if (list != null) {
                searchAdapter.list = list
                searchAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun onViewEvent(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.SelectItem -> {

                val bundle = Bundle()
                bundle.putString("detail", Gson().toJson(event.item))
                findNavController().navigate(R.id.action_characterList_to_characterDetail, bundle)
            }
        }
    }
}
