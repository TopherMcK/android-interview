package com.png.interview.weather.preferences

import java.util.*

enum class MeasurementSystem {
    IMPERIAL,
    METRIC;

    companion object {
        private val measurementSystemNames: Map<String, MeasurementSystem> = values().map {
            it.name to it
        }.toMap()

        fun getMeasurementSystemByName(name: String?): MeasurementSystem =
            measurementSystemNames[name?.uppercase()] ?: IMPERIAL
    }
}