package com.app.millennium.ui.fragments.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.core.common.Constant
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.toast
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.FragmentLikeBinding
import com.app.millennium.ui.adapters.product_home.ProductHomeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LikeFragment : Fragment() {

    //Binding
    private var _binding : FragmentLikeBinding? = null
    private val binding get() = _binding!!

    //View Model
    private val viewModel : LikeViewModel by viewModels()

    //Productos
    private var products = mutableListOf<Product>()
    //Adapter de los productos
    private lateinit var productHomeAdapter: ProductHomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initObservables()
    }

    /**
     * Inicializar la configuraciones de la vista
     */
    private fun initUI() {
        viewModel.getIdUserSession()
    }

    /**
     * Inicializar los observables del viewmodel
     */
    private fun initObservables() {
        /**
         * Obtener el id del usuario de la sesion
         */
        viewModel.getIdUserSession.observe(
            viewLifecycleOwner,
            {
                it?.let { viewModel.getAllLikeByUser(it) }
            }
        )

        /**
         * Obtener toda la lista de los prodcutos que me gustan
         */
        viewModel.getAllLikeByUser.observe(
            viewLifecycleOwner,
            { task ->
                task?.let { snapshot ->
                    snapshot.addOnSuccessListener { _snapshot ->
                        if (_snapshot.documents.isNotEmpty()){

                            for (like in _snapshot.documents){
                                if (like.isNotNull() && like.exists()){
                                    CoroutineScope(Dispatchers.IO).launch {
                                        like.getString(Constant.PROP_ID_PRODUCT_LIKE)?.let {
                                            idProduct -> viewModel.getProduct().invoke(idProduct).addOnSuccessListener { product ->
                                                product?.let { _product ->
                                                    if (_product.exists() && _product.isNotNull()){
                                                        _product.toObject(Product::class.java)
                                                            ?.let { products.add(it) }
                                                    }

                                                    if (products.size == _snapshot.size()){
                                                        productHomeAdapter = ProductHomeAdapter(products, requireContext())
                                                        //Configuramos la disposicion del recycler view
                                                        binding.rvProductsFavorites.layoutManager = LinearLayoutManager(
                                                            requireContext(),
                                                            LinearLayoutManager.VERTICAL,
                                                            false
                                                        )
                                                        //y le seteamos el adapter al recycler view
                                                        binding.rvProductsFavorites.adapter = productHomeAdapter

                                                        binding.progress.visibility = View.GONE
                                                        binding.mtvWithoutProductsFavorites.visibility = View.GONE
                                                        binding.llListProducts.visibility = View.VISIBLE
                                                    }
                                                }
                                            }
                                        }


                                    }
                                }
                            }
                        } else {
                            binding.progress.visibility = View.GONE
                            binding.mtvWithoutProductsFavorites.visibility = View.VISIBLE
                            binding.llListProducts.visibility = View.GONE
                        }
                    }

                    snapshot.addOnFailureListener { exc ->
                        activity?.toast("${exc.message}")
                        binding.progress.visibility = View.GONE
                        binding.mtvWithoutProductsFavorites.visibility = View.GONE
                        binding.llListProducts.visibility = View.GONE
                    }
                }
            }
        )
    }
}