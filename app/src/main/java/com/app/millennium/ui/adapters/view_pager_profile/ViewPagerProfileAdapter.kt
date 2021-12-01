package com.app.millennium.ui.adapters.view_pager_profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.millennium.ui.fragments.products_and_likes_to_user.LikesUserFragment
import com.app.millennium.ui.fragments.products_and_likes_to_user.ProductsUserFragment

class ViewPagerProfileAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
):  FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ProductsUserFragment() }
            1 -> { LikesUserFragment() }
            else -> { Fragment() }
        }
    }
}