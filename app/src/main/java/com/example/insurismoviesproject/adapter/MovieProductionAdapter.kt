package com.example.insurismoviesproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.insurismoviesproject.MovieDetailActivity
import com.example.insurismoviesproject.R
import com.example.insurismoviesproject.databinding.ItemProductionCompBinding
import com.example.insurismoviesproject.retrofit.URLHelper
import com.example.insurismoviesproject.retrofit.responces.ProductionCompaniesItem

class MovieProductionAdapter(
    var mContext: MovieDetailActivity,
    var mList: ArrayList<ProductionCompaniesItem>
): RecyclerView.Adapter<MovieProductionAdapter.MovieProductionViewHolder>() {

    class MovieProductionViewHolder(var holder: ItemProductionCompBinding): RecyclerView.ViewHolder(holder.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieProductionViewHolder {
        val binding = DataBindingUtil.inflate<ItemProductionCompBinding>(
            LayoutInflater.from(mContext),
            R.layout.item_production_comp,
            parent,
            false
        )
        return MovieProductionViewHolder(binding)
    }

    override fun onBindViewHolder(p0: MovieProductionViewHolder, position: Int) {

        if(mList[position].logoPath != "" && mList[position].logoPath != null){
            Glide.with(mContext)
                .load(URLHelper.IMAGE_BASE_URL+mList[position].logoPath)
                .into(p0.holder.productionIV)
        }else{
            p0.holder.productionIV.visibility = View.GONE
        }


        p0.holder.productionNameTV.text = mList[position].name

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}