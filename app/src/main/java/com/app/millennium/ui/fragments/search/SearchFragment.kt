package com.app.millennium.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.millennium.R
import com.app.millennium.databinding.FragmentSearchBinding
import com.app.millennium.ui.adapters.category.CategoryAdapter

class SearchFragment : Fragment() {

    //Binding
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val categories = mutableListOf<String>()
    private lateinit var categoryAdapter: CategoryAdapter

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
    }

    private fun initUI() {
        createListCategories()
        configRecyclerCategories()
    }

    private fun configRecyclerCategories() {
        binding.rvCategories.layoutManager = LinearLayoutManager(
            activity?.applicationContext,
            LinearLayoutManager.VERTICAL,
            false
        )
        categoryAdapter = CategoryAdapter(categories)
        binding.rvCategories.adapter = categoryAdapter
    }

    private fun createListCategories() {
        categories.add(getString(R.string.cat_coches))
        categories.add(getString(R.string.cat_motos_bicicleta))
        categories.add(getString(R.string.cat_motor_accesorios))
        categories.add(getString(R.string.cat_moda_accesorios))
        categories.add(getString(R.string.cat_inmobiliaria))
        categories.add(getString(R.string.cat_informatica_electronica))
        categories.add(getString(R.string.cat_deporte_ocio))
        categories.add(getString(R.string.cat_consolas_videojuegos))
        categories.add(getString(R.string.cat_hogar_jardin))
        categories.add(getString(R.string.cat_electrodomésticos))
        categories.add(getString(R.string.cat_cine_libros_música))
        categories.add(getString(R.string.cat_niños_bebes))
        categories.add(getString(R.string.cat_coleccionismo))
        categories.add(getString(R.string.cat_otros))
    }
}