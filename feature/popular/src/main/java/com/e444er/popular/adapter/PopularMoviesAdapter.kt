package com.e444er.popular.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.e444er.movie.model.Movie

internal class PopularMoviesAdapter(
    private val clickItem: (id: Int) -> Unit,
    private val selectItem: (movie: Movie?, isSelected: Boolean) -> Unit
): PagingDataAdapter<Movie, MovieVH>(PopularDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH =
        MovieVH.create(parent)

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        val item = getItem(holder.bindingAdapterPosition)
        val id = item?.id ?: 0

        holder.bind(
            item,
            { clickItem(id) },
            { selectItem(item, it) }
        )
    }

    override fun onViewRecycled(holder: MovieVH) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    class PopularDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Movie, newItem: Movie): Any {
            return true
        }
    }
}