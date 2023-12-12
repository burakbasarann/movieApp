package com.example.movieapp.ui.home


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieTrendItemBinding
import com.example.movieapp.model.Movies

class MoviesTrendsAdapter(
    private var moviesList: List<Movies>, var context: Context,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MoviesTrendsAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: MovieTrendItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: MovieTrendItemBinding

        fun bind(movieId: Int) {
            itemView.setOnClickListener {
                onItemClick(movieId)
            }
        }

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val tasarim = MovieTrendItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(tasarim)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = moviesList[position]
        val t = holder.binding
        Glide.with(context)
            .load("${movies.posterUrl}")
            .override(200, 200)
            .placeholder(R.drawable.start_img_min_blur)
            .into(t.posterImage)

        holder.bind(movies.id!!)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}