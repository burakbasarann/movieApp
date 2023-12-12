package com.example.movieapp.ui.fav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.databinding.FragmentFavBinding
import com.example.movieapp.ui.home.FavoriViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavFragment : Fragment() {

    private var _binding: FragmentFavBinding? = null
    private val binding get() = _binding!!
    private lateinit var moviesAdapter: FavAdapter
    private lateinit var viewModel: FavoriViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: FavoriViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel.yemeklerFavoriListesi.observe(viewLifecycleOwner) { list ->
            moviesAdapter = FavAdapter(list, requireContext(), viewModel) {
                findNavController().navigate(
                    FavFragmentDirections.actionFavFragmentToDetailFragment(
                        it
                    )
                )
            }
            binding.rvFav.adapter = moviesAdapter
        }

        binding.imghome.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}