package com.example.insurismoviesproject.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.insurismoviesproject.MainActivity
import com.example.insurismoviesproject.MovieDetailActivity
import com.example.insurismoviesproject.R
import com.example.insurismoviesproject.databinding.ItemMovieListBinding
import com.example.insurismoviesproject.retrofit.URLHelper
import com.example.insurismoviesproject.retrofit.responces.MoviesListResultsItem

class PopularMoviesAdapter(
    var mContext:MainActivity,
    val mList: ArrayList<MoviesListResultsItem>
): RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {

    class PopularMoviesViewHolder(var holder: ItemMovieListBinding): RecyclerView.ViewHolder(holder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val binding = DataBindingUtil.inflate<ItemMovieListBinding>(
            LayoutInflater.from(mContext),
            R.layout.item_movie_list,
            parent,
            false
        )
        return PopularMoviesViewHolder(binding)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(p0: PopularMoviesViewHolder, position: Int) {
        p0.holder.viewModel = PopularMoviesViewModel(mList[position])

        Glide.with(mContext)
            .load(URLHelper.IMAGE_BASE_URL+mList[position].backdropPath)
            .into(p0.holder.moviePoster)

        p0.holder.moviePoster.setOnClickListener {
            var intent = Intent(mContext, MovieDetailActivity::class.java)
            intent.putExtra("movie_id",mList[position].id.toString())
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }


}