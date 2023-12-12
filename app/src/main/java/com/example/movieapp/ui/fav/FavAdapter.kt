package com.example.movieapp.ui.fav


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieRowItemBinding
import com.example.movieapp.model.RoomDBEntity
import com.example.movieapp.ui.home.FavoriViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

class FavAdapter(
    private var moviesList: MutableList<RoomDBEntity>,
    var context: Context,
    private var viewModel: FavoriViewModel,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<FavAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: MovieRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: MovieRowItemBinding

        fun bind(movieId: Int) {
            itemView.setOnClickListener {
                onItemClick(movieId)
            }
        }

        init {
            this.binding = binding
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        moviesList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val tasarim = MovieRowItemBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(tasarim)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movies = moviesList[position]

        val t = holder.binding
        t.titleTextView.text = movies.title
        t.productionYearTextView.text = movies.year

        Glide.with(context)
            .load("${movies.posterUrl}")
            .override(1024, 768)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(t.backgroundImageView)

        Glide.with(context)
            .load("${movies.posterUrl}")
            .override(1024, 768)
            .placeholder(R.drawable.loading)
            .into(t.movieImageView)

        t.imageViewEmpty.visibility = View.GONE
        t.imageViewFull.visibility = View.VISIBLE

        holder.bind(movies.id!!)
        t.imageViewFull.setOnClickListener {
            viewModel.deleteMovies(movies)
            Toast.makeText(context, movies.title + " Favorilerden Kaldırıldı", Toast.LENGTH_SHORT)
                .show()
            removeItem(position)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}