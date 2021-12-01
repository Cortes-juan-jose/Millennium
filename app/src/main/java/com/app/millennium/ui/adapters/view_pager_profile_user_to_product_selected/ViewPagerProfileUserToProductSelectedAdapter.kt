package com.app.millennium.ui.adapters.view_pager_profile_user_to_product_selected

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.millennium.ui.fragments.products_and_likes_to_user_product_selected.ProductsUserToProductSelectedFragment

class ViewPagerProfileUserToProductSelectedAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val idUser: String
):  FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int = 1

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ProductsUserToProductSelectedFragment(idUser) }
            else -> { Fragment() }
        }
    }
}