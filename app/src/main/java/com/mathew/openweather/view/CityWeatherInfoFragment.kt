package com.mathew.openweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mathew.openweather.R
import com.mathew.openweather.databinding.LayoutCurrentWeatherMainBinding
import com.mathew.openweather.util.unixTimestampToTimeString
import com.mathew.openweather.viewmodel.CityWeatherInfoViewModel
import com.mathew.openweather.viewmodel.CityWeatherInfoViewModelFactory
import com.openweather.content.model.currentweather.CurrentWeather

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CityWeatherInfoFragment : Fragment() {

    private var _binding: LayoutCurrentWeatherMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val cityWeatherInfoViewModel: CityWeatherInfoViewModel by viewModels {
        CityWeatherInfoViewModelFactory(this.requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = LayoutCurrentWeatherMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("CityName")?.let {
            cityWeatherInfoViewModel.fetchWeatherInfoFromCityName(it)
        } ?: run {
            findNavController().popBackStack()
        }
        cityWeatherInfoViewModel.getCurrentWeatherLiveData()
            .observe(viewLifecycleOwner) { currentWeather ->
                currentWeather?.let {
                    setUpUI(it)
                }
            }
    }

    // TODO: Need to bind this using data binding.
    private fun setUpUI(data: CurrentWeather?) {
        binding.tvTemp.text = data?.main?.temp.toString()
        binding.tvCityName.text = data?.name
        binding.tvWeatherCondition.text = data?.weather!![0].main
        binding.include2.tvSunriseTime.text = data.sys?.sunrise?.unixTimestampToTimeString()
        binding.include2.tvSunsetTime.text = data.sys?.sunset?.unixTimestampToTimeString()
        binding.include2.tvRealFeelText.text =
            "${data.main?.feelsLike}${getString(R.string.degree_celsius_symbol)}"
        binding.include2.tvCloudinessText.text = "${data.clouds?.all}%"
        binding.include2.tvWindSpeedText.text = "${data.wind?.speed}m/s"
        binding.include2.tvHumidityText.text = "${data.main?.humidity}%"
        binding.include2.tvPressureText.text = "${data.main?.pressure}hPa"
        binding.include2.tvVisibilityText.text = "${data.visibility}M"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
