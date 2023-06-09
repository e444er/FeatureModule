package com.e444er.popular

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.e444er.common.nav.NavManager
import com.e444er.common.nav.NavScreen
import com.e444er.popular.adapter.PopularMoviesAdapter
import com.e444er.popular.databinding.FragmentPopularListBinding
import com.e444er.popular.adapter.LoadStatePagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class PopularListFragment : Fragment() {

    @Inject
    lateinit var navManager: NavManager
    private val popularListVM: PopularListVM by viewModels()
    internal var adapter: PopularMoviesAdapter? = null
    private var binding: FragmentPopularListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPopularListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PopularMoviesAdapter(
            { navManager.navigate(NavScreen.MovieDetail(it.toString())) },
            { movie, isSelected -> popularListVM.setFavoriteMovie(movie, isSelected) }
        )

        binding?.moviesRV?.also {
            it.layoutManager =  LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            it.adapter = adapter?.withLoadStateFooter(LoadStatePagingAdapter{ adapter?.retry() })
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                popularListVM.pagingMovieData.collect {
                    adapter?.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                adapter?.loadStateFlow
                    ?.distinctUntilChangedBy { it.refresh }
                    ?.filter {it.refresh is LoadState.Error}
                    // Scroll to top is synchronous with UI updates, even if remote load was triggered.
                    ?.collect {
                        if (it.refresh is LoadState.Error)
                            Toast.makeText(
                                requireContext(),
                                (it.refresh as LoadState.Error).error.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}