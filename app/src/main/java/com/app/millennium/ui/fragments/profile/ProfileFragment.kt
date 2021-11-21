package com.app.millennium.ui.fragments.profile

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.millennium.R
import com.app.millennium.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isThemeLight())
            binding.ctlAppbar.contentScrim = context?.let { ContextCompat.getDrawable(it, R.drawable.toolbar_light) }
        else
            binding.ctlAppbar.contentScrim = context?.let { ContextCompat.getDrawable(it, R.drawable.toolbar_dark) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isThemeLight(): Boolean {
        return when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {false}
            Configuration.UI_MODE_NIGHT_NO -> {true}
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {false}
            else -> {false}
        }
    }
}