package com.example.brain.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.brain.R
import com.example.brain.databinding.FragmentChoseLevelBinding
import com.example.brain.domain.entity.Level


class ChoseLevel : Fragment() {

    private var _binding: FragmentChoseLevelBinding? = null
    private val binding: FragmentChoseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChoseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentChoseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            testBtn.setOnClickListener {
                launchGameFragment(Level.TEST)
            }

            easyBtn.setOnClickListener {
                launchGameFragment(Level.EASY)
            }

            normalBtn.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }

            hardBtn.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChoseLevelDirections.actionChoseLevelToGameFragment(level)
        )
    }



}