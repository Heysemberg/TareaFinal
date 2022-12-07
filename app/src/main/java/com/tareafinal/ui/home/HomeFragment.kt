package com.tareafinal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tareafinal.R
import com.tareafinal.adapter.PostreAdapter
import com.tareafinal.databinding.FragmentHomeBinding
import com.tareafinal.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btAddPostre.setOnClickListener{
            findNavController().navigate(R.id.action_nav_home_to_addPostreFragment)
        }

        val postreAdapter = PostreAdapter()
        val reciclador = binding.reciclador
        reciclador.adapter = postreAdapter
        reciclador.layoutManager = LinearLayoutManager(requireContext())

        homeViewModel.obtenerPostres.observe(viewLifecycleOwner){
                postres -> postreAdapter.setPostres(postres)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}