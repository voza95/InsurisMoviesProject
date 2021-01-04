package com.example.insurismoviesproject.adapter

import androidx.databinding.BaseObservable
import com.example.insurismoviesproject.retrofit.responces.MoviesListResultsItem

class PopularMoviesViewModel(
    var moviesResultItem: MoviesListResultsItem
): BaseObservable() {
}