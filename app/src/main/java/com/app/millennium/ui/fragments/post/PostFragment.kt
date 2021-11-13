package com.app.millennium.ui.fragments.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.millennium.R
import com.app.millennium.core.common.openActivity
import com.app.millennium.ui.activities.post_product.PostProductActivity

class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    /**
     * En este fragment, cuando se haya creado abrirá la activity para publicar un producto
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.openActivity<PostProductActivity> {  }
    }
}