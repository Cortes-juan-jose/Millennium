package com.app.millennium.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.core.common.converProduct
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.toast
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.FragmentHomeBinding
import com.app.millennium.ui.adapters.product.ProductAdapter

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var products: MutableList<Product>
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()

    }

    private fun initUI() {
        binding.progress.visibility = View.VISIBLE
        binding.rvProducts.visibility = View.GONE
        products = mutableListOf()
        viewModel.init()
    }

    private fun initObservables() {
        viewModel.apply {

            /**
             * Observer para obtener los productos
             */
            getAllProducts.observe(
                viewLifecycleOwner,
                {
                    it?.let { task ->
                        task.addOnSuccessListener { result ->
                            if (result.isEmpty){
                                activity?.toast("Lista vacia")
                                binding.rvProducts.visibility = View.GONE
                                binding.progress.visibility = View.VISIBLE
                            } else {
                                for (document in result){
                                    if (document.isNotNull() && document.exists()){
                                        //Entonces se guarda en la lista
                                        products.add(document.data.converProduct())
                                    }
                                }
                                productAdapter = ProductAdapter(products)
                                binding.rvProducts.layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                binding.rvProducts.adapter = productAdapter
                                binding.rvProducts.visibility = View.VISIBLE
                                binding.progress.visibility = View.GONE
                            }
                        }
                        task.addOnFailureListener { exc ->
                            activity?.toast("${exc.message}")
                            binding.rvProducts.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}