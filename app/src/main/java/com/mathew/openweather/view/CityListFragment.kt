package com.mathew.openweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mathew.openweather.R
import com.mathew.openweather.databinding.FragmentCityListBinding
import com.mathew.openweather.util.Constants.CITY_NAME
import com.mathew.openweather.view.adapter.CityListAdapter
import com.mathew.openweather.viewmodel.CityListViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass for Listing City.
 */
@AndroidEntryPoint
class CityListFragment : Fragment() {

    private var _binding: FragmentCityListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val cityListViewModel: CityListViewModel by viewModels()

    private var adapter: CityListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initObservers()
    }

    private fun initObservers() {
        cityListViewModel.allCities.observe(viewLifecycleOwner) { cityList ->
            cityList?.let {
                if (it.isNotEmpty()) {
                    adapter?.setData(it)
                    binding.textViewError.visibility = View.GONE
                } else {
                    binding.textViewError.visibility = View.VISIBLE
                }
            } ?: run {
                binding.textViewError.visibility = View.VISIBLE
            }
        }
    }

    private fun initRecyclerView() {
        adapter = CityListAdapter()
        binding.recyclerViewCityList.adapter = adapter
        adapter?.onItemClickListener = {
            findNavController().navigate(
                R.id.action_Navigate_To_CityWeatherInfo,
                Bundle().apply {
                    putString(CITY_NAME, it?.cityName)
                })
        }
    }

    override fun onResume() {
        super.onResume()
        cityListViewModel.findAllCities()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
