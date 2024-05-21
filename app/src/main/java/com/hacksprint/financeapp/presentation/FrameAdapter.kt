package com.hacksprint.financeapp.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hacksprint.financeapp.FrameLayoutFragment
import com.hacksprint.financeapp.R

class FrameAdapter (fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FrameLayoutFragment(R.layout.frame_img1)
            1 -> FrameLayoutFragment(R.layout.frame_img2)
            2 -> FrameLayoutFragment(R.layout.frame_img_3)
            3 -> FrameLayoutFragment(R.layout.frame_img4)
            4 -> FrameLayoutFragment(R.layout.create_profile)


            else -> throw IllegalArgumentException("invalido,$position")
        }
    }

}