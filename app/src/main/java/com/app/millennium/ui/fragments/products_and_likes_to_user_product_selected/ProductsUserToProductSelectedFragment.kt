package com.app.millennium.ui.fragments.products_and_likes_to_user_product_selected

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
import com.app.millennium.databinding.FragmentProductsUserToProductSelectedBinding
import com.app.millennium.ui.adapters.product_profile_user_to_product_selected.ProductProfileUserToProductSelectedAdapter

class ProductsUserToProductSelectedFragment(private val idUser: String) : Fragment() {

    private var _binding: FragmentProductsUserToProductSelectedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserToProductSelectedViewModel by viewModels()

    private val products = mutableListOf<Product>()
    private lateinit var productProfileUserToProductSelectedAdapter: ProductProfileUserToProductSelectedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProductsUserToProductSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    private fun initUI() {
        viewModel.getProducts(idUser)
    }

    private fun initObservables() {
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
                            productProfileUserToProductSelectedAdapter = ProductProfileUserToProductSelectedAdapter(products)
                            //Configuramos la disposicion del recycler view
                            binding.rvProducts.layoutManager = LinearLayoutManager(
                                activity?.applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            //y le seteamos el adapter al recycler view
                            binding.rvProducts.adapter = productProfileUserToProductSelectedAdapter

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
}