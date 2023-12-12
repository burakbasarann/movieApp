package com.example.movieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.model.Movies
import com.example.movieapp.model.RoomDBEntity
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class HomeFragment : Fragment(), ListFragmentCommunicator {

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelDB: FavoriViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesTrendsAdapter: MoviesTrendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomeViewModel by viewModels()
        val tempViewModel2: FavoriViewModel by viewModels()

        viewModel = tempViewModel
        viewModelDB = tempViewModel2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.moviesList.observe(viewLifecycleOwner) {
            moviesAdapter = MoviesAdapter(it, requireContext(), this, viewModelDB)
            moviesTrendsAdapter = MoviesTrendsAdapter(it, requireContext()) {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        it
                    )
                )
            }
            binding.rvTrending.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvTrending.adapter = moviesTrendsAdapter
            binding.rvMovies.adapter = moviesAdapter

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun goToDetails(movieId: Int) {
        try {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                    movieId
                )
            )
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Bir Hata İle Karşılaşıldı", Toast.LENGTH_SHORT).show()
        }

    }

    override fun addToFavorites(movies: Movies) {
        try {
            val add = RoomDBEntity(
                movies.id,
                movies.title,
                movies.year,
                movies.runtime,
                movies.director,
                movies.actors,
                movies.plot,
                movies.posterUrl
            )
            viewModelDB.insertMovies(add)
            Toast.makeText(
                requireContext(), movies.title + " Favorilere Eklendi", Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Bir Hata İle Karşılaşıldı", Toast.LENGTH_SHORT).show()
        }

    }

    override fun deleteToFavorites(movies: Movies) {
        try {
            val delete = RoomDBEntity(
                movies.id,
                movies.title,
                movies.year,
                movies.runtime,
                movies.director,
                movies.actors,
                movies.plot,
                movies.posterUrl
            )
            viewModelDB.deleteMovies(delete)
            Toast.makeText(
                requireContext(),
                movies.title + " Favorilerden Silindi",
                Toast.LENGTH_LONG
            )
                .show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Bir Hata İle Karşılaşıldı", Toast.LENGTH_SHORT).show()
        }

    }
}

interface ListFragmentCommunicator {
    fun goToDetails(movieId: Int)
    fun addToFavorites(movies: Movies)
    fun deleteToFavorites(movies: Movies)
}