package com.png.interview.weather.ui.customview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.png.interview.databinding.ViewForecastSingleDayBinding
import com.png.interview.weather.ui.customview.ForecastRecyclerViewAdapter.ForecastViewHolder
import com.png.interview.weather.ui.model.ForecastDayViewData

class ForecastRecyclerViewAdapter(
    private var forecastViewDays: List<ForecastDayViewData>
): RecyclerView.Adapter<ForecastViewHolder>() {

    fun updateForecastViewDays(update: List<ForecastDayViewData>) {
        forecastViewDays = update
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ViewForecastSingleDayBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun getItemCount() = forecastViewDays.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        with(holder.forecastViewBinding) {
            with(forecastViewDays[position]) {
                tvDateValue.text = date
                tvMinTempValue.text = minTemp
                tvMaxTempValue.text = maxTemp
                tvWindSpeedValue.text = windSpeed
                tvConditionValue.text = condition
            }
        }
    }

    inner class ForecastViewHolder(val forecastViewBinding: ViewForecastSingleDayBinding) : RecyclerView.ViewHolder(forecastViewBinding.root) {}
}