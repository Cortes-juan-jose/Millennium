package com.app.millennium.ui.fragments.products_and_opinions_to_user_product_selected

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.millennium.R
import com.app.millennium.databinding.FragmentOpinionsUserBinding
import com.app.millennium.databinding.FragmentOpinionsUserToProductSelectedBinding

class OpinionsUserToProductSelectedFragment(private val idUser: String) : Fragment() {

    private var _binding: FragmentOpinionsUserToProductSelectedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOpinionsUserToProductSelectedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}