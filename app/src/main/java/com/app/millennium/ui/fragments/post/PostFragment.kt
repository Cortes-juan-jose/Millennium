package com.app.millennium.ui.fragments.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.millennium.core.common.openActivity
import com.app.millennium.databinding.FragmentPostBinding
import com.app.millennium.ui.activities.post_product.PostProductActivity

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * En este fragment, cuando se haya creado abrirá la activity para publicar un producto
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.openActivity<PostProductActivity> {  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}