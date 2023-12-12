package com.example.movieapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.ui.fav.FavFragmentDirections
import com.example.movieapp.ui.home.HomeFragmentDirections
import com.example.movieapp.ui.home.HomeViewModel
import com.example.movieapp.util.removeWhitespaces
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.BlurTransformation

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var movieId: String
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val genres = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeViewModel by viewModels()
        viewModel = tempViewModel
        movieId = MovieDetailFragmentArgs.fromBundle(requireArguments()).movieId.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.moviesList.observe(viewLifecycleOwner) { listMovies ->
            listMovies.forEach { movies ->
                if (movies.id == movieId.toInt()) {

                    Glide.with(requireContext())
                        .load("${movies.posterUrl}")
                        .override(1024, 768)
                        .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 5)))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(binding.backgroundImageView.drawable) //using previous image to prevent image flickering when switching images.
                        .into(binding.backgroundImageView)


                    Glide.with(requireContext())
                        .load("${movies.posterUrl}")
                        .override(1024, 768)
                        .placeholder(R.drawable.loading)
                        .into(binding.movieImageView)

                    binding.titleTextView.text = movies.title
                    binding.directorTextView.text = movies.director
                    binding.productionYearTextView.text = movies.year
                    binding.descriptionTextView.text = movies.plot
                    binding.castTextView.text = movies.actors

                    movies.genres?.forEach { category ->
                        genres.add(category)
                    }
                    binding.genresTextView.text = genres.toString()

                    binding.titleTextView.setOnClickListener {
                        findNavController().navigate(
                            MovieDetailFragmentDirections.actionDetailFragmentToWebViewFragment(
                                movies.title!!
                            )
                        )
                    }
                }
            }
            binding.imghome.setOnClickListener {
                findNavController().popBackStack()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}