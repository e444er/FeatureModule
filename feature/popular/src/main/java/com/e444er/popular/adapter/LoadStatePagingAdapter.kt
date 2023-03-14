package com.e444er.popular.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.e444er.popular.adapter.ErrorStateVH

internal class LoadStatePagingAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<ErrorStateVH>() {

    override fun onBindViewHolder(holder: ErrorStateVH, loadState: LoadState) {
        holder.bind(retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ErrorStateVH =
        ErrorStateVH.create(parent)
}