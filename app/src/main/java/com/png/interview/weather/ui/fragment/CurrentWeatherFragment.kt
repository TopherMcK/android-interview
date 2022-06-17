package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.png.interview.databinding.FragmentCurrentWeatherBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.CurrentWeatherFragmentViewBinder
import com.png.interview.weather.ui.customview.AutoCompleteListArrayAdapter
import com.png.interview.weather.ui.viewmodel.CurrentWeatherViewModel

class CurrentWeatherFragment : InjectedFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentCurrentWeatherBinding = FragmentCurrentWeatherBinding.inflate(inflater, container,false)
        val rootView = binding.root

        val autocompleteAdapter = AutoCompleteListArrayAdapter(rootView.context)
        with(binding.viewAutocompleteSuggestions.suggestionListView) {
            adapter = autocompleteAdapter
            setOnItemClickListener { _, _, position, _ ->
                binding.viewBinder?.suggestionItemClicked(position)
            }
        }

        return binding.apply {
            val viewModel: CurrentWeatherViewModel = getViewModel()
            viewBinder = CurrentWeatherFragmentViewBinder(
                viewModel,
                requireActivity(),
                settingsAction = {
                    findNavController().navigate(CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToSettingsFragment())
                },
                forecastAction = { forecastQuery ->
                    findNavController().navigate(CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToForecastFragment(forecastQuery))
                },
                viewLifecycleOwner,
                autocompleteAdapter
            )
            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }
}