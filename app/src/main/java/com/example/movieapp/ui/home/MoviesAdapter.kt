package com.example.movieapp.ui.home


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.MovieRowItemBinding
import com.example.movieapp.model.Movies
import jp.wasabeef.glide.transformations.BlurTransformation

class MoviesAdapter(
    private var moviesList: List<Movies>, var context: Context,
    private val communicator: ListFragmentCommunicator,
    private val viewModel: FavoriViewModel
) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: MovieRowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: MovieRowItemBinding

        init {
            this.binding = binding
        }
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
        t.genresTextView.text = movies.genres.toString()

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

        holder.itemView.setOnClickListener {
            communicator.goToDetails(movies.id!!)
        }
        val c = viewModel.yemeklerFavoriListesi
        c.value!!.forEach{
            if(it.id!! == movies.id)
            {
                t.imageViewFull.visibility = View.VISIBLE
                t.imageViewEmpty.visibility = View.GONE
            }
        }
        t.imageViewEmpty.setOnClickListener {
            communicator.addToFavorites(movies)
            t.imageViewEmpty.visibility = View.GONE
            t.imageViewFull.visibility = View.VISIBLE
        }
        t.imageViewFull.setOnClickListener {
            communicator.deleteToFavorites(movies)
            t.imageViewFull.visibility = View.GONE
            t.imageViewEmpty.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}