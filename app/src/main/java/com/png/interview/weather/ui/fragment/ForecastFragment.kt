package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.png.interview.R
import com.png.interview.databinding.FragmentForecastBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.ForecastFragmentViewBinder
import com.png.interview.weather.ui.customview.ForecastRecyclerViewAdapter

class ForecastFragment : InjectedFragment() {

    private lateinit var forecastFragmentViewBinder: ForecastFragmentViewBinder
    private val forecastFragmentArgs: ForecastFragmentArgs by navArgs<ForecastFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentForecastBinding.inflate(inflater, container,false)

        val linearLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(binding.root.context)
        val recyclingViewAdapter = ForecastRecyclerViewAdapter(emptyList())
        binding.root.findViewById<RecyclerView>(R.id.forecast_day_list).apply {
            layoutManager = linearLayoutManager
            adapter = recyclingViewAdapter
        }

        forecastFragmentViewBinder = ForecastFragmentViewBinder(
            getViewModel(),
            recyclingViewAdapter,
            viewLifecycleOwner
        )

        return binding.apply {
            viewBinder = forecastFragmentViewBinder
            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastFragmentViewBinder.loadForecastViewData(forecastFragmentArgs.forecastQuery)
    }
}