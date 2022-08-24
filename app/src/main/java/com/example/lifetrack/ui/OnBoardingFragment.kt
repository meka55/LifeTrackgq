package com.example.lifetrack.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lifetrack.adapters.OnBoardingAdapter
import com.example.lifetrack.room.OnBoardingModel
import com.example.lifetrack.OnItemClicker
import com.example.lifetrack.R
import com.example.lifetrack.databinding.FragmentOnBoardingBinding

class OnBoardingFragment : Fragment(), OnItemClicker {

    private lateinit var binding: FragmentOnBoardingBinding
    private val list = arrayListOf<OnBoardingModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("board_preferences",
            Context.MODE_PRIVATE)

        checkIsShown(sharedPreferences)

        list.add(OnBoardingModel(R.drawable.img, "Экономь время", "Дальше"))
        list.add(OnBoardingModel(R.drawable.img_1, "Достигай целей", "Дальше"))
        list.add(OnBoardingModel(R.drawable.img_2, "Развивайся", "Начинаем"))
        binding.viewPager.adapter = OnBoardingAdapter(list, this)
        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    private fun checkIsShown(sharedPreferences: SharedPreferences) {
        val isShown: Boolean = sharedPreferences.getBoolean("IsShow", false)
        if (isShown) {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    override fun onClick() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("board_preferences",
            Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isShow",true).apply()
        findNavController().navigate(R.id.clearBackStack)

    }

    override fun onClickNext() {
        onNext()
    }

    private fun onNext() {
        val adapter = binding.viewPager.adapter
        val currentPosition = binding.viewPager.currentItem
        val nextPosition = currentPosition + 1
        if (nextPosition < adapter?.itemCount!!) {
            binding.viewPager.currentItem = nextPosition
        } else
            binding.viewPager.currentItem = 0
    }
}