package com.app.millennium.ui.fragments.products_and_opinions_to_user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.core.common.converProduct
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.isNull
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.FragmentProductsUserBinding
import com.app.millennium.ui.adapters.product_profile.ProductProfileAdapter

class ProductsUserFragment : Fragment() {

    private var _binding: FragmentProductsUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    private val products = mutableListOf<Product>()
    private lateinit var productProfileAdapter: ProductProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    private fun initUI() {
        viewModel.getIdUserToSession()
    }

    private fun initObservables() {

        viewModel.getIdUserToSession.observe(
            viewLifecycleOwner,
            {
                it?.let { viewModel.getProducts(it) }
            }
        )

        viewModel.getAllProductsByUser.observe(
            viewLifecycleOwner,
            {
                it?.let {

                    it.addSnapshotListener { value, error ->
                        if (products.isNotEmpty())
                            products.clear()

                        if (error.isNotNull()){
                            return@addSnapshotListener
                        }

                        if (value.isNull() || value!!.isEmpty){
                            binding.rvProducts.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            binding.mtvWithoutProducts.visibility = View.VISIBLE
                        } else {

                            for (product in value.documents){
                                if (product.isNotNull() && product.exists()){
                                    products.add(product.data.converProduct())
                                }
                            }

                            //Una vez tengamos todos los productos creamos el adapter
                            //con la lista de los producots
                            productProfileAdapter = ProductProfileAdapter(products, requireContext())
                            //Configuramos la disposicion del recycler view
                            binding.rvProducts.layoutManager = LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            //y le seteamos el adapter al recycler view
                            binding.rvProducts.adapter = productProfileAdapter

                            //Escondemos el texto sin productos el progress y mostramos la lista
                            binding.rvProducts.visibility = View.VISIBLE
                            binding.mtvWithoutProducts.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                        }
                    }
                }
            }
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}