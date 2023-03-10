package com.mathew.openweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mathew.openweather.viewmodel.AddCityViewModel
import com.mathew.openweather.viewmodel.AddCityViewModelFactory
import com.mathew.openweather.R
import com.mathew.openweather.databinding.FragmentAddCityBinding

/**
 * A simple [Fragment] subclass for adding City.
 */
class AddCityFragment : Fragment() {

    private var _binding: FragmentAddCityBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val addCityViewModel: AddCityViewModel by viewModels {
        AddCityViewModelFactory(this.requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCityBinding.inflate(inflater, container, false)
        binding.viewModel = addCityViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddCity.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

//        addCityViewModel.allCities.observe(viewLifecycleOwner) {
//            it?.let {
//
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
