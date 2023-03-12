package com.mathew.openweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mathew.openweather.databinding.FragmentAddCityBinding
import com.mathew.openweather.viewmodel.AddCityViewModel

/**
 * A simple [Fragment] subclass for adding City.
 */
class AddCityFragment : Fragment() {

    private var _binding: FragmentAddCityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val addCityViewModel: AddCityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCityBinding.inflate(inflater, container, false)
        binding.viewModel = addCityViewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
