package com.hacksprint.financeapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hacksprint.financeapp.databinding.CreateProfileBinding

class CreateProfileFragment : Fragment() {
    private lateinit var binding: CreateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


}

