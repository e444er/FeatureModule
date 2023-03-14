package com.e444er.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e444er.movie.model.Movie
import com.e444er.movie.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteListVM @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies = _favoriteMovies.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.favoriteMovies().collect{ movies ->
                _favoriteMovies.value = movies
            }
        }
    }

    fun deleteFromFavorite(id: Int){
        viewModelScope.launch {
            movieRepository.deleteFromFavorite(id)
        }
    }
}