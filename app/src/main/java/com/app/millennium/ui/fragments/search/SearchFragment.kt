package com.app.millennium.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.FragmentSearchBinding
import com.app.millennium.ui.adapters.product_home.ProductHomeAdapter

class SearchFragment : Fragment() {

    //Binding
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    //View Model
    private val viewModel : SearchViewModel by viewModels()

    //Productos
    private var products = mutableListOf<Product>()
    //Adapter de los productos
    private lateinit var productHomeAdapter: ProductHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    private fun initObservables() {

    }

    private fun initUI() {
        Toast.makeText(requireContext(), "Buscar", Toast.LENGTH_SHORT).show()
    }
}