package com.app.millennium.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.R
import com.app.millennium.core.common.converProduct
import com.app.millennium.core.common.isNotNull
import com.app.millennium.core.common.toast
import com.app.millennium.core.utils.ConfigThemeApp
import com.app.millennium.data.model.Product
import com.app.millennium.databinding.FragmentHomeBinding
import com.app.millennium.ui.adapters.product_home.ProductHomeAdapter

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val fragmentViewModel: HomeFragmentViewModel by viewModels()

    private var products = mutableListOf<Product>()
    private lateinit var productHomeAdapter: ProductHomeAdapter

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

    //Init ui
    private fun initUI() {
        buildLottieAnimation()
        fragmentViewModel.init()
    }

    /**
     * Metodo que setea una u otra animacion dependiendo del tema del dispositivo
     */
    private fun buildLottieAnimation() {
        if (ConfigThemeApp.isThemeLight(requireContext())){
            binding.animArrow.setAnimation(R.raw.anim_arrow_down_post_product)
        } else {
            binding.animArrow.setAnimation(R.raw.anim_arrow_down_post_product_theme_dark)
        }
    }

    private fun initObservables() {
        fragmentViewModel.apply {

            /**
             * Observer para obtener los productos
             */
            getAllProducts.observe(
                viewLifecycleOwner,
                {
                    it?.let { task ->
                        task.addOnSuccessListener { result ->
                            /**
                             * Si se vuelve a este fragment desde el botón de ir hacia
                             * atrás se cargara por dos veces he por ello que la lista debemos
                             * vaciarla y volver a insertarle todos los productos
                             */
                            if (products.isNotEmpty())
                                products.clear()
                            //En result tenemos la lista de todos los productos
                            //Verificamos que haya productos en la lista
                            if (result.isEmpty){
                                //Si la lista es vacía mostramos un layout indicándoolo
                                binding.llListProducts.visibility = View.GONE
                                binding.progress.visibility = View.GONE
                                binding.clSinProductos.visibility = View.VISIBLE
                            } else {
                                //De lo contrario recorremos la lista
                                for (product in result){
                                    //Ahora para casa product (document) verificamos
                                    //que exista y no sea nulo
                                    if (product.isNotNull() && product.exists()){
                                        //si no es nulo y existe ntonces se guarda en la lista
                                        //convirtiendo cada product en un objeto producto
                                        //ya que data del product devuelve un map con todas
                                        //las propiedades
                                        products.add(product.data.converProduct())
                                    }
                                }
                                //Una vez tengamos todos los productos creamos el adapter
                                //con la lista de los producots
                                productHomeAdapter = ProductHomeAdapter(products)
                                //Configuramos la disposicion del recycler view
                                binding.rvProducts.layoutManager = LinearLayoutManager(
                                    requireContext(),
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                //y le seteamos el adapter al recycler view
                                binding.rvProducts.adapter = productHomeAdapter

                                //Y a continuación, mostramos la vista de la lista
                                //y escondemos el progress bar y la vista de aviso sin productos
                                binding.llListProducts.visibility = View.VISIBLE
                                binding.clSinProductos.visibility = View.GONE
                                binding.progress.visibility = View.GONE
                            }
                        }
                        task.addOnFailureListener { exc ->
                            activity?.toast("${exc.message}")
                            binding.llListProducts.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            binding.clSinProductos.visibility = View.GONE
                        }
                    }
                }
            )
        }
    }
}